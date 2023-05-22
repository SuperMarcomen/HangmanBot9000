package it.marcodemartino.hangmanbot.telegram.callback;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;
import io.github.ageofwar.telejam.inline.InlineKeyboardButton;
import io.github.ageofwar.telejam.methods.EditMessageText;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import it.marcodemartino.hangmanbot.game.words.WordsProvider;
import it.marcodemartino.hangmanbot.telegram.buttons.back.BackStartButton;
import it.marcodemartino.hangmanbot.telegram.keyboard.ExtraButtonInlineKeyboardMarkup;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getLocale;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getStringAsText;

public class NewMatchCallback implements CallbackDataHandler {

    private final Bot bot;
    private final WordsProvider wordsProvider;

    public NewMatchCallback(Bot bot, WordsProvider wordsProvider) {
        this.bot = bot;
        this.wordsProvider = wordsProvider;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws IOException {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        if (!callbackQuery.getData().get().equals("new_match")) return;

        long userId = callbackQuery.getSender().getId();
        String inlineMessageId = callbackQuery.getInlineMessageId().get();
        Locale locale = getLocale(userId);
        Set<String> categories = wordsProvider.getCategoriesFromLocale(locale);

        EditMessageText editMessageText = new EditMessageText()
                .text(getStringAsText("message_choose_categories", userId))
                .inlineMessage(inlineMessageId)
                .replyMarkup(generateCategoriesKeyboard(categories, userId));
        bot.execute(editMessageText);
    }

    private InlineKeyboardMarkup generateCategoriesKeyboard(Set<String> categories, long userId) {
        List<InlineKeyboardButton> categoriesButtons = categories.stream()
                .map(category -> new CallbackDataInlineKeyboardButton(category, "category_" + category))
                .collect(Collectors.toList());
        return ExtraButtonInlineKeyboardMarkup.fromColumnsWithExtraButton(2, new BackStartButton(userId), categoriesButtons);
    }

}
