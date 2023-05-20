package it.marcodemartino.hangmanbot.telegram.buttons.back;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class BackSettingsButton extends CallbackDataInlineKeyboardButton {

    public BackSettingsButton(long userId) {
        super(
                getString("button_back_start", userId),
                "back_settings"
        );
    }
}
