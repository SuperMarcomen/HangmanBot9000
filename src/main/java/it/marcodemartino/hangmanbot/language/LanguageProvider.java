package it.marcodemartino.hangmanbot.language;

import java.util.Locale;

public interface LanguageProvider {

    Locale getUserLanguage(long userId);
}
