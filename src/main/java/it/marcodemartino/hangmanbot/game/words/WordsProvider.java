package it.marcodemartino.hangmanbot.game.words;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class WordsProvider {

    protected Map<Locale, Words> wordsMap;

    public abstract void loadAllWords();

    public abstract Set<String> getCategoriesFromLocale(Locale locale);
    public Words getWordsFromLocale(Locale locale) {
        return wordsMap.get(locale);
    }
}
