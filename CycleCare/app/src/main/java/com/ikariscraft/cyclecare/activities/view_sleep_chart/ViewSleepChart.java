package com.ikariscraft.cyclecare.activities.view_sleep_chart;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.databinding.FragmentViewSleepChartBinding;
import com.ikariscraft.cyclecare.model.MyValueFormatter;
import com.ikariscraft.cyclecare.model.SleepHoursInformation;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

import java.util.ArrayList;
import java.util.List;


public class ViewSleepChart extends Fragment {
    private FragmentViewSleepChartBinding binding;
    private ViewSleepChartViewModel viewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static ViewSleepChart newInstance(String param1, String param2) {
        ViewSleepChart fragment = new ViewSleepChart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentViewSleepChartBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(ViewSleepChartViewModel.class);
        Log.e("Creación de la pantalla", "Se ha creado el fragmento");



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_sleep_chart, container, false);
        BarChart barChart = view.findViewById(R.id.barChart);

        if(viewModel.getSleepChartRequestStatus().getValue() != RequestStatus.LOADING) {
            SessionSingleton sessionSingleton = SessionSingleton.getInstance();
            viewModel.getSleepHoursChart("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImZhaXJ5MGZTaGFtcG9vIiwiaWF0IjoxNzE2NTAyNTYyLCJleHAiOjE3MTY1MDYxNjJ9.jn--f6jysjwAkwA8pXNBfZuN7qifBj80BOFgaJe4mRo");
        }
        viewModel.getSleepHours().observe(getViewLifecycleOwner(), new Observer<List<SleepHoursInformation>>() {
            @Override
            public void onChanged(List<SleepHoursInformation> sleepHoursInformations) {
                if (sleepHoursInformations != null) {
                    ArrayList<BarEntry> sleepHoursChart = new ArrayList<>();
                    int index = 1;
                    for (SleepHoursInformation hours: sleepHoursInformations) {
                        sleepHoursChart.add(new BarEntry(index, hours.getTotalSleepHours()));
                        index++;
                    }
                    BarDataSet barDataSet = new BarDataSet(sleepHoursChart, "Horas de sueño");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16F);

                    String[] labels = new String[sleepHoursInformations.size()];

                    for (int i = 0; i < sleepHoursInformations.size(); i++) {
                        labels[i] = sleepHoursInformations.get(i).getDayOfWeek(); // Reemplaza con tus etiquetas reales
                    }

                    MyValueFormatter formatter = new MyValueFormatter(labels);
                    barDataSet.setValueFormatter(formatter);

                    BarData barData = new BarData(barDataSet);
                    barChart.setFitBars(true);
                    barChart.setData(barData);
                    barChart.animateY(2000);
                }else {
                    Log.e("Error nulo", "La lista está vacía");
                }
            }
        });


        Log.e("Creación de la tabla", "Se debió haber creado la estadística");
        return view;
    }
}