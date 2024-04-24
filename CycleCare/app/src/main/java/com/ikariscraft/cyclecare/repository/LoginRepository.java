package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.data.Database;

public class LoginRepository {
    private final Database database;

    public LoginRepository(Database database){
        this.database = database;
    }
    public int login(String username, String hashedPassword) {
        int result = 1;

        return result;
    }
}
