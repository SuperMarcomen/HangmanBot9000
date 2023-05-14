package it.marcodemartino.hangmanbot.game.words;

import java.util.Locale;
import java.util.Map;

public abstract class WordsProvider {

    protected Map<Locale, Words> wordsMap;

    abstract void loadAllWords();
    public Words getWordsFromLocale(Locale locale) {
        return wordsMap.get(locale);
    }
}
