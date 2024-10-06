package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.match.GuessResult;
import it.marcodemartino.hangmanbot.entities.match.RunningMatch;
import it.marcodemartino.hangmanbot.entities.match.RunningMatchId;
import it.marcodemartino.hangmanbot.repositories.RunningMatchRepository;
import jakarta.annotation.PostConstruct;
import java.text.Collator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates new matches and handles the guessing of letters.
 */
@Service
public class MatchesService {

  private final WordsService wordsService;
  private final StatsService statsService;
  private final Map<RunningMatchId, RunningMatch> runningMatches;
  private final RunningMatchRepository runningMatchRepository;
  private final Collator collator = Collator.getInstance();

  /**
   * Initializes all the needed instances.
   *
   * @param wordsService           the service to get random words
   * @param statsService           the service to update the stats
   * @param runningMatchRepository the repository to store running matches
   */
  @Autowired
  public MatchesService(WordsService wordsService, StatsService statsService,
                        RunningMatchRepository runningMatchRepository) {
    this.wordsService = wordsService;
    this.statsService = statsService;
    this.runningMatchRepository = runningMatchRepository;
    runningMatches = new HashMap<>();
    collator.setStrength(Collator.PRIMARY);
  }

  /**
   * Loads on startup the matches that were running before the bot
   * was stopped.
   */
  @PostConstruct
  public void loadMatches() {
    List<RunningMatch> matches = runningMatchRepository.findAll();
    for (RunningMatch runningMatch : matches) {
      RunningMatchId matchId = new RunningMatchId(runningMatch.chatId(), runningMatch.messageId());
      runningMatches.put(matchId, runningMatch);
    }
  }

  /*
   * I decided against passing the model class to the view-side of the application,
   * because it contains some data manipulation methods that I would prefer be
   * hidden from that side (like {@link #RunningMatch.addGuessedLetter(char letter) addGuessedLetter}).
   * Therefore, here come some wrapper methods to expose only the needed data.
   */

  /**
   * Returns all the guessed letters of the match corresponding
   * to the given id.
   *
   * @param matchId the id of the match
   * @return all the guessed letters of the match
   */
  public char[] getGuessedLetterOfMatch(RunningMatchId matchId) {
    RunningMatch match = runningMatches.get(matchId);
    return match.guessedLetters();
  }

  /**
   * Returns the category of the match corresponding
   * to the given id.
   *
   * @param matchId the id of the match
   * @return the category of the match
   */
  public String getCategoryOfMatch(RunningMatchId matchId) {
    RunningMatch match = runningMatches.get(matchId);
    return match.category();
  }

  /**
   * Returns the word to guess of the match corresponding
   * to the given id.
   *
   * @param matchId the id of the match
   * @return the word to guess of the match
   */
  public String getWordOfMatch(RunningMatchId matchId) {
    RunningMatch match = runningMatches.get(matchId);
    return match.word();
  }

  /**
   * Returns the amount of lives left for the match corresponding
   * to the given id.
   *
   * @param matchId the id of the match
   * @return the amount of lives of the match
   */
  public int getLivesOfMatch(RunningMatchId matchId) {
    RunningMatch match = runningMatches.get(matchId);
    return match.lives();
  }

  /**
   * Forwards the call to the method in RunningMatch.
   *
   * @param matchId the id of the match
   * @return the current state of the word
   */
  public String getWordStateOfMatch(RunningMatchId matchId) {
    RunningMatch match = runningMatches.get(matchId);
    return match.getWordState();
  }

  /**
   * Creates a new match taking a random word and storing it into the db.
   *
   * @param locale         the preferred language of the user and of the word to be
   *                       randomly chosen (if sharedCategory is false)
   * @param category       the category of the word to be randomly chosen
   * @param sharedCategory indicates whether the category is language specific
   * @param matchId        the id of the match
   * @param userIdentity   the identity of the user who started the match
   */
  public void newMatch(Locale locale, String category, boolean sharedCategory,
                       RunningMatchId matchId,
                       UserIdentity userIdentity) {
    String word = sharedCategory
        ? wordsService.getWordFrom(category)
        : wordsService.getWordFrom(locale, category);
    RunningMatch runningMatch = new RunningMatch()
        .messageId(matchId.messageId())
        .chatId(matchId.chatId())
        .word(word)
        .owner(userIdentity)
        .locale(locale)
        .category(category);

    runningMatches.put(matchId, runningMatch);
    runningMatchRepository.save(runningMatch);
    statsService.increaseStartedMatches(userIdentity);
  }

  /**
   * Checks whether the guesses letter is correct and whether the match is won.
   *
   * @param letter       the letter that was guessed
   * @param userIdentity the identity of the user
   * @param matchId      the id of the match
   * @return the result of the guess
   */
  public GuessResult guessResult(Character letter, UserIdentity userIdentity, RunningMatchId matchId) {
    RunningMatch match = runningMatches.get(matchId);
    GuessResult guessResult = guessLetter(letter, match);
    match = statsService.updateStats(guessResult, match, userIdentity);
    switch (guessResult) {
      case MATCH_WON, MATCH_LOST -> {
        runningMatches.remove(matchId);
        runningMatchRepository.delete(match);
      }
      case LETTER_RIGHT, LETTER_WRONG -> runningMatchRepository.save(match);
      default -> {
      }
    }

    runningMatchRepository.save(match);

    return guessResult;
  }

  private GuessResult guessLetter(Character letter, RunningMatch match) {
    if (match.isLetterGuessed(letter)) {
      return GuessResult.LETTER_ALREADY_GUESSED;
    }

    match.addGuessedLetter(letter);

    if (!stringContainsLetter(match.word(), letter)) {
      match.lives(match.lives() - 1);
      if (match.lives() == 0) {
        return GuessResult.MATCH_LOST;
      }
      return GuessResult.LETTER_WRONG;
    }

    if (match.isMatchWon()) {
      return GuessResult.MATCH_WON;
    }
    return GuessResult.LETTER_RIGHT;
  }

  private boolean stringContainsLetter(String word, Character letter) {
    String letterString = letter.toString();
    for (char c : word.toCharArray()) {
      if (collator.equals(String.valueOf(c), letterString)) {
        return true;
      }
    }
    return false;
  }
}
