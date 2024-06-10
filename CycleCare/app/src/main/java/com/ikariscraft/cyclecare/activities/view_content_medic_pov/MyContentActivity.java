package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.databinding.ActivityMyContentBinding;

import java.util.List;

public class MyContentActivity extends AppCompatActivity {

    private ActivityMyContentBinding binding;

    private MyContentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MyContentViewModel.class);
        binding.contentRecycler.setLayoutManager(new LinearLayoutManager(this));


        showMyContent();
    }

    private void showMyContent(){
        if(viewModel.getMyContentRequestStatus().getValue() != RequestStatus.LOADING){
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImZhaXJ5MGZTaGFtcG9vIiwiaWF0IjoxNzE4MDQ1MjY5LCJleHAiOjE3MTgwNDg4Njl9.BAsASF1Sav6SOldZdGJb3oUD9W4U-w8iJ0IZX5-ZAEQ";
            viewModel.getMyContent(token);
            viewModel.getMyInformativeContent().observe(this, new Observer<List<InformativeContentJSONResponse>>() {
                @Override
                public void onChanged(List<InformativeContentJSONResponse> informativeContentJSONResponses) {
                    if(informativeContentJSONResponses != null){
                        Log.e("No es un error", "La lista no viene vacía");
                        InformativeContentAdapter adapter = new InformativeContentAdapter();
                        binding.contentRecycler.setAdapter(adapter);
                        adapter.submitList(informativeContentJSONResponses);
                    }else {
                        Log.e("Error nulo", "La lista esta vacia");
                    }
                }
            });
        }
    }
}