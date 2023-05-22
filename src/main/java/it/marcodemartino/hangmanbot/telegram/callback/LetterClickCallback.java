package it.marcodemartino.hangmanbot.telegram.callback;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.methods.AnswerCallbackQuery;
import io.github.ageofwar.telejam.methods.EditMessageText;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import io.github.ageofwar.telejam.text.Text;
import it.marcodemartino.hangmanbot.game.Match;
import it.marcodemartino.hangmanbot.game.Matches;
import it.marcodemartino.hangmanbot.game.stats.dao.DAO;
import it.marcodemartino.hangmanbot.game.stats.entities.UserData;
import it.marcodemartino.hangmanbot.game.stats.entities.UserStats;
import it.marcodemartino.hangmanbot.game.words.WordsProvider;
import it.marcodemartino.hangmanbot.telegram.buttons.back.BackStartButton;
import it.marcodemartino.hangmanbot.telegram.keyboard.AlphabetKeyboard;

import java.util.Locale;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getLocale;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getParametirizedString;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class LetterClickCallback implements CallbackDataHandler {

    private final Bot bot;
    private final WordsProvider wordsProvider;
    private final Matches matches;
    private final DAO<UserStats> userStatsDAO;
    private final DAO<UserData> userDataDAO;


    public LetterClickCallback(Bot bot, WordsProvider wordsProvider, Matches matches, DAO<UserStats> userStatsDAO, DAO<UserData> userDataDAO) {
        this.bot = bot;
        this.wordsProvider = wordsProvider;
        this.matches = matches;
        this.userStatsDAO = userStatsDAO;
        this.userDataDAO = userDataDAO;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        String data = callbackQuery.getData().get();
        if (!data.startsWith("letter")) return;
        String inlineMessageId = callbackQuery.getInlineMessageId().get();
        Match match = matches.getMatchFromId(inlineMessageId);

        long userId = callbackQuery.getSender().getId();
        if (match == null) {
            AnswerCallbackQuery answerInlineQuery = new AnswerCallbackQuery()
                    .callbackQuery(callbackQuery)
                    .text(getString("message_no_match_found", userId));
            bot.execute(answerInlineQuery);
            return;
        }

        char letter = data.split("_")[1].charAt(0);

        if (match.isLetterAlreadyGuessed(letter)) {
            AnswerCallbackQuery answerInlineQuery = new AnswerCallbackQuery()
                    .callbackQuery(callbackQuery)
                    .text(getString("message_letter_already_guessed", userId));
            bot.execute(answerInlineQuery);
            return;
        }

        boolean result = match.guessLetter(letter);
        updateUserStats(userId, result);
        updateUserName(userId, callbackQuery.getSender().getFirstName());

        StringBuilder message = new StringBuilder(
                getParametirizedString("message_match", userId, callbackQuery.getSender(), match));

        Locale locale = getLocale(userId);
        InlineKeyboardMarkup keyboard;
        if (!match.isMatchEnded()) {
            keyboard = AlphabetKeyboard.generate(wordsProvider.getAlphabetFromLocale(locale), match);
        } else {
            keyboard = new InlineKeyboardMarkup(new BackStartButton(userId));
            matches.deleteMatch(inlineMessageId);
            if (match.getLives() == 0) {
                message.append(getParametirizedString("message_reveal_word", userId, callbackQuery.getSender(), match));
            } else {
                message.append(getString("message_match_won", userId));
            }
        }

        EditMessageText editMessageText = new EditMessageText()
                .inlineMessage(inlineMessageId)
                .replyMarkup(keyboard)
                .text(Text.parseHtml(message.toString()));
        bot.execute(editMessageText);

    }

    private void updateUserName(long userId, String name) {
        if (userDataDAO.isPresent(userId)) {
            UserData userData = userDataDAO.getOrCreate(userId);
            if (userData.getName().equals(name)) return;
            userData.setName(name);
            userDataDAO.update(userData);
        } else {
            UserData userData = new UserData(userId, name, Locale.ENGLISH);
            userDataDAO.insert(userData);
        }
    }

    private void updateUserStats(long userId, boolean result) {
        UserStats userStats = userStatsDAO.getOrCreate(userId);

        if (result) {
            userStats.setRightLetters(userStats.getRightLetters() + 1);
        } else {
            userStats.setWrongLetters(userStats.getWrongLetters() + 1);
        }
        userStatsDAO.update(userStats);
    }

}
