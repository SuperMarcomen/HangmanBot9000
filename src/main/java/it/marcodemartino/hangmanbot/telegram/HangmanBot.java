package it.marcodemartino.hangmanbot.telegram;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.LongPollingBot;
import it.marcodemartino.hangmanbot.game.Matches;
import it.marcodemartino.hangmanbot.game.database.Database;
import it.marcodemartino.hangmanbot.game.stats.UserStatsService;
import it.marcodemartino.hangmanbot.game.words.WordsProvider;
import it.marcodemartino.hangmanbot.game.words.WordsProviderTxt;
import it.marcodemartino.hangmanbot.language.TelegramLanguages;
import it.marcodemartino.hangmanbot.telegram.callback.BackStartCallback;
import it.marcodemartino.hangmanbot.telegram.callback.CategoryChosenCallback;
import it.marcodemartino.hangmanbot.telegram.callback.LetterClickCallback;
import it.marcodemartino.hangmanbot.telegram.callback.NewMatchCallback;
import it.marcodemartino.hangmanbot.telegram.callback.stats.PointsStatsCallback;
import it.marcodemartino.hangmanbot.telegram.callback.settings.ChooseLanguageCallback;
import it.marcodemartino.hangmanbot.telegram.callback.settings.LanguageChosenCallback;
import it.marcodemartino.hangmanbot.telegram.callback.settings.SettingsCallback;
import it.marcodemartino.hangmanbot.telegram.callback.stats.RatioStatsCallback;
import it.marcodemartino.hangmanbot.telegram.inline.InlineResults;

import java.io.IOException;

public class HangmanBot extends LongPollingBot {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Pass the token of the bot as an argument");
            return;
        }

        String token = args[0];
        Bot bot = Bot.fromToken(token);
        try (HangmanBot hangmanBot = new HangmanBot(bot, args)) {
            hangmanBot.run();
        }
    }

    public HangmanBot(Bot bot, String[] args) {
        super(bot);
        String password = "";
        if (args.length == 2) {
            password = args[1];
        }
        Database.initializeConnection(password);

        WordsProvider wordsProvider = new WordsProviderTxt();
        Matches matches = new Matches();
        UserStatsService userStatsService = new UserStatsService();
        TelegramLanguages.setUserDataDAO(userStatsService.getUserDataDAO());

        events.registerUpdateHandlers(
                new InlineResults(bot),
                new NewMatchCallback(bot, wordsProvider),
                new CategoryChosenCallback(bot, wordsProvider, matches, userStatsService.getUserStatsDAO()),
                new LetterClickCallback(bot, wordsProvider, matches, userStatsService.getUserStatsDAO(), userStatsService.getUserDataDAO()),
                new BackStartCallback(bot),
                new SettingsCallback(bot),
                new ChooseLanguageCallback(bot),
                new LanguageChosenCallback(bot, userStatsService.getUserDataDAO()),
                new PointsStatsCallback(bot, userStatsService),
                new RatioStatsCallback(bot, userStatsService)
        );
    }
}
