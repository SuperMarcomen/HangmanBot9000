package it.marcodemartino.hangmanbot.game.stats.entities;

public class UserStats {

    private final long userId;
    private int startedMatches;
    private int rightLetters;
    private int wrongLetters;

    public UserStats(long userId, int startedMatches, int rightLetters, int wrongLetters) {
        this.userId = userId;
        this.startedMatches = startedMatches;
        this.rightLetters = rightLetters;
        this.wrongLetters = wrongLetters;
    }

    public float getRatio() {
        if (rightLetters + wrongLetters == 0) return 0;
        return (float) rightLetters / ((float) rightLetters + (float) wrongLetters);
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

}
