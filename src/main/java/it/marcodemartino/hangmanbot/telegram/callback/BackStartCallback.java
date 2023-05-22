package it.marcodemartino.hangmanbot.telegram.callback;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.methods.EditMessageText;
import io.github.ageofwar.telejam.text.Text;
import it.marcodemartino.hangmanbot.telegram.keyboard.StartKeyboard;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getParametirizedString;

public class BackStartCallback implements CallbackDataHandler {

    private final Bot bot;

    public BackStartCallback(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        if (!callbackQuery.getData().get().equals("back_start")) return;

        long userId = callbackQuery.getSender().getId();
        String inlineMessageId = callbackQuery.getInlineMessageId().get();

        EditMessageText editMessageText = new EditMessageText()
                .text(Text.parseHtml(getParametirizedString("message_start", userId, callbackQuery.getSender())))
                .inlineMessage(inlineMessageId)
                .replyMarkup(StartKeyboard.generate(userId));
        bot.execute(editMessageText);
    }
}
