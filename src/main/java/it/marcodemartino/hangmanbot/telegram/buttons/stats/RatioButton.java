package it.marcodemartino.hangmanbot.telegram.buttons.stats;

public class RatioButton extends StatsButton {

    public RatioButton(boolean active, long userId) {
        super(active, userId, "button_statistics_ratio", "stats_ratio");
    }
}
