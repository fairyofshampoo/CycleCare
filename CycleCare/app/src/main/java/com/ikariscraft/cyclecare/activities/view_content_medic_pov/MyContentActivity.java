package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import static com.ikariscraft.cyclecare.BR.informativeContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

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
                        InformativeContentAdapter adapter = new InformativeContentAdapter();
                        binding.contentRecycler.setAdapter(adapter);
                        adapter.setOnItemClickListener(informativeContent -> {
                            ShowInformativeContetData(informativeContent);
                        });
                        adapter.submitList(informativeContentJSONResponses);
                    }else {
                        Toast.makeText(MyContentActivity.this, "No tiene articulos registrados", Toast.LENGTH_SHORT).show();
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

    private void ShowInformativeContetData(InformativeContentJSONResponse informativeContentJSONResponse){
        Intent intent = new Intent(this, ViewContentByMedic.class);
        intent.putExtra(ViewContentByMedic.REGISTER,  informativeContentJSONResponse);
        startActivity(intent);
    }

}