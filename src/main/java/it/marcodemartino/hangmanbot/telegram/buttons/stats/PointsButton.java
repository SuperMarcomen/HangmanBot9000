package it.marcodemartino.hangmanbot.telegram.buttons.stats;

public class PointsButton extends StatsButton {

    public PointsButton(boolean active, long userId) {
        super(active, userId, "button_statistics_points", "stats_points");
    }
}
