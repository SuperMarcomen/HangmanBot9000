package it.marcodemartino.hangmanbot.game;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private static final int LIVES_AMOUNT = 5;
    private final String word;
    private final String category;
    private final List<Character> guessedLetters;
    private int lives;

    public Match(String word, String category) {
        this.word = word;
        this.category = category;
        guessedLetters = new ArrayList<>();
        lives = LIVES_AMOUNT;
    }

    public boolean isLetterAlreadyGuessed(char letter) {
        return guessedLetters.contains(Character.toLowerCase(letter));
    }

    public boolean guessLetter(char letter) {
        char lowerCaseLetter = Character.toLowerCase(letter);
        guessedLetters.add(letter);
        return word.contains(String.valueOf(lowerCaseLetter));
    }

    public void decreaseLife() {
        lives--;
    }

    public boolean isMatchEnded() {
        if (lives == 0) return true;
        return isWordGuessed();
    }

    private boolean isWordGuessed() {
        for (char c : word.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
