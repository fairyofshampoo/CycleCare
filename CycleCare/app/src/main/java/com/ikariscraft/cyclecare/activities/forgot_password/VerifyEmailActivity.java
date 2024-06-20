package com.ikariscraft.cyclecare.activities.forgot_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.requests.PasswordResetRequest;
import com.ikariscraft.cyclecare.databinding.ActivityVerifyEmailBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

public class VerifyEmailActivity extends AppCompatActivity {

    private ForgotPasswordViewModel viewModel;
    private ActivityVerifyEmailBinding binding;
    public static final String EMAIL_KEY = "email_key";
    public PasswordResetRequest resetPasswordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        setupVerifyEmailButton();
        setupFieldsValidation();
    }

    private void startNewPasswordActivity() {
        Intent intent = new Intent(this, NewPasswordActivity.class);
        intent.putExtra(NewPasswordActivity.RESET_PASSWORD_KEY, resetPasswordData);
        startActivity(intent);
    }

    private void setupFieldsValidation() {
        viewModel.isCodeValid().observe(this, isCodeValid -> {
            if(isCodeValid){
                binding.errorCodeTextView.setVisibility(View.GONE);
            } else {
                binding.errorCodeTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupVerifyEmailButton() {
        binding.btnConfirmEmail.setOnClickListener(v -> {
            if(areFieldsValid()){
                resetPasswordData.setToken(binding.codeEditText.getText().toString().trim();
                startNewPasswordActivity();
            }

        });
    }

    private boolean areFieldsValid() {
        String code = binding.codeEditText.getText().toString().trim();
        viewModel.ValidateCode(code);

        return Boolean.TRUE.equals(viewModel.isCodeValid().getValue());
    }
}