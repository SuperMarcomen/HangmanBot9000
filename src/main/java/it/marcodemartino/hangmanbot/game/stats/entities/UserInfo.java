package it.marcodemartino.hangmanbot.game.stats.entities;

public class UserInfo {

    private final UserStats stats;
    private final UserData data;

    public UserInfo(UserStats stats, UserData data) {
        this.stats = stats;
        this.data = data;
    }

    public UserStats getStats() {
        return stats;
    }

    public UserData getData() {
        return data;
    }
}
