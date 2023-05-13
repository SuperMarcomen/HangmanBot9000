package it.marcodemartino.hangmanbot.telegram.buttons;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class SettingsButton extends CallbackDataInlineKeyboardButton {

    public SettingsButton(long userId) {
        super(
                getString("button_settings", userId),
                "settings"
        );
    }
}
