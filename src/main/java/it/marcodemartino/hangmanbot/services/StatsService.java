package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.match.GuessResult;
import it.marcodemartino.hangmanbot.entities.match.RunningMatch;
import it.marcodemartino.hangmanbot.entities.stats.UserMatchContribution;
import it.marcodemartino.hangmanbot.entities.stats.UserStatistics;
import it.marcodemartino.hangmanbot.repositories.UserStatisticsRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

  public RunningMatch updateStats(GuessResult guessResult, RunningMatch match, UserIdentity userIdentity) {
    Optional<UserStatistics> userStatsOpt = userStatsRepository.findById(userIdentity.userId());
    UserStatistics userStats = userStatsOpt.orElse(new UserStatistics(userIdentity));
    switch (guessResult) {
      case MATCH_WON -> {
        Optional<UserMatchContribution> max = match.contributions()
            .stream()
            .max(Comparator.comparing(UserMatchContribution::rightLetters));
        if (max.isEmpty()) {

          break;
        }
        List<UserMatchContribution> userMaxContributions = match.contributions()
            .stream()
            .filter(contribution -> contribution.rightLetters() == max.get().rightLetters())
            .toList();
        UserMatchContribution maxRandomUser = userMaxContributions.get(random.nextInt(userMaxContributions.size()));
        userStats.increaseWonMatches().increaseRightLetters();
        Optional<UserStatistics> maxRandomUserStats = userStatsRepository.findById(maxRandomUser.userId());
            updateContribution(match, userIdentity);
      }
      case LETTER_RIGHT -> {
        userStats.increaseRightLetters();
        updateContribution(match, userIdentity);
      }
      case LETTER_WRONG, MATCH_LOST -> userStats.increaseWrongLetters();
      default -> {
      }
    }
    userStatsRepository.save(userStats);
    return match;
  }

  private void updateContribution(RunningMatch match, UserIdentity userIdentity) {
    UserMatchContribution contribution = match.contributionOf(userIdentity.userId());
    contribution.increaseRightLetters();
  }
}
