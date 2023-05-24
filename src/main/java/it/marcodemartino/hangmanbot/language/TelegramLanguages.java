package it.marcodemartino.hangmanbot.language;

import io.github.ageofwar.telejam.text.Text;
import it.marcodemartino.hangmanbot.game.Match;
import it.marcodemartino.hangmanbot.game.stats.dao.DAO;
import it.marcodemartino.hangmanbot.game.stats.entities.UserData;
import it.marcodemartino.hangmanbot.game.stats.entities.UserInfo;

import java.util.Locale;
import java.util.ResourceBundle;

public class TelegramLanguages {

    private static DAO<UserData> userDataDAO;
    private static final String NAME_OF_USER = "%username";
    private static final String WORD_CURRENT_STATUS = "%current_word";
    private static final String WORD_CATEGORY = "%category";
    private static final String WORD_TO_GUESS = "%word";
    private static final String LIVES = "%lives";
    private static final String RIGHT_LETTERS = "%right_letters";
    private static final String WRONG_LETTERS = "%wrong_letters";
    private static final String RATIO = "%ratio";
    private static final String POINTS = "%points";

    public static String getParameterizedStringStats(String key, long userId, UserInfo userInfo) {
        String message = getString(key, userId);
        message = replaceUserVariable(message, userInfo.getData().getName());
        message = message
                .replace(RIGHT_LETTERS, String.valueOf(userInfo.getStats().getRightLetters()))
                .replace(WRONG_LETTERS, String.valueOf(userInfo.getStats().getWrongLetters()))
                .replace(RATIO, String.format("%.2f", userInfo.getStats().getRatio()))
                .replace(POINTS, String.format("%.0f", userInfo.getStats().getPoints()));
        return message;
    }

    public static String getParametirizedString(String key, long userId, String name, Match match) {
        String message = getString(key, userId);
        message = replaceUserVariable(message, name);
        return replaceMatchVariable(message, match);
    }

    public static String getParametirizedString(String key, long userId, String name) {
        return replaceUserVariable(getString(key, userId), name);
    }

    private static String replaceUserVariable(String message, String name) {
        return message.replace(NAME_OF_USER, name);
    }

    private static String replaceMatchVariable(String message, Match match) {
        return message
                .replace(WORD_CURRENT_STATUS, match.getCurrentStatusWord())
                .replace(WORD_CATEGORY, match.getCategory())
                .replace(WORD_TO_GUESS, match.getWord())
                .replace(LIVES, String.valueOf(match.getLives()));
    }

    public static String getString(String key, long userId) {
        Locale locale = getLocale(userId);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("telegram/messages", locale);
        return resourceBundle.getString(key);
    }

    public static String getStringFromLocale(String key, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("telegram/messages", locale);
        return resourceBundle.getString(key);
    }

    public static Text getStringAsText(String key, long userId) {
        return Text.parseHtml(getString(key, userId));
    }

    public static Locale getLocale(long userId) {
        return userDataDAO.getOrCreate(userId).getLocale();
    }

    public static void setUserDataDAO(DAO<UserData> userDataDAO) {
        TelegramLanguages.userDataDAO = userDataDAO;
    }
}
