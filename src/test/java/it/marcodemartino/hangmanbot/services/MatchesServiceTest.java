package it.marcodemartino.hangmanbot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.UserLanguage;
import it.marcodemartino.hangmanbot.entities.match.GuessResult;
import it.marcodemartino.hangmanbot.entities.match.RunningMatchId;
import it.marcodemartino.hangmanbot.repositories.RunningMatchRepository;
import it.marcodemartino.hangmanbot.repositories.UserIdentityRepository;
import it.marcodemartino.hangmanbot.repositories.UserLanguageRepository;
import it.marcodemartino.hangmanbot.repositories.UserStatisticsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/hangmanbot",
    "spring.datasource.username=hangmanbot",
    "spring.datasource.password=secret"
})
class MatchesServiceTest {

  @Autowired
  private MatchesService matchesService;

  @Autowired
  private StatsService statsService;

  @Autowired
  private UserIdentityRepository userIdentityRepository;

  @Autowired
  private UserLanguageRepository userLanguageRepository;

  @Autowired
  private UserStatisticsRepository userStatisticsRepository;

  @Autowired
  private RunningMatchRepository matchesRepository;

  private UserIdentity firstUser;

  @BeforeEach
  void setUp() {
    matchesRepository.deleteAll();
    userLanguageRepository.deleteAll();
    userStatisticsRepository.deleteAll();
    userIdentityRepository.deleteAll();

    firstUser = new UserIdentity()
        .userId(123456)
        .firstName("First")
        .username("User1");

    userIdentityRepository.save(firstUser);
    UserLanguage userLanguage = new UserLanguage()
        .userId(firstUser.userId())
        .user(firstUser)
        .language(Locale.ENGLISH);
    firstUser.language(userLanguage);

    userIdentityRepository.save(firstUser);

    List<UserLanguage> all = userLanguageRepository.findAll();
    List<UserIdentity> all1 = userIdentityRepository.findAll();

    UserIdentity userIdentity =
        userIdentityRepository.findById(123456L).orElseThrow(() -> new EntityNotFoundException("User not found"));

    UserLanguage language = new UserLanguage();
    language.language(Locale.ENGLISH);
    language.userId(userIdentity.userId());
    language.user(userIdentity);

    userLanguageRepository.save(language);

    System.out.println(all1);
  }

  @Test
  void newMatch() {
    RunningMatchId matchId = new RunningMatchId(123, 456);
    UserIdentity userIdentity = userIdentityRepository.findById(123456L).orElse(null);

    matchesService.newMatch(Locale.ENGLISH, "car", false, matchId, userIdentity);

    String wordOfMatch = matchesService.getWordOfMatch(matchId);
    assertEquals("mercedes", wordOfMatch);

    GuessResult firstResult = matchesService.guessResult('e', firstUser, matchId);
    assertEquals(firstResult, GuessResult.LETTER_RIGHT);
    assertEquals("-e--e-e-", matchesService.getWordStateOfMatch(matchId));
  }
}