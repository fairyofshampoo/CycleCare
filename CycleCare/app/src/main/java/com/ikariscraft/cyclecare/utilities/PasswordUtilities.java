package com.ikariscraft.cyclecare.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtilities {
    public static String computeSHA256Hash(String password) {
        String textHashed;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] bytes = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte item : bytes) {
                String hex = String.format("%02x", item);
                stringBuilder.append(hex);
            }
            textHashed = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            textHashed = null;
        }

        return textHashed;
    }

}
