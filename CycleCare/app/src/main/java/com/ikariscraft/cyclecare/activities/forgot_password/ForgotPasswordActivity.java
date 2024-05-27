package com.ikariscraft.cyclecare.activities.forgot_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.databinding.ActivityForgotPasswordBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        setupSendEmailButton();
        setupFieldsValidation();
        setupForgotPasswordStatusListener();
    }

    private void setupForgotPasswordStatusListener() {
        viewModel.getForgotPasswordRequestStatus().observe(this, requestStatus -> {
            switch (requestStatus){
                case DONE:
                    startVerifyEmailActivity();
                    break;
                case ERROR:
                    binding.btnSendEmail.setEnabled(true);
                    ProcessErrorCodes errorCode = viewModel.getForgotPasswordErrorCode().getValue();

                    if(errorCode != null){
                        showForgotPasswordError(errorCode);
                    }
                    break;
            }
        });
    }

    private void showForgotPasswordError(ProcessErrorCodes errorCode) {
        String message = "";
        switch (errorCode){
            case NOT_FOUND_ERROR:
                message = getString(R.string.email_not_found);
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
        }
    }

    private void startVerifyEmailActivity() {
        Intent intent = new Intent(this, VerifyEmailActivity.class);
        startActivity(intent);
    }

    private void setupFieldsValidation() {
        viewModel.isEmailValid().observe(this, isEmailValid ->{
            if(isEmailValid){
                binding.errorEmailTextView.setVisibility(View.GONE);
            }else{
                binding.errorEmailTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void sendEmail() {
        if(viewModel.getForgotPasswordRequestStatus().getValue() != RequestStatus.LOADING){
            String email = binding.emailEditText.getText().toString().trim();
            viewModel.sendEmail(email);
        }
    }

    private boolean areFieldsValid() {
        String email = binding.emailEditText.getText().toString().trim();
        viewModel.ValidateEmail(email);

        return Boolean.TRUE.equals(viewModel.isEmailValid().getValue());
    }

    private void setupSendEmailButton() {
        binding.btnSendEmail.setOnClickListener(v -> {
            if(areFieldsValid()){
                sendEmail();
            }
        });
    }
}