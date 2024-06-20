package com.ikariscraft.cyclecare.api.responses;

public class PredictionJSONResponse {
    private String nextPeriodStartDate;
    private String nextPeriodEndDate;

    public PredictionJSONResponse() {
    }

    public PredictionJSONResponse(String nextPeriodStartDate, String nextPeriodEndDate) {
        this.nextPeriodStartDate = nextPeriodStartDate;
        this.nextPeriodEndDate = nextPeriodEndDate;
    }

    public String getNextPeriodStartDate() {
        return nextPeriodStartDate;
    }

    public void setNextPeriodStartDate(String nextPeriodStartDate) {
        this.nextPeriodStartDate = nextPeriodStartDate;
    }

    public String getNextPeriodEndDate() {
        return nextPeriodEndDate;
    }

    public void setNextPeriodEndDate(String nextPeriodEndDate) {
        this.nextPeriodEndDate = nextPeriodEndDate;
    }
}
