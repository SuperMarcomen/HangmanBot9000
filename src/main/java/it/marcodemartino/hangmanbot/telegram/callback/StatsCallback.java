package it.marcodemartino.hangmanbot.telegram.callback;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.callbacks.CallbackDataHandler;
import io.github.ageofwar.telejam.callbacks.CallbackQuery;
import io.github.ageofwar.telejam.inline.InlineKeyboardButton;
import io.github.ageofwar.telejam.methods.EditMessageText;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import io.github.ageofwar.telejam.text.Text;
import it.marcodemartino.hangmanbot.game.stats.UserStatsService;
import it.marcodemartino.hangmanbot.game.stats.entities.UserInfo;
import it.marcodemartino.hangmanbot.telegram.buttons.back.BackStartButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getParameterizedStringStats;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class StatsCallback implements CallbackDataHandler {

    private final Bot bot;
    private final UserStatsService userStatsService;

    public StatsCallback(Bot bot, UserStatsService userStatsService) {
        this.bot = bot;
        this.userStatsService = userStatsService;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        if (!callbackQuery.getData().get().equals("stats")) return;

        long userId = callbackQuery.getSender().getId();
        String inlineMessageId = callbackQuery.getInlineMessageId().get();

        String message = generateMessageTopPlayers(5, userId);
        EditMessageText editMessageText = new EditMessageText()
                .text(Text.parseHtml(message))
                .inlineMessage(inlineMessageId)
                .replyMarkup(generateKeyboard(userId));
        bot.execute(editMessageText);
    }

    private InlineKeyboardMarkup generateKeyboard(long userId) {
        return new InlineKeyboardMarkup(new InlineKeyboardButton[] {
                new BackStartButton(userId)
        });
    }

    private String generateMessageTopPlayers(int limit, long userId) {
        StringBuilder message = new StringBuilder();
        message.append(getString("message_stats_title", userId));

        for (UserInfo topPlayer : userStatsService.getTopPlayers(limit)) {
            message.append(getParameterizedStringStats("message_stats_line", userId, topPlayer));
        }
        return message.toString();
    }
}
