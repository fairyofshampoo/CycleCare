package com.ikariscraft.cyclecare.utilities;

public class Validations {

    public static boolean isPasswordValid(String password) {
        boolean isValidPassword = false;

        if (password != null) {
            isValidPassword = !password.isEmpty() && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$\n");
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

}
