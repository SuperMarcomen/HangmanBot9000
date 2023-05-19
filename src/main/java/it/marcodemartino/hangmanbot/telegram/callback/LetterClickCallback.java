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
import it.marcodemartino.hangmanbot.game.words.WordsProvider;
import it.marcodemartino.hangmanbot.telegram.buttons.BackButton;
import it.marcodemartino.hangmanbot.telegram.keyboard.AlphabetKeyboard;

import java.util.Locale;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getLocale;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getParametirizedString;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class LetterClickCallback implements CallbackDataHandler {

    private final Bot bot;
    private final WordsProvider wordsProvider;
    private final Matches matches;

    public LetterClickCallback(Bot bot, WordsProvider wordsProvider, Matches matches) {
        this.bot = bot;
        this.wordsProvider = wordsProvider;
        this.matches = matches;
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
        String message = getParametirizedString("message_match", userId, callbackQuery.getSender(), match);

        EditMessageText editMessageText = new EditMessageText()
                .text(Text.parseHtml(message))
                .inlineMessage(inlineMessageId);

        Locale locale = getLocale(userId);
        InlineKeyboardMarkup keyboard;
        if (!match.isMatchEnded()) {
            keyboard = AlphabetKeyboard.generate(wordsProvider.getAlphabetFromLocale(locale), match.getGuessedLetters());
        } else {
            keyboard = new InlineKeyboardMarkup(new BackButton(userId));
            matches.deleteMatch(inlineMessageId);
        }
        editMessageText.replyMarkup(keyboard);
        bot.execute(editMessageText);

    }


}
