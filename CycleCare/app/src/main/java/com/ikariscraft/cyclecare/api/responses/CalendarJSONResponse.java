package com.ikariscraft.cyclecare.api.responses;

import com.ikariscraft.cyclecare.model.CycleLog;

import java.util.List;

public class CalendarJSONResponse {
    List<CycleLog> cycleLogs;

    public CalendarJSONResponse() {
    }

    public CalendarJSONResponse(List<CycleLog> cycleLogs) {
        this.cycleLogs = cycleLogs;
    }

    public List<CycleLog> getCycleLogs() {
        return cycleLogs;
    }

    public void setCycleLogs(List<CycleLog> cycleLogs) {
        this.cycleLogs = cycleLogs;
    }
}
