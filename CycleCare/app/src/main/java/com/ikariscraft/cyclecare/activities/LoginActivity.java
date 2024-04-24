package com.ikariscraft.cyclecare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.databinding.ActivityLoginBinding;
import com.ikariscraft.cyclecare.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(viewModel);

        // Observa el resultado del inicio de sesión
        viewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == 1) {
                // Inicio de sesión exitoso, navega a la siguiente actividad
                // Aquí puedes iniciar la actividad principal de tu aplicación, por ejemplo
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                // Error de inicio de sesión, muestra un mensaje al usuario
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.login();
            }
        });
    }
}