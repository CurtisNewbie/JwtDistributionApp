package com.curtisnewbie.util;

import java.util.Random;

import javax.enterprise.context.Dependent;

@Dependent
public class RandomGenerator {

    public String generateRandomString(int len) {
        var rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            // ASCII char sets, Only capital english
            sb.append((char) (65 + rand.nextInt(26)));
        }
        return sb.toString();
    }
}