package it.marcodemartino.hangmanbot.game.words;

import it.marcodemartino.hangmanbot.game.collections.RandomArrayList;
import it.marcodemartino.hangmanbot.game.collections.RandomHashMap;

public class Words {

    private final RandomHashMap<String, RandomArrayList<String>> words;

    public Words() {
        this.words = new RandomHashMap<>();
    }

    public void addWords(String category, RandomArrayList<String> words) {
        this.words.put(category, words);
    }

    public String getRandomCategory() {
        return words.getRandomKey();
    }

    public String getRandomWordFromCategory(String category) {
        return words.get(category).getRandomElement();
    }
}
