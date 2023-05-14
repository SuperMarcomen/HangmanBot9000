package it.marcodemartino.hangmanbot.game.words;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordsProviderTxtTest {

    @Test
    void loadAllWords() {
        WordsProviderTxt wordsProvider = new WordsProviderTxt();
        wordsProvider.loadAllWords();

        Map<Locale, Words> map = wordsProvider.getWordsMap();
        assertFalse(map.isEmpty());
        assertNotNull(map.get(Locale.ENGLISH));
    }
}