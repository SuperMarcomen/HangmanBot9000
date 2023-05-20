package it.marcodemartino.hangmanbot.telegram.callback.settings;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.inline.InlineKeyboardButton;
import io.github.ageofwar.telejam.methods.EditMessageText;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import it.marcodemartino.hangmanbot.telegram.buttons.back.BackStartButton;
import it.marcodemartino.hangmanbot.telegram.buttons.language.LanguageMenuButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class SettingsCallback implements CallbackDataHandler {

    private final Bot bot;

    public SettingsCallback(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        if (!callbackQuery.getData().get().equals("settings") && !callbackQuery.getData().get().equals("back_settings")) return;

        long userId = callbackQuery.getSender().getId();
        String inlineMessageId = callbackQuery.getInlineMessageId().get();

        EditMessageText editMessageText = new EditMessageText()
                .text(getString("message_settings_choose", userId))
                .inlineMessage(inlineMessageId)
                .replyMarkup(generateSettingsKeyboard(userId));
        bot.execute(editMessageText);
    }

    private InlineKeyboardMarkup generateSettingsKeyboard(long userId) {
        return new InlineKeyboardMarkup(new InlineKeyboardButton[][] {
                {
                        new LanguageMenuButton(userId)
                },
                {
                        new BackStartButton(userId)
                }
        });
    }
}
