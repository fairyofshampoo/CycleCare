package com.ikariscraft.cyclecare.api.requests;

import com.ikariscraft.cyclecare.model.BirthControl;
import com.ikariscraft.cyclecare.model.Medication;
import com.ikariscraft.cyclecare.model.Mood;
import com.ikariscraft.cyclecare.model.Pill;
import com.ikariscraft.cyclecare.model.Symptom;

import java.util.List;

public class NewCycleLogBody {
    private int cycleLogId;
    private String username;
    private int sleepHours;
    private String creationDate;
    private String note;
    private int menstrualFlowId;
    private int vaginalFlowId;
    private List<Symptom> symptoms;
    private List<Mood> moods;
    private List<Medication> medications;
    private List<Pill> pills;
    private List<BirthControl> birthControls;

    public NewCycleLogBody() {
    }

    public NewCycleLogBody(int cycleLogId, String username, int sleepHours, String creationDate, String note, int menstrualFlowId, int vaginalFlowId, List<Symptom> symptoms, List<Mood> moods, List<Medication> medications, List<Pill> pills, List<BirthControl> birthControls) {
        this.cycleLogId = cycleLogId;
        this.username = username;
        this.sleepHours = sleepHours;
        this.creationDate = creationDate;
        this.note = note;
        this.menstrualFlowId = menstrualFlowId;
        this.vaginalFlowId = vaginalFlowId;
        this.symptoms = symptoms;
        this.moods = moods;
        this.medications = medications;
        this.pills = pills;
        this.birthControls = birthControls;
    }

    public int getCycleLogId() {
        return cycleLogId;
    }

    public void setCycleLogId(int cycleLogId) {
        this.cycleLogId = cycleLogId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getMenstrualFlowId() {
        return menstrualFlowId;
    }

    public void setMenstrualFlowId(int menstrualFlowId) {
        this.menstrualFlowId = menstrualFlowId;
    }

    public int getVaginalFlowId() {
        return vaginalFlowId;
    }

    public void setVaginalFlowId(int vaginalFlowId) {
        this.vaginalFlowId = vaginalFlowId;
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

    public List<BirthControl> getBirthControls() {
        return birthControls;
    }

    public void setBirthControls(List<BirthControl> birthControls) {
        this.birthControls = birthControls;
    }
}
