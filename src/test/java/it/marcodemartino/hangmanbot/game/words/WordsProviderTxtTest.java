package it.marcodemartino.hangmanbot.game.words;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordsProviderTxtTest {

    private final WordsProviderTxt wordsProvider = new WordsProviderTxt();
    private final Map<Locale, Words> map = wordsProvider.getWordsMap();

    @Test
    void checkIfWordsEmpty() {
        assertFalse(map.isEmpty());
    }

    @Test
    void checkIfCategoriesNotNull() {
        assertNotNull(map.get(Locale.ENGLISH));
    }

    @Test
    void checkIfAlphabetEmpty() {
        assertFalse(map.get(Locale.ENGLISH).getAlphabet().isEmpty());
    }
}