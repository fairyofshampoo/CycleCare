package com.ikariscraft.cyclecare.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.activities.main_screen.PrincipalScreen;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.databinding.ActivityLoginBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.PasswordUtilities;
import com.ikariscraft.cyclecare.utilities.Role;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

public class    LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setUpLoginButtonClick();
        setupFieldsValidations();
        setupLoginStatusListener();
    }

    private void setupFieldsValidations() {
        viewModel.isUserValid().observe(this, isUserValid -> {
            if(isUserValid){
                binding.errorUserNameTextView.setVisibility(android.view.View.GONE);
            } else {
                binding.errorUserNameTextView.setVisibility(android.view.View.VISIBLE);
            }
        });

        viewModel.isPasswordValid().observe(this, isPasswordValid -> {
            if(isPasswordValid){
                binding.errorPasswordTextView.setVisibility(android.view.View.GONE);
            } else {
                binding.errorPasswordTextView.setVisibility(android.view.View.VISIBLE);
            }
        });
    }

    private void setUpLoginButtonClick() {
        binding.btnLogin.setOnClickListener( v -> {
            if(validateFields()){
                login();
            }
        });
    }

    private boolean validateFields() {
        String username = binding.userEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        viewModel.validateUser(username);
        viewModel.validatePassword(password);

        return Boolean.TRUE.equals(viewModel.isUserValid().getValue())
                && Boolean.TRUE.equals(viewModel.isPasswordValid().getValue());
    }

    private void login() {

        if (viewModel.getLoginRequestStatus().getValue() != RequestStatus.LOADING) {
            String user = binding.userEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
            String passwordHashed = PasswordUtilities.computeSHA256Hash(password);
            viewModel.login(user, passwordHashed);
        }
    }

    private void setupLoginStatusListener(){
        viewModel.getLoginRequestStatus().observe(this, requestStatus -> {
            if(requestStatus == RequestStatus.DONE){
                startMainMenu();
            }

            if (requestStatus == RequestStatus.ERROR) {
                ProcessErrorCodes errorCode = viewModel.getLoginErrorCode().getValue();

                if(errorCode != null){
                    showLoginError(errorCode);
                }
            }
        });
    }

    private void showLoginError(ProcessErrorCodes errorCode) {
        String message = "";

        switch (errorCode){
            case REQUEST_FORMAT_ERROR:
                message = getString(R.string.login_invalid_credentials_message);
                break;
            case SERVICE_NOT_AVAILABLE_ERROR:
                message = getString(R.string.login_server_error_message);
                break;
            case FATAL_ERROR:
                message = getString(R.string.login_fatal_error_message);
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
        }

        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    private void startMainMenu() {
        Role role = Role.valueOf(SessionSingleton.getInstance().getPerson().getRole());

        switch (role) {
            case USER: {
                Intent intent = new Intent(this, PrincipalScreen.class);
                startActivity(intent);
                break;
            }
            case MEDIC: {
                Toast.makeText(this, "Pantalla de médico", Toast.LENGTH_SHORT).show();
                break;
            }
            default: {
                Toast.makeText(this, R.string.role_not_supported, Toast.LENGTH_SHORT).show();
            }
        }
    }

}