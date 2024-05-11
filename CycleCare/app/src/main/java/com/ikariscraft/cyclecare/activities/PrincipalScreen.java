package com.ikariscraft.cyclecare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.databinding.ActivityPrincipalScreenBinding;

public class PrincipalScreen extends AppCompatActivity {

    ActivityPrincipalScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), new ViewSleepChart())
                .addToBackStack(null)
                .commit();

    }
}