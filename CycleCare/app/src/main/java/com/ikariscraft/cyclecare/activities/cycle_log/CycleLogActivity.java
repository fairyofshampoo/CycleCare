package com.ikariscraft.cyclecare.activities.cycle_log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.databinding.ActivityCycleLogBinding;

import java.time.LocalDate;

public class CycleLogActivity extends AppCompatActivity {
    private ActivityCycleLogBinding binding;
    private CalendarViewModel viewModel;
    public static final String SELECTED_DATE_KEY = "selected_date_key";
    public LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCycleLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        Bundle extras = getIntent().getExtras();
        selectedDate = extras.getParcelable("selected_date_key");

        setupSaveCycleLogButton();
        setupFieldsValidation();
        setUpOperationStatusListener();
    }

    private void setUpOperationStatusListener() {
        viewModel.
    }

    private void setupFieldsValidation() {

    }

    private void setupSaveCycleLogButton() {

    }
}