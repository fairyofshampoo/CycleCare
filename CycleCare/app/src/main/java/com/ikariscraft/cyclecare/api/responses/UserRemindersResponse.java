package com.ikariscraft.cyclecare.api.responses;

import com.ikariscraft.cyclecare.model.Reminder;

import java.util.List;

public class UserRemindersResponse {
    List<Reminder> reminders;

    public UserRemindersResponse(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public UserRemindersResponse() {
    }
}
