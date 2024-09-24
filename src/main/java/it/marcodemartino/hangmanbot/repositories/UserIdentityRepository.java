package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Stores the user identities.
 */
public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
}
