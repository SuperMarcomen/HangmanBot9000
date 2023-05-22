package it.marcodemartino.hangmanbot.telegram.keyboard;

import io.github.ageofwar.telejam.inline.InlineKeyboardButton;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import it.marcodemartino.hangmanbot.telegram.buttons.NewMatchButton;
import it.marcodemartino.hangmanbot.telegram.buttons.SettingsButton;
import it.marcodemartino.hangmanbot.telegram.buttons.StaticsButton;

public class StartKeyboard {

    public static InlineKeyboardMarkup generate(long userId) {
        return new InlineKeyboardMarkup(new InlineKeyboardButton[][] {
                {
                        new NewMatchButton(userId),
                        new StaticsButton(userId)
                },
                {
                        new SettingsButton(userId)
                }
        });
    }
}
