package it.marcodemartino.hangmanbot.telegram.buttons;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class BackButton extends CallbackDataInlineKeyboardButton {

    public BackButton(long userId) {
        super(
                getString("button_back_start", userId),
                "back_start"
        );
    }
}
