package com.deongeon.ai.global.util;

public final class EmailMasker {

    private EmailMasker() {}

    public static String mask(String email) {
        if (email == null || !email.contains("@")) return "***";

        String[] parts = email.split("@", 2);
        String local = parts[0];
        String domain = parts[1];

        if (local.length() <= 2) {
            return "**@" + domain;
        }

        return local.substring(0, 2) + "***@" + domain;
    }
}
