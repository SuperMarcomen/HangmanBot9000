package it.marcodemartino.hangmanbot.telegram.buttons.language;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import java.util.Locale;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getStringFromLocale;

public class LanguageButton extends CallbackDataInlineKeyboardButton {

    public LanguageButton(Locale locale) {
        super(
                getStringFromLocale("button_language", locale),
                "language_" + locale.getLanguage()
        );
    }
}
