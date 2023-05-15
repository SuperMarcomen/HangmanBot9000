package it.marcodemartino.hangmanbot.language;

import io.github.ageofwar.telejam.users.User;
import it.marcodemartino.hangmanbot.game.Match;

import java.util.Locale;
import java.util.ResourceBundle;

public class TelegramLanguages {

    private static final LanguageProvider languageProvider = new DummyLanguageProvider();
    private static final String NAME_OF_USER = "%username";
    private static final String WORD_CURRENT_STATUS = "%current_word";
    private static final String WORD_CATEGORY = "%category";
    private static final String LIVES = "%lives";


    public static String getParametirizedString(String key, long userId, User user, Match match) {
        String message = getString(key, userId);
        message = replaceUserVariable(message, user);
        return replaceMatchVariable(message, match);
    }

    public static String getParametirizedString(String key, long userId, User user) {
        return replaceUserVariable(getString(key, userId), user);
    }

    private static String replaceUserVariable(String message, User user) {
        return message.replace(NAME_OF_USER, user.getFirstName());
    }

    private static String replaceMatchVariable(String message, Match match) {
        return message
                .replace(WORD_CURRENT_STATUS, match.getCurrentStatusWord())
                .replace(WORD_CATEGORY, match.getCategory())
                .replace(LIVES, String.valueOf(match.getLives()));
    }

    public static String getString(String key, long userId) {
        Locale locale = getLocale(userId);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("telegram/messages", locale);
        return resourceBundle.getString(key);
    }

    public static Locale getLocale(long userId) {
        return languageProvider.getUserLanguage(userId);
    }
}
