package com.ikariscraft.cyclecare.api.responses;

import com.ikariscraft.cyclecare.model.SleepHoursInformation;

import java.util.List;

public class SleepChartJSONResponse {
    private List<SleepHoursInformation> sleepHours;

    public List<SleepHoursInformation> getSleepHours() {
        return sleepHours;
    }
}
