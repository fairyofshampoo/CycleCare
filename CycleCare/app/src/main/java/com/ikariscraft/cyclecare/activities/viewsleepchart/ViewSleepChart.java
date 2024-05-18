package com.ikariscraft.cyclecare.activities.viewsleepchart;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ikariscraft.cyclecare.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewSleepChart#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ViewSleepChart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewSleepChart.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_sleep_chart, container, false);
        BarChart barChart = view.findViewById(R.id.barChart);

        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(1, 10));
        visitors.add(new BarEntry(2, 8));
        visitors.add(new BarEntry(3, 5));
        visitors.add(new BarEntry(4, 9));
        visitors.add(new BarEntry(5, 10));
        visitors.add(new BarEntry(6, 12));
        visitors.add(new BarEntry(7, 3));

        BarDataSet barDataSet = new BarDataSet(visitors, "Visitors");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(android.R.color.black);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar chart example");
        barChart.animateY(2000);


        return view;
    }
}