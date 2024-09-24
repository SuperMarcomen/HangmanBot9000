package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
}
