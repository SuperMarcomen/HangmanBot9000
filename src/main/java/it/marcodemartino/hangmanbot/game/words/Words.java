package it.marcodemartino.hangmanbot.game.words;

import it.marcodemartino.hangmanbot.game.collections.RandomArrayList;
import it.marcodemartino.hangmanbot.game.collections.RandomHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Words {

    private final RandomHashMap<String, RandomArrayList<String>> words;
    private final List<Character> alphabet;

    public Words() {
        this.words = new RandomHashMap<>();
        alphabet = new ArrayList<>();
    }

    public void addWords(String category, RandomArrayList<String> words) {
        this.words.put(category, words);
    }

    public void addAlphabetCharacters(List<String> alphabet) {
        for (String string : alphabet) {
            this.alphabet.add(string.charAt(0));
        }
    }

    public Set<String> getCategories() {
        return words.keySet();
    }

    public String getRandomCategory() {
        return words.getRandomKey();
    }

    public String getRandomWordFromCategory(String category) {
        return words.get(category).getRandomElement();
    }

    public List<Character> getAlphabet() {
        return alphabet;
    }
}
