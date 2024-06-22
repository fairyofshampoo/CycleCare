package com.ikariscraft.cyclecare.api.responses;

public class GetRateJSONResponse {
    private int value;

    public  GetRateJSONResponse(){}

    public GetRateJSONResponse(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
