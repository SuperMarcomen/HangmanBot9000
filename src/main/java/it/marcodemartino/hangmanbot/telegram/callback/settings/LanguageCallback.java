package it.marcodemartino.hangmanbot.telegram.callback.settings;

import io.github.ageofwar.telejam.inline.InlineKeyboardButton;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import it.marcodemartino.hangmanbot.telegram.buttons.back.BackSettingsButton;
import it.marcodemartino.hangmanbot.telegram.buttons.language.LanguageButton;
import it.marcodemartino.hangmanbot.telegram.keyboard.ExtraButtonInlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class LanguageCallback {

    private final Set<Locale> availableLocales;

    public LanguageCallback() {
        this.availableLocales = getLocales();
    }

    protected Set<Locale> getLocales() {
        Set<Locale> resourceBundles = new HashSet<>();

        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("telegram/messages", locale);
                if (!resourceBundle.getLocale().equals(locale)) continue;
                resourceBundles.add(locale);
            } catch (MissingResourceException ignored) { }
        }

        return Collections.unmodifiableSet(resourceBundles);
    }

    protected InlineKeyboardMarkup generateSettingsKeyboard(long userId) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (Locale locale : availableLocales) {
            buttons.add(new LanguageButton(locale));
        }

        return ExtraButtonInlineKeyboardMarkup.fromColumnsWithExtraButton(2,  new BackSettingsButton(userId), buttons);
    }
}
