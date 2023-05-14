package it.marcodemartino.hangmanbot.game.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomHashMap<V, K> extends HashMap<V, K> {

    private final List<V> keys;
    private final Random random;

    public RandomHashMap() {
        keys = new ArrayList<>();
        random = new Random();
    }

    public V getRandomKey() {
        int randomInt = random.nextInt(keys.size());
        return keys.get(randomInt);
    }

    @Override
    public K put(V key, K value) {
        keys.add(key);
        return super.put(key, value);
    }

    @Override
    public K remove(Object key) {
        keys.remove(key);
        return super.remove(key);
    }
}
