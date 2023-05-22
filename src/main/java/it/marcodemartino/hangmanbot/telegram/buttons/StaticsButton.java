package it.marcodemartino.hangmanbot.telegram.buttons;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class StaticsButton extends CallbackDataInlineKeyboardButton {

    public StaticsButton(long userId) {
        super(
                getString("button_statistics", userId),
                "stats"
        );
    }
}