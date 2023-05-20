package it.marcodemartino.hangmanbot.game.stats.entities;

import java.util.Locale;

public class UserData {

    private final long userId;
    private String name;
    private Locale locale;

    public UserData(long userId, String name, Locale locale) {
        this.userId = userId;
        this.name = name;
        this.locale = locale;
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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
