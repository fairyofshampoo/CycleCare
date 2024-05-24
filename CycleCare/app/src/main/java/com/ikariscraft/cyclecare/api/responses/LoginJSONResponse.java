package com.ikariscraft.cyclecare.api.responses;


public class LoginJSONResponse {
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String role;
    private String email;
    private String token;

    public LoginJSONResponse(){}

    public LoginJSONResponse(String name, String firstLastName, String secondLastName, String role, String email, String token) {
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.role = role;
        this.email = email;
        this.token = token;
    }

    public String getName() {
        return name;
    }


    public String getFirstLastName() {
        return firstLastName;
    }


    public String getSecondLastName() {
        return secondLastName;
    }


    public String getRole() {
        return role;
    }


    public String getEmail() {
        return email;
    }


    public String getToken() {
        return token;
    }

}
