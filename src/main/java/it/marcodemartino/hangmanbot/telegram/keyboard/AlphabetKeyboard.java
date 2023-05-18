package it.marcodemartino.hangmanbot.telegram.keyboard;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;

import java.util.List;

public class AlphabetKeyboard {

    public static InlineKeyboardMarkup generate(List<Character> alphabet, List<Character> guessedLetters) {
        CallbackDataInlineKeyboardButton[] buttons = new CallbackDataInlineKeyboardButton[alphabet.size()];
        for (int i = 0; i < alphabet.size(); i++) {
            Character letter = alphabet.get(i);
            String text = String.valueOf(letter);
            if (guessedLetters.contains(Character.toLowerCase(letter))) {
                text = "-";
            }
            buttons[i] = new CallbackDataInlineKeyboardButton(text, "letter_" + letter);
        }

        return InlineKeyboardMarkup.fromColumns(4, buttons);
    }
}
