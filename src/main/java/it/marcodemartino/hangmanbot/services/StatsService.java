package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.match.GuessResult;
import it.marcodemartino.hangmanbot.entities.match.RunningMatch;
import it.marcodemartino.hangmanbot.entities.stats.UserMatchContribution;
import it.marcodemartino.hangmanbot.entities.stats.UserStatistics;
import it.marcodemartino.hangmanbot.repositories.UserStatisticsRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

  private final UserStatisticsRepository userStatsRepository;
  private final Random random;

  @Autowired
  public StatsService(UserStatisticsRepository userStatsRepository) {
    this.userStatsRepository = userStatsRepository;
    random = new Random();
  }

  public void increaseStartedMatches(UserIdentity userIdentity) {
    UserStatistics userStats = getOrCreateStats(userIdentity.userId());
    userStats.increaseStartedMatches();
    userStatsRepository.save(userStats);
  }

  public RunningMatch updateStats(GuessResult guessResult, RunningMatch match, UserIdentity userIdentity) {
    UserStatistics userStats = getOrCreateStats(userIdentity.userId());

    switch (guessResult) {
      case MATCH_WON, MATCH_PERFECTLY_WON -> {
        increaseLetterContribution(match, userStats, userIdentity);

        // Finds the user that contributed to most to this match (pulls one randomly in case there are
        // multiple one with as big of a contribution) and increases its stats
        UserStatistics maxRandomUserStats = getBestUserForMatch(match);
        if (guessResult.equals(GuessResult.MATCH_WON)) {
          maxRandomUserStats.increaseWonMatches();
        } else {
          maxRandomUserStats.increasePerfectMatches();
        }

        userStatsRepository.save(maxRandomUserStats);
      }
      case LETTER_RIGHT -> {
        increaseLetterContribution(match, userStats, userIdentity);
      }
      case LETTER_WRONG, MATCH_LOST -> userStats.increaseWrongLetters();
      default -> {
      }
    }

    userStatsRepository.save(userStats);
    return match;
  }

  private UserStatistics getBestUserForMatch(RunningMatch match) {
    // Find the user who guessed the most right letters
    UserMatchContribution maxContribution = match.contributions()
        .stream()
        .max(Comparator.comparing(UserMatchContribution::rightLetters))
        .orElseThrow(() -> new IllegalStateException("Couldn't find any user contributing to the match!"));

    // Find all the users who guessed the same max amount of letters
    List<UserMatchContribution> allMaxContributions = match.contributions()
        .stream()
        .filter(contribution -> contribution.rightLetters() == maxContribution.rightLetters())
        .toList();

    // Out of all the best users for this match, pull one randomly and increase its "won matches" stat
    int randomWinningUserIndex = random.nextInt(allMaxContributions.size());
    UserMatchContribution maxRandomUser = allMaxContributions.get(randomWinningUserIndex);
    return getOrCreateStats(maxRandomUser.userId());
  }

  private UserStatistics getOrCreateStats(long userId) {
    return userStatsRepository
        .findById(userId)
        .orElse(new UserStatistics().userId(userId));
  }

  private void increaseLetterContribution(RunningMatch match, UserStatistics userStats, UserIdentity userIdentity) {
    UserMatchContribution contribution = match.contributionOf(userIdentity.userId());
    contribution.increaseRightLetters();
    userStats.increaseRightLetters();
  }
}
