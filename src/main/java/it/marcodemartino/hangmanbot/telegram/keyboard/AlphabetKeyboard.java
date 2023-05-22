package it.marcodemartino.hangmanbot.telegram.keyboard;

import io.github.ageofwar.telejam.inline.CallbackDataInlineKeyboardButton;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;
import it.marcodemartino.hangmanbot.game.Match;

import java.util.List;

public class AlphabetKeyboard {

    public static InlineKeyboardMarkup generate(List<Character> alphabet, Match match) {
        CallbackDataInlineKeyboardButton[] buttons = new CallbackDataInlineKeyboardButton[alphabet.size()];
        for (int i = 0; i < alphabet.size(); i++) {
            Character letter = alphabet.get(i);
            String text = String.valueOf(letter);
            if (match.isLetterAlreadyGuessed(letter)) {
                text = "-";
            }
            buttons[i] = new CallbackDataInlineKeyboardButton(text, "letter_" + letter);
        }

        return InlineKeyboardMarkup.fromColumns(4, buttons);
    }
}
