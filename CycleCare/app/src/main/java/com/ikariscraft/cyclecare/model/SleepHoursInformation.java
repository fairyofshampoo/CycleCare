package com.ikariscraft.cyclecare.model;

public class SleepHoursInformation {
    private String dayOfWeek;
    private int totalSleepHours;

    public SleepHoursInformation(){

    }

    public SleepHoursInformation(String dayOfWeek, int totalSleepHours) {
        this.dayOfWeek = dayOfWeek;
        this.totalSleepHours = totalSleepHours;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTotalSleepHours() {
        return totalSleepHours;
    }

    public void setTotalSleepHours(int totalSleepHours) {
        this.totalSleepHours = totalSleepHours;
    }
}
