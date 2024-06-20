package com.ikariscraft.cyclecare.model;

public class Symptom {
    private int symptomId;
    private String name;

    public Symptom() {
    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
