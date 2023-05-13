package it.marcodemartino.hangmanbot.language;

import java.util.Locale;

public class DummyLanguageProvider implements LanguageProvider {
    @Override
    public Locale getUserLanguage(long userId) {
        return Locale.ENGLISH;
    }
}
