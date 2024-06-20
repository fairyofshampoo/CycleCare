package com.ikariscraft.cyclecare.model;

public class BirthControl {
    private int birthControlId;
    private String name;
    private String status;

    public BirthControl() {
    }

    public int getBirthControlId() {
        return birthControlId;
    }

    public void setBirthControlId(int birthControlId) {
        this.birthControlId = birthControlId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
