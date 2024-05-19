package com.ikariscraft.cyclecare.activities.registeraccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.databinding.ActivityCycleTypeBinding;

public class CycleTypeActivity extends AppCompatActivity {

    private ActivityCycleTypeBinding binding;

    public static final String REGISTER = "register_key";

    private int REGULAR = 1;

    private int IRREGULAR = 0;

    public UserRegisterData registerCycleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCycleTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        registerCycleType = extras.getParcelable("register_key");

        setupOptionButtons();
    }

    private void setupOptionButtons(){
        binding.btnIrregular.setOnClickListener(v -> {
            System.out.print("Datos de la pantalla anterior: ");
            System.out.print("Nombre: " + registerCycleType.getName());
        });

        binding.btnRegular.setOnClickListener(v -> {
            registerCycleType.setIsRegular(REGULAR);
            startPeriodInformation();
        });

        binding.btnIdk.setOnClickListener(v -> {
            registerCycleType.setIsRegular(IRREGULAR);
            startPeriodInformation();
        });
    }

    private void startPeriodInformation(){
        Intent intent = new Intent(this, PeriodInformationActivity.class);
        intent.putExtra(PeriodInformationActivity.REGISTER_DATA, registerCycleType);
        startActivity(intent);
    }

}