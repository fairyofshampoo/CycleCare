package com.ikariscraft.cyclecare.utilities;

import com.ikariscraft.cyclecare.model.Person;

public class Session {
    private static Session instance;
    private String token;
    private Person person;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static void setInstance(Session instance) {
        Session.instance = instance;
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
