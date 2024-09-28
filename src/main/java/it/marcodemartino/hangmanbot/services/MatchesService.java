package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.entities.RunningMatch;
import it.marcodemartino.hangmanbot.entities.RunningMatchId;
import it.marcodemartino.hangmanbot.entities.UserIdentity;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchesService {

  private final WordsService wordsService;
  private final Map<RunningMatchId, RunningMatch> runningMatches;

  @Autowired
  public MatchesService(WordsService wordsService) {
    this.wordsService = wordsService;
    runningMatches = new HashMap<>();
  }

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
  }
}
