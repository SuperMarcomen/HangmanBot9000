package it.marcodemartino.hangmanbot.telegram.buttons;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class NewMatchButton extends CallbackDataInlineKeyboardButton {

    public NewMatchButton(long userId) {
        super(
                getString("button_new_match", userId),
                "start"
        );
    }
}
