package com.ikariscraft.cyclecare.model;

public class MenstrualCycle {
    private int menstrualCycleId;
    private String username;
    private int isRegular;
    private int aproxCycleDuration;
    private int aproxPeriodDuration;

    public int getMenstrualCycleId() {
        return menstrualCycleId;
    }

    public void setMenstrualCycleId(int menstrualCycleId) {
        this.menstrualCycleId = menstrualCycleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsRegular() {
        return isRegular;
    }

    public void setIsRegular(int isRegular) {
        this.isRegular = isRegular;
    }

    public int getAproxCycleDuration() {
        return aproxCycleDuration;
    }

    public void setAproxCycleDuration(int aproxCycleDuration) {
        this.aproxCycleDuration = aproxCycleDuration;
    }

    public int getAproxPeriodDuration() {
        return aproxPeriodDuration;
    }

    public void setAproxPeriodDuration(int aproxPeriodDuration) {
        this.aproxPeriodDuration = aproxPeriodDuration;
    }
}
