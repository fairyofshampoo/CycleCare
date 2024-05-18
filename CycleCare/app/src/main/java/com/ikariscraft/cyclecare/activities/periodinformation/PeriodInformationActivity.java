package com.ikariscraft.cyclecare.activities.periodinformation;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.databinding.ActivityPeriodInformationBinding;
import com.ikariscraft.cyclecare.model.*;

public class PeriodInformationActivity extends AppCompatActivity {

    ActivityPeriodInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPeriodInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}