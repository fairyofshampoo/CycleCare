package com.ikariscraft.cyclecare.activities.register_account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.databinding.ActivityRegisterAccountBinding;

public class RegisterAccountActivity extends AppCompatActivity {

    private ActivityRegisterAccountBinding binding;

    private RegisterAccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(RegisterAccountViewModel.class);

        setupNextButton();
        setupFieldsValidation();

    }

    private void setupNextButton() {
        binding.nextBtn.setOnClickListener(v -> {
                if(areFieldsValid()){
                    startCycleTypeScreen();
                }
        });
    }

    private  boolean areFieldsValid () {
        String name = binding.nameEditText.getText().toString().trim();
        String firstLastName = binding.firstLastNameEditText.getText().toString().trim();
        String secondLastName = binding.secondLastNameEditText.getText().toString().trim();
        String user = binding.userEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        viewModel.ValidateName(name);
        viewModel.ValidateFirstLastName(firstLastName);
        viewModel.ValidateSecondLastName(secondLastName);
        viewModel.ValidateUsername(user);
        viewModel.ValidatePassword(password);

        return Boolean.TRUE.equals(viewModel.isNameValid().getValue()) &&
               Boolean.TRUE.equals(viewModel.isFirstLastNameValid().getValue()) &&
               Boolean.TRUE.equals(viewModel.isSecondLastNameValid().getValue()) &&
               Boolean.TRUE.equals(viewModel.isUserValid().getValue()) &&
               Boolean.TRUE.equals(viewModel.isPasswordValid().getValue());

    }

    private void setupFieldsValidation() {
        viewModel.isNameValid().observe(this, isNameValid -> {
            if(isNameValid) {
                binding.errorNameTextView.setVisibility(View.GONE);
            } else {
                binding.errorNameTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isFirstLastNameValid().observe(this, isFirstLastNameValid -> {
            if(isFirstLastNameValid) {
                binding.errorFirstLastNameTextView.setVisibility(View.GONE);
            } else {
                binding.errorFirstLastNameTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isSecondLastNameValid().observe(this, isSecondLastNameValid -> {
            if(isSecondLastNameValid) {
                binding.errorSecondLastNameTextView.setVisibility(View.GONE);
            } else {
                binding.errorSecondLastNameTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isUserValid().observe(this, isUserValid -> {
            if(isUserValid) {
                binding.errorUserNameTextView.setVisibility(View.GONE);
            } else {
                binding.errorUserNameTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isPasswordValid().observe(this, isPasswordValid -> {
            if(isPasswordValid) {
                binding.errorPasswordTextView.setVisibility(View.GONE);
            } else {
                binding.errorPasswordTextView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void startCycleTypeScreen() {
        Intent intent = new Intent(this, CycleTypeActivity.class);
        UserRegisterData registerData = createRegister();
        intent.putExtra(CycleTypeActivity.REGISTER, registerData);
        startActivity(intent);
    }

    private UserRegisterData createRegister() {
        UserRegisterData registerData = new UserRegisterData();
        registerData.setName(binding.nameEditText.getText().toString().trim());
        registerData.setFirstLastName(binding.firstLastNameEditText.getText().toString().trim());
        registerData.setSecondLastName(binding.secondLastNameEditText.getText().toString().trim());
        registerData.setEmail(binding.emailEditText.getText().toString().trim());
        registerData.setRole("Menstruante");
        registerData.setUsername(binding.userEditText.getText().toString().trim());
        registerData.setPassword(binding.passwordEditText.getText().toString().trim());
        return registerData;
    }

}