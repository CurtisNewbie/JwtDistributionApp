package com.curtisnewbie.util;

import java.util.Random;

public class RandomGenerator {

    public String generateRandomString(int len) {
        var rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            // ASCII char sets
            sb.append((char) rand.nextInt(129));
        }
        return sb.toString();
    }
}