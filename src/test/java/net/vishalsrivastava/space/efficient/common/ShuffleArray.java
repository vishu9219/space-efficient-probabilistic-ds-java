package net.vishalsrivastava.space.efficient.common;

import java.util.Random;

public class ShuffleArray {

    private ShuffleArray() {
    }

    public static <T> void shuffle(T[] array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            T temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
    }
}
