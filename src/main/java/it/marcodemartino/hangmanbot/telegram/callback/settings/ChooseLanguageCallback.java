package it.marcodemartino.hangmanbot.telegram.callback.settings;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.methods.EditMessageText;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getStringAsText;

public class ChooseLanguageCallback extends LanguageCallback implements CallbackDataHandler {

    private final Bot bot;

    public ChooseLanguageCallback(Bot bot) {
        super();
        this.bot = bot;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        if (!callbackQuery.getData().get().equals("button_language_menu")) return;

        long userId = callbackQuery.getSender().getId();
        String inlineMessageId = callbackQuery.getInlineMessageId().get();

        EditMessageText editMessageText = new EditMessageText()
                .text(getStringAsText("message_settings_choose_language", userId))
                .inlineMessage(inlineMessageId)
                .replyMarkup(generateSettingsKeyboard(userId));
        bot.execute(editMessageText);
    }
}
