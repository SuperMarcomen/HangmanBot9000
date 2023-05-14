package it.marcodemartino.hangmanbot.telegram.keyboard;

import io.github.ageofwar.telejam.inline.InlineKeyboardButton;
import io.github.ageofwar.telejam.replymarkups.InlineKeyboardMarkup;

import java.util.List;

public class ExtraButtonInlineKeyboardMarkup {
    public static InlineKeyboardMarkup fromColumns(int columns, InlineKeyboardButton extraButton, InlineKeyboardButton... buttons) {
        int rows = buttons.length / columns;
        int len = rows * columns == buttons.length ? rows : rows + 1;
        InlineKeyboardButton[][] keyboard = new InlineKeyboardButton[len+1][];
        for (int row = 0; row < rows; row++) {
            keyboard[row] = new InlineKeyboardButton[columns];
            System.arraycopy(buttons, row * columns, keyboard[row], 0, columns);
        }
        int remained = buttons.length - rows * columns;
        if (remained > 0) {
            keyboard[rows] = new InlineKeyboardButton[remained];
            System.arraycopy(buttons, rows * columns, keyboard[rows], 0, remained);
        }
        keyboard[len] = new InlineKeyboardButton[1];
        keyboard[len][0] = extraButton;
        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup fromColumnsWithExtraButton(int columns, InlineKeyboardButton extraButton, List<InlineKeyboardButton> buttons) {
        return fromColumns(columns, extraButton, buttons.toArray(new InlineKeyboardButton[0]));
    }
}
