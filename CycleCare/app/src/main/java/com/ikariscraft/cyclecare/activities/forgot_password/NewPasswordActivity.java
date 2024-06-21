package com.ikariscraft.cyclecare.activities.forgot_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.activities.login.LoginActivity;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.PasswordResetRequest;
import com.ikariscraft.cyclecare.databinding.ActivityNewPasswordBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.PasswordUtilities;
import com.ikariscraft.cyclecare.utilities.Validations;

public class NewPasswordActivity extends AppCompatActivity {
    private ActivityNewPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;
    public static final String RESET_PASSWORD_KEY = "reset_password_key";
    public PasswordResetRequest resetPasswordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        
        Bundle extras = getIntent().getExtras();
        resetPasswordData = extras.getParcelable("reset_password_key");
        
        setupSavePasswordButton();
        setupFieldsValidation();
        setUpOperationStatusListener();
    }

    private void setUpOperationStatusListener() {
        viewModel.getForgotPasswordRequestStatus().observe(this, requestStatus -> {
            if (requestStatus == RequestStatus.DONE) {
                Toast.makeText(this, "Se ha cambiado la contraseña", Toast.LENGTH_SHORT).show();
                startLoginActivity();
            }

            if (requestStatus == RequestStatus.ERROR) {
                ProcessErrorCodes errorCode = viewModel.getForgotPasswordErrorCode().getValue();

                if(errorCode != null) {
                    showNewPasswordError(errorCode);
                }

            }
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void showNewPasswordError(ProcessErrorCodes errorCode) {
        String message = "";

        switch (errorCode){
            case REQUEST_FORMAT_ERROR:
                message = getString(R.string.new_password_bad_request_error);
                break;
            case NOT_FOUND_ERROR:
                message = getString(R.string.new_password_not_found_error);
                break;
            case SERVICE_NOT_AVAILABLE_ERROR:
                message = getString(R.string.login_server_error_message);
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
        }

        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    private void setupFieldsValidation() {
        viewModel.isPasswordValid().observe(this, isPasswordValid -> {
            if (isPasswordValid) {
                binding.errorPasswordTextView.setVisibility(View.GONE);
            } else {
                binding.errorPasswordTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isPasswordConfirmationValid().observe(this, isPasswordConfirmationValid -> {
            if (isPasswordConfirmationValid) {
                binding.errorPasswordConfirmTextView.setVisibility(View.GONE);
            } else {
                binding.errorPasswordConfirmTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupSavePasswordButton() {
        binding.btnSavePassword.setOnClickListener(v -> {
            if (areFieldsValid()) {
                completeData();
                if (viewModel.getForgotPasswordRequestStatus().getValue() != RequestStatus.LOADING) {
                    viewModel.resetPassword(resetPasswordData);
                }
            }
        });
    }

    private void completeData() {
        String password = binding.newPasswordEditText.getText().toString();
        String passwordConfirm = binding.confirmPasswordEditText.getText().toString();
        resetPasswordData.setNewPassword(PasswordUtilities.computeSHA256Hash(password));
        resetPasswordData.setConfirmPassword(PasswordUtilities.computeSHA256Hash(passwordConfirm));
    }

    private boolean areFieldsValid() {
        String password = binding.newPasswordEditText.getText().toString();
        String passwordConfirm = binding.confirmPasswordEditText.getText().toString();

        viewModel.ValidatePassword(password);
        viewModel.ValidatePasswordConfirmation(password, passwordConfirm);

        return Boolean.TRUE.equals(viewModel.isPasswordValid().getValue())
                && Boolean.TRUE.equals(viewModel.isPasswordConfirmationValid().getValue());
    }
}