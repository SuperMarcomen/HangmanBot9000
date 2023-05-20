package it.marcodemartino.hangmanbot.telegram.buttons.language;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class LanguageMenuButton extends CallbackDataInlineKeyboardButton {

    public LanguageMenuButton(long userId) {
        super(
                getString("button_language_menu", userId),
                "button_language_menu"
        );
    }
}
