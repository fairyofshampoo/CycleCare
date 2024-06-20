package com.ikariscraft.cyclecare.model;

public class Pill {
    private int pillId;
    private String status;

    public Pill() {
    }

    public int getPillId() {
        return pillId;
    }

    public void setPillId(int pillId) {
        this.pillId = pillId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
