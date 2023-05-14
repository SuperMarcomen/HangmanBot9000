package it.marcodemartino.hangmanbot.game.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class RandomArrayList<E> extends ArrayList<E> {

    private final Random random;

    public RandomArrayList() {
        random = new Random();
    }

    public RandomArrayList(Collection<? extends E> c) {
        super(c);
        random = new Random();
    }

    public E getRandomElement() {
        int randomInt = random.nextInt(size());
        return get(randomInt);
    }
}
