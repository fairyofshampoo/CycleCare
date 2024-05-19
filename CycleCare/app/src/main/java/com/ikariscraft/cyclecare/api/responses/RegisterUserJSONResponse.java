package com.ikariscraft.cyclecare.api.responses;

public class RegisterUserJSONResponse {
    private String email;
    private String message;

    public RegisterUserJSONResponse(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emmail) {
        this.email = emmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
