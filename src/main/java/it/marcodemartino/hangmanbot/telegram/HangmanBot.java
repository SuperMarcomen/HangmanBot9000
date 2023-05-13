package it.marcodemartino.hangmanbot.telegram;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.LongPollingBot;
import it.marcodemartino.hangmanbot.telegram.inline.InlineResults;

import java.io.IOException;

public class HangmanBot extends LongPollingBot {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Pass the token of the bot as an argument");
        }

        String token = args[0];
        Bot bot = Bot.fromToken(token);
        HangmanBot hangmanBot = new HangmanBot(bot);
        hangmanBot.run();
    }

    public HangmanBot(Bot bot) {
        super(bot);
        events.registerUpdateHandler(
                new InlineResults(bot)
        );
    }
}
