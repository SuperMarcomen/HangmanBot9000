package it.marcodemartino.hangmanbot.game.stats;

public class UserStats {

    private final long userId;
    private int startedMatches;
    private int rightLetters;
    private int wrongLetters;
    private int guessedWords;

    public UserStats(long userId, int startedMatches, int rightLetters, int wrongLetters, int guessedWords) {
        this.userId = userId;
        this.startedMatches = startedMatches;
        this.rightLetters = rightLetters;
        this.wrongLetters = wrongLetters;
        this.guessedWords = guessedWords;
    }

    public long getUserId() {
        return userId;
    }

    public int getStartedMatches() {
        return startedMatches;
    }

    public void setStartedMatches(int startedMatches) {
        this.startedMatches = startedMatches;
    }

    public int getRightLetters() {
        return rightLetters;
    }

    public void setRightLetters(int rightLetters) {
        this.rightLetters = rightLetters;
    }

    public int getWrongLetters() {
        return wrongLetters;
    }

    public void setWrongLetters(int wrongLetters) {
        this.wrongLetters = wrongLetters;
    }

    public int getGuessedWords() {
        return guessedWords;
    }

    public void setGuessedWords(int guessedWords) {
        this.guessedWords = guessedWords;
    }
}
