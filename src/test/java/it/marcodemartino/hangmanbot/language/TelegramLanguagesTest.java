package it.marcodemartino.hangmanbot.language;

import it.marcodemartino.hangmanbot.game.database.Database;
import it.marcodemartino.hangmanbot.game.stats.UserStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramLanguagesTest {

    @BeforeEach
    void init() {
        Database.initializeConnection("");
        UserStatsService userStatsService = new UserStatsService();
        TelegramLanguages.setUserDataDAO(userStatsService.getUserDataDAO());
    }

    @Test
    void getString() {
        assertEquals("english test", TelegramLanguages.getString("test", 0L));
    }
}