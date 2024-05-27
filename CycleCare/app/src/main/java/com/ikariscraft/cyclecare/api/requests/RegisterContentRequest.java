package com.ikariscraft.cyclecare.api.requests;

import android.graphics.Bitmap;

import java.util.Date;

public class RegisterContentRequest {
    private String title;
    private String description;
    private Date creationDate;
    private Bitmap image;

    public RegisterContentRequest() { }

    public RegisterContentRequest(String title, String description, Date creationDate, Bitmap image) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
