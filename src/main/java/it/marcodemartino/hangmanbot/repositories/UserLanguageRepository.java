package it.marcodemartino.hangmanbot.repositories;

import it.marcodemartino.hangmanbot.entities.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLanguageRepository extends JpaRepository<UserLanguage, Long> {
}
