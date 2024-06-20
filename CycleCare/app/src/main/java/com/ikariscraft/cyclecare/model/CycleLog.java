package com.ikariscraft.cyclecare.model;

import java.util.List;

public class CycleLog {

    private int cycleLogId;
    private String creationDate;
    private Integer menstrualFlowId;
    private Integer vaginalFlowId;
    private int sleepHours;
    private String username;
    private String note;
    private List<Symptom> symptoms;
    private List<Mood> moods;
    private List<Medication> medications;
    private List<Pill> pills;
    private List<BirthControl> birthControl;

    public CycleLog() {
    }

    public CycleLog(int cycleLogId, String creationDate, List<BirthControl> birthControl, List<Medication> medications, List<Symptom> symptoms, List<Mood> moods, Integer menstrualFlowId, String note, List<Pill> pills, int sleepHours, String username, Integer vaginalFlowId) {
    }

    public int getCycleLogId() {
        return cycleLogId;
    }

    public void setCycleLogId(int cycleLogId) {
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

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
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
}
