package it.marcodemartino.hangmanbot.telegram.callback.settings;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.methods.EditMessageText;
import it.marcodemartino.hangmanbot.game.stats.dao.DAO;
import it.marcodemartino.hangmanbot.game.stats.entities.UserData;

import java.util.Locale;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class LanguageChosenCallback extends LanguageCallback implements CallbackDataHandler {

    private final Bot bot;
    private final DAO<UserData> userDataDAO;

    public LanguageChosenCallback(Bot bot, DAO<UserData> userDataDAO) {
        super();
        this.bot = bot;
        this.userDataDAO = userDataDAO;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        String query = callbackQuery.getData().get();
        if (!query.startsWith("language")) return;

        long userId = callbackQuery.getSender().getId();
        String inlineMessageId = callbackQuery.getInlineMessageId().get();
        updateUserLanguage(userId, Locale.forLanguageTag(query.split("_")[1]));

        EditMessageText editMessageText = new EditMessageText()
                .text(getString("message_settings_choose", userId))
                .inlineMessage(inlineMessageId)
                .replyMarkup(generateSettingsKeyboard(userId));
        bot.execute(editMessageText);
    }

    private void updateUserLanguage(long userId, Locale locale) {
        UserData userData = userDataDAO.getOrCreate(userId);
        userData.setLocale(locale);
        userDataDAO.update(userData);
    }

}
