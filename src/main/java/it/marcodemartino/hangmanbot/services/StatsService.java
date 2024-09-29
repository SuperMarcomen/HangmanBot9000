package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.match.GuessResult;
import it.marcodemartino.hangmanbot.entities.stats.UserStatistics;
import it.marcodemartino.hangmanbot.repositories.UserStatisticsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

  private final UserStatisticsRepository userStatisticsRepository;

  @Autowired
  public StatsService(UserStatisticsRepository userStatisticsRepository) {
    this.userStatisticsRepository = userStatisticsRepository;
  }

  public void updateStats(GuessResult guessResult, UserIdentity userIdentity) {
    Optional<UserStatistics> userStatsOpt = userStatisticsRepository.findById(userIdentity.userId());
    UserStatistics userStats = userStatsOpt.orElse(new UserStatistics(userIdentity));
    switch (guessResult) {
      case MATCH_WON -> userStats.increaseWonMatches().increaseRightLetters();
      case LETTER_RIGHT -> userStats.increaseRightLetters();
      case LETTER_WRONG, MATCH_LOST -> userStats.increaseWrongLetters();
    }
  }
}
