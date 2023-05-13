package it.marcodemartino.hangmanbot.language;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramLanguagesTest {

    @Test
    void getString() {
        assertEquals("english test", TelegramLanguages.getString("test", 0L));
    }
}