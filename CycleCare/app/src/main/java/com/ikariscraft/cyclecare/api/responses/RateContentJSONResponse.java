package com.ikariscraft.cyclecare.api.responses;

public class RateContentJSONResponse {
    private Boolean isError;
    private String statusCode;
    private String details;

    public RateContentJSONResponse(){

    }

    public RateContentJSONResponse(Boolean isError, String statusCode, String details) {
        this.isError = isError;
        this.statusCode = statusCode;
        this.details = details;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
