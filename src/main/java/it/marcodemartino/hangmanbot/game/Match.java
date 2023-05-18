package it.marcodemartino.hangmanbot.game;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private static final int LIVES_AMOUNT = 5;
    public static final char WORD_FILLER = '-';
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
        guessedLetters.add(lowerCaseLetter);
        boolean result = word.toLowerCase().contains(String.valueOf(lowerCaseLetter));
        if (!result) decreaseLives();
        return result;
    }

    private void decreaseLives() {
        lives--;
    }

    public boolean isMatchEnded() {
        if (lives == 0) return true;
        return isWordGuessed();
    }

    public String getCurrentStatusWord() {
        StringBuilder currentWord = new StringBuilder();
        for (char c : word.toCharArray()) {
            char lowerCase = Character.toLowerCase(c);
            if (guessedLetters.contains(lowerCase)) {
                currentWord.append(c);
            } else {
                currentWord.append(WORD_FILLER);
            }
        }
        return currentWord.toString();
    }

    private boolean isWordGuessed() {
        for (char c : word.toCharArray()) {
            if (!guessedLetters.contains(Character.toLowerCase(c))) {
                return false;
            }
        }
        return true;
    }

    public String getCategory() {
        return category;
    }

    public int getLives() {
        return lives;
    }

    public List<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
