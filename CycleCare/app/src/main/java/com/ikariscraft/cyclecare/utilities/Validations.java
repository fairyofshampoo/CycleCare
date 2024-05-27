package com.ikariscraft.cyclecare.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    public static boolean isPasswordValid(String password) {
        boolean isValidPassword = false;

        if (password != null) {
            String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";

            Pattern pattern = Pattern.compile(passwordPattern);
            Matcher matcher = pattern.matcher(password);

            isValidPassword = matcher.matches();
        }
        return isValidPassword;
    }

    public static boolean isNameValid(String name) {
        boolean isValidName = false;

        if (name != null) {
            isValidName = !name.isEmpty() && name.matches("[a-zA-Z\\s]+");
        }

        return isValidName;
    }

    public static boolean isUsernameValid(String username) {
        boolean isValidUsername = false;

        if (username != null) {
            isValidUsername = !username.isEmpty() && username.length() >= 3 && username.length() <= 20 && username.matches("[a-zA-Z0-9._]+");
        }

        return isValidUsername;
    }

    public static boolean isNotEmpty(String data) {
        boolean isNotEmpty = false;

        if (data != null) {
            isNotEmpty = !data.isEmpty();
        }
        return isNotEmpty;
    }

    public static boolean isValidEmail(String text) {
        boolean isValidEmail = false;

        if (text != null) {
            String emailPattern = "^[A-Za-z0-9+_.-]{1,64}@[A-Za-z0-9.-]{1,63}$";

            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(text);

            isValidEmail = matcher.matches();
        }

        return isValidEmail;
    }

}
