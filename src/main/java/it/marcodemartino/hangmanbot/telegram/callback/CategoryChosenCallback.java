package it.marcodemartino.hangmanbot.telegram.callback;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.methods.EditMessageText;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import io.github.ageofwar.telejam.text.Text;
import it.marcodemartino.hangmanbot.game.Match;
import it.marcodemartino.hangmanbot.game.Matches;
import it.marcodemartino.hangmanbot.game.stats.dao.DAO;
import it.marcodemartino.hangmanbot.game.stats.entities.UserStats;
import it.marcodemartino.hangmanbot.game.words.Words;
import it.marcodemartino.hangmanbot.game.words.WordsProvider;
import it.marcodemartino.hangmanbot.telegram.keyboard.AlphabetKeyboard;

import java.util.Locale;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getLocale;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getParametirizedString;

public class CategoryChosenCallback implements CallbackDataHandler {

    private final Bot bot;
    private final WordsProvider wordsProvider;
    private final Matches matches;
    private final DAO<UserStats> userStatsDAO;

    public CategoryChosenCallback(Bot bot, WordsProvider wordsProvider, Matches matches, DAO<UserStats> userStatsDAO) {
        this.bot = bot;
        this.wordsProvider = wordsProvider;
        this.matches = matches;
        this.userStatsDAO = userStatsDAO;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        String data = callbackQuery.getData().get();
        if (!data.startsWith("category")) return;

        String inlineMessageId = callbackQuery.getInlineMessageId().get();
        long userId = callbackQuery.getSender().getId();
        Locale locale = getLocale(userId);

        String category = data.split("_")[1];
        Match match = createMatch(locale, inlineMessageId, category);

        updateStartedMatches(userId);

        String message = getParametirizedString("message_match", userId, callbackQuery.getSender(), match);
        InlineKeyboardMarkup keyboard = AlphabetKeyboard.generate(wordsProvider.getAlphabetFromLocale(locale), match);

        EditMessageText editMessageText = new EditMessageText()
                .text(Text.parseHtml(message))
                .inlineMessage(inlineMessageId)
                .replyMarkup(keyboard);
        bot.execute(editMessageText);
    }

    private Match createMatch(Locale locale, String id, String category) {
        Words words = wordsProvider.getWordsFromLocale(locale);
        String randomWord = words.getRandomWordFromCategory(category);
        return matches.startNewMatchRandomWord(id, randomWord, category);
    }

    private void updateStartedMatches(long userId) {
        UserStats userStats = userStatsDAO.getOrCreate(userId);
        userStats.setStartedMatches(userStats.getStartedMatches() + 1);
        userStatsDAO.update(userStats);
    }
}
