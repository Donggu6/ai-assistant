package com.deongeon.ai.admin.controller.ai.coach.util;

public class Extractors {

    public static Integer extractPrice(String msg) {
        String digits = msg.replaceAll("[^0-9]", "");
        return digits.isEmpty() ? null : Integer.parseInt(digits);
    }

    public static boolean contains(String msg, String... keywords) {
        for (String k : keywords) {
            if (msg.contains(k)) return true;
        }
        return false;
    }
}
