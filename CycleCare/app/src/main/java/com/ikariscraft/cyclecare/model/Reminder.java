package com.ikariscraft.cyclecare.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Reminder implements Parcelable {
    private String title;
    private String description;
    private String creationDate;
    private Integer reminderId;
    private String scheduleId;
    private String username;

    public Reminder() {
    }

    protected Reminder(Parcel in) {
        title = in.readString();
        description = in.readString();
        creationDate = in.readString();
        if (in.readByte() == 0) {
            reminderId = null;
        } else {
            reminderId = in.readInt();
        }
        scheduleId = in.readString();
        username = in.readString();
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reminder)) return false;
        Reminder reminder = (Reminder) o;
        return Objects.equals(getTitle(), reminder.getTitle()) && Objects.equals(getDescription(), reminder.getDescription()) && Objects.equals(getCreationDate(), reminder.getCreationDate()) && Objects.equals(getReminderId(), reminder.getReminderId()) && Objects.equals(getScheduleId(), reminder.getScheduleId()) && Objects.equals(getUsername(), reminder.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getCreationDate(), getReminderId(), getScheduleId(), getUsername());
    }

    public Reminder(String title, String description, String creationDate, Integer reminderId, String scheduleId, String username) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.reminderId = reminderId;
        this.scheduleId = scheduleId;
        this.username = username;
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

    public Integer getReminderId() {
        return reminderId;
    }

    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(creationDate);
        if (reminderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reminderId);
        }
        dest.writeString(scheduleId);
        dest.writeString(username);
    }
}
