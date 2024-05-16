package org.example.util;

import java.util.Random;

public class Randomizer {
    private static final Random random = new Random();
    public static int inInterval(int min, int max) {
        return random.nextInt(max-min) + min;
    }
}
