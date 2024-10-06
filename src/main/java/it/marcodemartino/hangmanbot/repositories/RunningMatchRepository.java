package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.match.RunningMatch;
import it.marcodemartino.hangmanbot.entities.match.RunningMatchId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Stores currently running matches.
 */
public interface RunningMatchRepository extends JpaRepository<RunningMatch, RunningMatchId> {
}
