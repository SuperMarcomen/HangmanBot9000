package it.marcodemartino.hangmanbot.telegram.callback;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.methods.EditMessageText;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import io.github.ageofwar.telejam.text.Text;
import it.marcodemartino.hangmanbot.game.Match;
import it.marcodemartino.hangmanbot.game.Matches;
import it.marcodemartino.hangmanbot.game.words.Words;
import it.marcodemartino.hangmanbot.game.words.WordsProvider;
import it.marcodemartino.hangmanbot.telegram.keyboard.AlphabetKeyboard;

import java.util.Collections;
import java.util.Locale;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getLocale;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getParametirizedString;

public class CategoryChosenCallback implements CallbackDataHandler {

    private final Bot bot;
    private final WordsProvider wordsProvider;
    private final Matches matches;

    public CategoryChosenCallback(Bot bot, WordsProvider wordsProvider, Matches matches) {
        this.bot = bot;
        this.wordsProvider = wordsProvider;
        this.matches = matches;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        String data = callbackQuery.getData().get();
        if (!data.startsWith("category")) return;

        String category = data.split("_")[1];
        String inlineMessageId = callbackQuery.getInlineMessageId().get();
        long userId = callbackQuery.getSender().getId();
        Locale locale = getLocale(userId);
        Words words = wordsProvider.getWordsFromLocale(locale);
        String randomWord = words.getRandomWordFromCategory(category);
        Match match = matches.startNewMatchRandomWord(inlineMessageId, randomWord, category);

        String message = getParametirizedString("message_match", userId, callbackQuery.getSender(), match);
        InlineKeyboardMarkup keyboard = AlphabetKeyboard.generate(wordsProvider.getAlphabetFromLocale(locale), Collections.emptyList());

        EditMessageText editMessageText = new EditMessageText()
                .text(Text.parseHtml(message))
                .inlineMessage(inlineMessageId)
                .replyMarkup(keyboard);
        bot.execute(editMessageText);
    }
}
