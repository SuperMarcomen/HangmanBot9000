package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Stores the preferred language of the users.
 */
public interface UserLanguageRepository extends JpaRepository<UserLanguage, Long> {
}
