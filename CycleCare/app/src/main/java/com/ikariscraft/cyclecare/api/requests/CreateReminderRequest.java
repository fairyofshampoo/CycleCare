package com.ikariscraft.cyclecare.api.requests;

public class CreateReminderRequest {

    private String description;
    private String title;
    private String creationDate;

    public CreateReminderRequest(){}

    public CreateReminderRequest(String description, String title, String creationDate) {
        this.description = description;
        this.title = title;
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
