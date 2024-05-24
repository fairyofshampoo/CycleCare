package com.ikariscraft.cyclecare.model;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyValueFormatter extends ValueFormatter {
    private final String[] labels;

    public MyValueFormatter(String[] labels) {
        this.labels = labels;
    }

    @Override
    public String getBarLabel(BarEntry barEntry) {
        int index = (int) barEntry.getX();
        return labels[index - 1];
    }
}
