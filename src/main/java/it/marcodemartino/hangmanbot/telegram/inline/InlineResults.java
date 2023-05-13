package it.marcodemartino.hangmanbot.telegram.inline;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.inline.ChosenInlineResult;
import io.github.ageofwar.telejam.inline.InlineKeyboardButton;
import io.github.ageofwar.telejam.inline.InlineQuery;
import io.github.ageofwar.telejam.inline.InlineQueryHandler;
import io.github.ageofwar.telejam.inline.InlineQueryResult;
import io.github.ageofwar.telejam.inline.InlineQueryResultArticle;
import io.github.ageofwar.telejam.inline.InputTextMessageContent;
import io.github.ageofwar.telejam.methods.AnswerInlineQuery;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import io.github.ageofwar.telejam.text.Text;
import it.marcodemartino.hangmanbot.telegram.buttons.NewMatchButton;
import it.marcodemartino.hangmanbot.telegram.buttons.SettingsButton;

import java.io.IOException;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class InlineResults implements InlineQueryHandler {

    private final Bot bot;

    public InlineResults(Bot bot) {
        this.bot = bot;
    }


    @Override
    public void onChosenInlineResult(ChosenInlineResult chosenInlineResult) throws Throwable {
        InlineQueryHandler.super.onChosenInlineResult(chosenInlineResult);
    }

    @Override
    public void onInlineQuery(InlineQuery inlineQuery) throws IOException {
        long userId = inlineQuery.getSender().getId();
        String query = inlineQuery.getQuery();
        InlineQueryResult[] inlineQueryResults = generateInlineKeyboard(userId, query);

        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery()
                .inlineQuery(inlineQuery)
                .cacheTime(0)
                .results(inlineQueryResults);
        bot.execute(answerInlineQuery);
    }

    private InlineQueryResult[] generateInlineKeyboard(long userId, String query) {
        InlineQueryResult inlineResult = new InlineQueryResultArticle(
                "start",
                getString("inline_start_title", userId),
                new InputTextMessageContent(Text.parseHtml(getString("message_start", userId)), false),
                generateStartKeyboard(userId),
                getString("inline_start_description", userId)
        );
        return new InlineQueryResult[] { inlineResult };
    }

    private InlineKeyboardMarkup generateStartKeyboard(long userId) {
        return new InlineKeyboardMarkup(new InlineKeyboardButton[] {
                new NewMatchButton(userId),
                new SettingsButton(userId)
        });
    }


}
