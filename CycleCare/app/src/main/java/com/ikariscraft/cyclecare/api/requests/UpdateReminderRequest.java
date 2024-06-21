package com.ikariscraft.cyclecare.api.requests;

public class UpdateReminderRequest {

    private String description;

    private String title;

    private String  creationDate;

    private String scheduleId;


    public UpdateReminderRequest(){}

    public UpdateReminderRequest(String description, String title, String creationDate, String scheduleId) {
        this.description = description;
        this.title = title;
        this.creationDate = creationDate;
        this.scheduleId = scheduleId;
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

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}
