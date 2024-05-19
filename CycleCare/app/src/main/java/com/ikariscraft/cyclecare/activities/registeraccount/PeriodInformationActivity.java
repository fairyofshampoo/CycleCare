package com.ikariscraft.cyclecare.activities.registeraccount;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.databinding.ActivityPeriodInformationBinding;
import com.ikariscraft.cyclecare.model.*;

public class PeriodInformationActivity extends AppCompatActivity {

    ActivityPeriodInformationBinding binding;

    private PeriodInformationViewModel viewModel;

    public static final String REGISTER_DATA = "register_key_period_information";

    public UserRegisterData registerPeriodInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPeriodInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PeriodInformationViewModel.class);

        Bundle extras = getIntent().getExtras();
        registerPeriodInformation = extras.getParcelable("register_key_period_information");

        setupRegisterButton();
        setupFieldsValidation();
    }

    private void setupRegisterButton(){
        binding.btnRegisterAccount.setOnClickListener(v -> {
            if(areFieldsValid()){
                completeData();
                if(viewModel.getRegisterRequestStatus().getValue() != RequestStatus.LOADING){
                    viewModel.registerNewAccount(registerPeriodInformation);
                }
            }
        });
    }

    private boolean areFieldsValid(){
        String periodDuration = binding.periodDurationEditText.getText().toString().trim();
        String cycleDuration = binding.cycleDurationEditText.getText().toString().trim();

        viewModel.ValidatePeriodDuration(periodDuration);
        viewModel.ValidateCycleDuration(cycleDuration);

        return Boolean.TRUE.equals(viewModel.isPeriodDurationValid().getValue()) &&
                Boolean.TRUE.equals(viewModel.isCycleDurationValid().getValue());
    }

    private void setupFieldsValidation(){
        viewModel.isCycleDurationValid().observe(this, isCycleDurationValid -> {
            if(isCycleDurationValid){
                binding.errorCycleTextView.setVisibility(View.GONE);
            } else {
                binding.errorCycleTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isPeriodDurationValid().observe(this, isPeriodDurationValid -> {
            if(isPeriodDurationValid) {
                binding.errorPeriodTextView.setVisibility(View.GONE);
            }else {
                binding.errorPeriodTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void completeData() {
        int periodDuration = Integer.parseInt(binding.periodDurationEditText.getText().toString().trim());
        int cycleDuration = Integer.parseInt(binding.cycleDurationEditText.getText().toString().trim());

        registerPeriodInformation.setAproxPeriodDuration(periodDuration);
        registerPeriodInformation.setAproxCycleDuration(cycleDuration);
    }
}