package it.marcodemartino.hangmanbot.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class TelegramLanguages {

    private static final LanguageProvider languageProvider = new DummyLanguageProvider();

    public static String getString(String key, long userId) {
        Locale locale = languageProvider.getUserLanguage(userId);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("telegram/messages", locale);
        return resourceBundle.getString(key);
    }
}
