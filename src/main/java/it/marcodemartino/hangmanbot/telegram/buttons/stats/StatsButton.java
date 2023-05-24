package it.marcodemartino.hangmanbot.telegram.buttons.stats;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;

import static it.marcodemartino.hangmanbot.language.TelegramLanguages.getString;

public class StatsButton extends CallbackDataInlineKeyboardButton {

    public StatsButton(boolean active, long userId, String key, String callback) {
        super(
                generateText(active, userId, key),
                active ? "already_active" : callback
        );
    }

    private static String generateText(boolean active, long userId, String key) {
        String emoji = getEmoji(active, userId);
        String message = getString(key, userId);
        return emoji + " " + message;
    }

    private static String getEmoji(boolean active, long userId) {
        if (active) {
            return getString("button_status_disabled", userId);
        } else {
            return getString("button_status_enabled", userId);
        }
    }
}
