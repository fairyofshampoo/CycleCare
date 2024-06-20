package com.ikariscraft.cyclecare.model;

public class Medication {
    private int medicationId;
    private String name;

    public Medication() {
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
