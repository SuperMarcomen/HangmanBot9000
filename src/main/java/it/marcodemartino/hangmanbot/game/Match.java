package it.marcodemartino.hangmanbot.game;

import java.text.Collator;

public class Match {

    private static final int LIVES_AMOUNT = 5;
    private static final int ALPHABET_SIZE = 26;
    public static final char WORD_FILLER = '-';
    private final String word;
    private final String category;
    private final char[] guessedLetters;
    private final Collator collator;
    private int writeIndex;
    private int lives;

    public Match(String word, String category) {
        this.word = word;
        this.category = category;
        guessedLetters = new char[ALPHABET_SIZE];
        collator = Collator.getInstance();
        collator.setDecomposition(2);
        collator.setStrength(0);
        writeIndex = 0;
        lives = LIVES_AMOUNT;
    }

    public boolean isLetterAlreadyGuessed(char letter) {
        for (char guessedLetter : guessedLetters) {
            if (areLettersIdentical(guessedLetter, letter)) return true;
        }
        return false;
    }

    public boolean guessLetter(char letter) {
        guessedLetters[writeIndex++] = letter;
        boolean result = doesWordContainLetter(letter);

        if (!result) decreaseLives();
        return result;
    }

    public boolean isMatchEnded() {
        if (lives == 0) return true;
        return isWordGuessed();
    }

    public String getCurrentStatusWord() {
        StringBuilder currentWord = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (Character.isSpaceChar(c) || !Character.isAlphabetic(c)) {
                currentWord.append(c);
                continue;
            }
            if (isLetterAlreadyGuessed(c)) {
                currentWord.append(c);
            } else {
                currentWord.append(WORD_FILLER);
            }
        }
        return currentWord.toString();
    }

    private boolean areLettersIdentical(char first, char second) {
        int result = collator.compare(String.valueOf(first), String.valueOf(second));
        return  result == 0;
    }

    private boolean doesWordContainLetter(char letter) {
        boolean result = false;
        for (char c : word.toCharArray()) {
            if (areLettersIdentical(c, letter)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void decreaseLives() {
        lives--;
    }

    private boolean isWordGuessed() {
        for (char c : word.toCharArray()) {
            if (Character.isSpaceChar(c) || !Character.isAlphabetic(c)) continue;
            if (!isLetterAlreadyGuessed(c)) {
                return false;
            }
        }
        return true;
    }

    public String getWord() {
        return word;
    }

    public String getCategory() {
        return category;
    }

    public int getLives() {
        return lives;
    }

}
