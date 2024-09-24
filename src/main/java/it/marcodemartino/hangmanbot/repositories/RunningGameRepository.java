package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.RunningGame;
import it.marcodemartino.hangmanbot.entities.RunningGameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningGameRepository extends JpaRepository<RunningGame, RunningGameId> {
}
