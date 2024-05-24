package com.ikariscraft.cyclecare.utilities;

import com.ikariscraft.cyclecare.model.Person;

public class SessionSingleton {
    private static SessionSingleton instance;
    private String token;
    private Person person;

    private SessionSingleton() {
    }

    public static SessionSingleton getInstance() {
        if (instance == null) {
            instance = new SessionSingleton();
        }
        return instance;
    }

    public static void setInstance(SessionSingleton instance) {
        SessionSingleton.instance = instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
