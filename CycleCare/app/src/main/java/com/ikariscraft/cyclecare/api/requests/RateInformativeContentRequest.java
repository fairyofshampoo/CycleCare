package com.ikariscraft.cyclecare.api.requests;

public class RateInformativeContentRequest {

    private int rating;

    public RateInformativeContentRequest(int rating) {
        this.rating = rating;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
