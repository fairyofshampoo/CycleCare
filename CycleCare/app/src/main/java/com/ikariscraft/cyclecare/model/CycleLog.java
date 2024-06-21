package com.ikariscraft.cyclecare.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class CycleLog implements Parcelable {

    private Integer cycleLogId;
    private String creationDate;
    private Integer menstrualFlowId;
    private Integer vaginalFlowId;
    private Integer sleepHours;
    private String username;
    private String note;
    private List<Symptom> symptoms;
    private List<Mood> moods;
    private List<Medication> medications;
    private List<Pill> pills;
    private List<BirthControl> birthControl;

    public CycleLog() {
    }

    protected CycleLog(Parcel in) {
        if (in.readByte() == 0) {
            cycleLogId = null;
        } else {
            cycleLogId = in.readInt();
        }
        creationDate = in.readString();
        if (in.readByte() == 0) {
            menstrualFlowId = null;
        } else {
            menstrualFlowId = in.readInt();
        }
        if (in.readByte() == 0) {
            vaginalFlowId = null;
        } else {
            vaginalFlowId = in.readInt();
        }
        if (in.readByte() == 0) {
            sleepHours = null;
        } else {
            sleepHours = in.readInt();
        }
        username = in.readString();
        note = in.readString();
        symptoms = in.createTypedArrayList(Symptom.CREATOR);
        moods = in.createTypedArrayList(Mood.CREATOR);
        medications = in.createTypedArrayList(Medication.CREATOR);
        pills = in.createTypedArrayList(Pill.CREATOR);
        birthControl = in.createTypedArrayList(BirthControl.CREATOR);
    }

    public static final Creator<CycleLog> CREATOR = new Creator<CycleLog>() {
        @Override
        public CycleLog createFromParcel(Parcel in) {
            return new CycleLog(in);
        }

        @Override
        public CycleLog[] newArray(int size) {
            return new CycleLog[size];
        }
    };

    public Integer getCycleLogId() {
        return cycleLogId;
    }

    public void setCycleLogId(Integer cycleLogId) {
        this.cycleLogId = cycleLogId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getMenstrualFlowId() {
        return menstrualFlowId;
    }

    public void setMenstrualFlowId(Integer menstrualFlowId) {
        this.menstrualFlowId = menstrualFlowId;
    }

    public Integer getVaginalFlowId() {
        return vaginalFlowId;
    }

    public void setVaginalFlowId(Integer vaginalFlowId) {
        this.vaginalFlowId = vaginalFlowId;
    }

    public Integer getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(Integer sleepHours) {
        this.sleepHours = sleepHours;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public List<Mood> getMoods() {
        return moods;
    }

    public void setMoods(List<Mood> moods) {
        this.moods = moods;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<Pill> getPills() {
        return pills;
    }

    public void setPills(List<Pill> pills) {
        this.pills = pills;
    }

    public List<BirthControl> getBirthControl() {
        return birthControl;
    }

    public void setBirthControl(List<BirthControl> birthControl) {
        this.birthControl = birthControl;
    }

    public CycleLog(Integer cycleLogId, String creationDate, Integer menstrualFlowId, Integer vaginalFlowId, Integer sleepHours, String username, String note, List<Symptom> symptoms, List<Mood> moods, List<Medication> medications, List<Pill> pills, List<BirthControl> birthControl) {
        this.cycleLogId = cycleLogId;
        this.creationDate = creationDate;
        this.menstrualFlowId = menstrualFlowId;
        this.vaginalFlowId = vaginalFlowId;
        this.sleepHours = sleepHours;
        this.username = username;
        this.note = note;
        this.symptoms = symptoms;
        this.moods = moods;
        this.medications = medications;
        this.pills = pills;
        this.birthControl = birthControl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(this.cycleLogId);
        dest.writeString(this.creationDate);
        dest.writeValue(this.menstrualFlowId);
        dest.writeValue(this.vaginalFlowId);
        dest.writeValue(this.sleepHours);
        dest.writeString(this.username);
        dest.writeString(this.note);
        dest.writeTypedList(this.symptoms);
        dest.writeTypedList(this.moods);
        dest.writeTypedList(this.medications);
        dest.writeTypedList(this.pills);
        dest.writeTypedList(this.birthControl);
    }
}
