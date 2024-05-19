package com.ikariscraft.cyclecare.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ikariscraft.cyclecare.activities.principalscreen.PrincipalScreen;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.databinding.ActivityLoginBinding;

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
        setupLoginStatusListener();
    }

    private void setUpLoginButtonClick() {
        binding.btnLogin.setOnClickListener( v -> {
            //Primero tiene que ir una validación
            if (viewModel.getLoginRequestStatus().getValue() != RequestStatus.LOADING) {
                String user = binding.userEditText.getText().toString().trim();
                String password = binding.passwordEditText.getText().toString().trim();
                viewModel.login(user,password);
            }
        });
    }

    private void setupLoginStatusListener(){
        viewModel.getLoginRequestStatus().observe(this, requestStatus -> {
            if(requestStatus == RequestStatus.DONE){
                startMainMenu();
            }

            if (requestStatus == RequestStatus.ERROR) {
                Toast.makeText(LoginActivity.this, "No funciona", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMainMenu () {
        Intent intent = new Intent(this, PrincipalScreen.class);
        startActivity(intent);
    }

}