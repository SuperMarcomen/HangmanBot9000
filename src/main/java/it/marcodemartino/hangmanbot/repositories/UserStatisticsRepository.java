package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
}
