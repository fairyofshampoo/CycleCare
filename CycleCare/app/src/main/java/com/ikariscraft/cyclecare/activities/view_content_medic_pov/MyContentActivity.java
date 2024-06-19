package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.databinding.ActivityMyContentBinding;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

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
        setUpUploadArticleButton();
        //setUpUploadVideoButton();
    }

    private void showMyContent(){
        if(viewModel.getMyContentRequestStatus().getValue() != RequestStatus.LOADING){
            SessionSingleton session = SessionSingleton.getInstance();
            String token = session.getToken();
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

    private void setUpUploadArticleButton(){
        binding.btnUploadArticle.setOnClickListener(v -> {
            Intent intent = new Intent(this, PublishInformativeContentActivity.class);
            startActivity(intent);
        });
    }

}