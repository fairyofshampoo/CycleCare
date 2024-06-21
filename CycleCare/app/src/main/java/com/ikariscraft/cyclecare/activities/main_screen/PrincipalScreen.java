package com.ikariscraft.cyclecare.activities.main_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ikariscraft.cyclecare.activities.cycle_log.CalendarFragment;
import com.ikariscraft.cyclecare.activities.register_reminder.RemindersFragment;
import com.ikariscraft.cyclecare.activities.view_content_user_pov.InformativeContentFragment;
import com.ikariscraft.cyclecare.activities.view_sleep_chart.ViewSleepChart;
import com.ikariscraft.cyclecare.databinding.ActivityPrincipalScreenBinding;

public class PrincipalScreen extends AppCompatActivity {

    ActivityPrincipalScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpRemindersButton();
        setUpCalendarButton();
        setUpInformativeContentButton();
        setUpSleepChartButton();
        showCalendarFragment();
    }

    private void setUpSleepChartButton() {
        binding.btnSleepChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.fragmentContainer.getId(), new ViewSleepChart())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setUpInformativeContentButton() {
        binding.btnInformativeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.fragmentContainer.getId(), new InformativeContentFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setUpCalendarButton() {
        binding.btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarFragment();
            }
        });
    }

    private void showCalendarFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), new CalendarFragment())
                .addToBackStack(null)
                .commit();
    }

    private void setUpRemindersButton() {
        binding.btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(binding.fragmentContainer.getId(), new RemindersFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        
    }


}