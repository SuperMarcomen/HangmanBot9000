package it.marcodemartino.hangmanbot.game.stats.entities;

public class UserData {

    private final long userId;
    private String name;

    public UserData(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
