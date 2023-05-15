package it.marcodemartino.hangmanbot.game;

import java.util.HashMap;
import java.util.Map;

public class Matches {

    private final Map<String, Match> matches;

    public Matches() {
        matches = new HashMap<>();
    }

    public Match startNewMatchRandomWord(String identifier, String word, String category) {
        Match match = new Match(word, category);
        matches.put(identifier, match);
        return match;
    }
}
