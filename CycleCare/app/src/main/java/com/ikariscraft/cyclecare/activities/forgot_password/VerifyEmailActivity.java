package com.ikariscraft.cyclecare.activities.forgot_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.databinding.ActivityVerifyEmailBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

public class VerifyEmailActivity extends AppCompatActivity {

    private ForgotPasswordViewModel viewModel;
    private ActivityVerifyEmailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        setupVerifyEmailButton();
        setupFieldsValidation();
        setupVerifyEmailStatusListener();
    }

    private void setupVerifyEmailStatusListener() {
        viewModel.getForgotPasswordRequestStatus().observe(this, requestStatus -> {
            switch (requestStatus){
                case DONE:
                    startNewPasswordActivity();
                    break;
                case ERROR:
                    binding.btnConfirmEmail.setEnabled(true);
                    ProcessErrorCodes errorCode = viewModel.getForgotPasswordErrorCode().getValue();

                    if(errorCode != null){
                        showVerifyEmailError(errorCode);
                    }
                    break;
            }
        });
    }

    private void showVerifyEmailError(ProcessErrorCodes errorCode) {
        String message = "";
        switch (errorCode){
            case NOT_FOUND_ERROR:
                message = getString(R.string.email_not_found);
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
        }
    }

    private void startNewPasswordActivity() {
        Intent intent = new Intent(this, NewPasswordActivity.class);
        startActivity(intent);
    }

    private void setupFieldsValidation() {
        viewModel.isCodeValid().observe(this, isCodeValid -> {
            if(isCodeValid){
                binding.errorCodeTextView.setVisibility(View.GONE);
            } else {
                binding.tilCode.setError(getString(R.string.invalid_code));
            }
        });
    }

    private void setupVerifyEmailButton() {

    }
}