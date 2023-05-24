package it.marcodemartino.hangmanbot.telegram.callback.stats;

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
import it.marcodemartino.hangmanbot.telegram.buttons.stats.PointsButton;
import it.marcodemartino.hangmanbot.telegram.buttons.stats.RatioButton;

import java.util.List;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getParameterizedStringStats;
import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class RatioStatsCallback implements CallbackDataHandler {

    private final Bot bot;
    private final UserStatsService userStatsService;

    public RatioStatsCallback(Bot bot, UserStatsService userStatsService) {
        this.bot = bot;
        this.userStatsService = userStatsService;
    }

    @Override
    public void onCallbackData(CallbackQuery callbackQuery, String name, String args) throws Throwable {
        if (callbackQuery.getData().isEmpty()) return;
        if (callbackQuery.getInlineMessageId().isEmpty()) return;
        String callbackData = callbackQuery.getData().get();
        if (!callbackData.equals("stats_ratio")) return;

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
        return new InlineKeyboardMarkup(new InlineKeyboardButton[][] {
                {
                        new PointsButton(false, userId),
                        new RatioButton(true, userId)
                },
                {
                        new BackStartButton(userId)
                }
        });
    }

    private String generateMessageTopPlayers(int limit, long userId) {
        StringBuilder message = new StringBuilder();
        message.append(getString("message_stats_title", userId));

        List<UserInfo> stats = userStatsService.getTopPlayersRatio(limit);
        if (!statsContainsUser(userId, stats)) {
            message.append(getParameterizedStringStats("message_stats_your_ratio", userId, userStatsService.getUserInfoOf(userId)));
            message.append(System.lineSeparator());
        }

        for (UserInfo topPlayer : stats) {
            message.append(getParameterizedStringStats("message_stats_ratio_line", userId, topPlayer));
            message.append(System.lineSeparator());
        }
        return message.toString();
    }

    private boolean statsContainsUser(long userId, List<UserInfo> stats) {
        for (UserInfo stat : stats) {
            if (stat.getStats().getUserId() == userId) return true;
        }
        return false;
    }
}