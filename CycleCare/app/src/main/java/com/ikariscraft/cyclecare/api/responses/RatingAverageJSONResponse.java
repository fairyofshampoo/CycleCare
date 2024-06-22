package com.ikariscraft.cyclecare.api.responses;

public class RatingAverageJSONResponse {
    double contentAVG;

    public RatingAverageJSONResponse() {
    }

    public RatingAverageJSONResponse(double contentAVG) {
        this.contentAVG = contentAVG;
    }

    public double getAverage() {
        return contentAVG;
    }

    public void setAverage(double contentAVG) {
        this.contentAVG = contentAVG;
    }
}
