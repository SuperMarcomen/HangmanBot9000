package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.stats.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Stores statistics about the matches played by the users.
 */
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
}
