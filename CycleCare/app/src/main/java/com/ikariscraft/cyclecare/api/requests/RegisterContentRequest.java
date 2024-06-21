package com.ikariscraft.cyclecare.api.requests;

import android.graphics.Bitmap;

import java.util.Date;

public class RegisterContentRequest {
    private String title;
    private String description;
    private String creationDate;
    private String image;

    public RegisterContentRequest() { }

    public RegisterContentRequest(String title, String description, String creationDate, String image) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
