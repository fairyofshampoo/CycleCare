package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.RegisterContentRequest;
import com.ikariscraft.cyclecare.databinding.ActivityPublishInformativeContentBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.io.IOException;
import java.util.Date;

public class PublishInformativeContentActivity extends AppCompatActivity {
    ActivityPublishInformativeContentBinding binding;
    private PublishInformativeContentViewModel viewModel;
    private boolean isImageSelected = false;
    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublishInformativeContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PublishInformativeContentViewModel.class);

        setUpImageLayout();
        setUpFieldsValidation();
        setUpPublishButton();
        setUpStatusListener();
    }

    private void setUpPublishButton(){
        binding.btnPublishArticle.setOnClickListener(v -> {
            if(validateFields()){
                if (isImageSelected){
                    publishArticle();
                }else {
                    Toast.makeText(PublishInformativeContentActivity.this, "Es obligatoria la selección de una imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void publishArticle(){
        if(viewModel.getPublishArticleRequestStatus().getValue() != RequestStatus.LOADING){
            try {
                RegisterContentRequest contentRequest = createRequest();
                String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImZhaXJ5MGZTaGFtcG9vIiwiaWF0IjoxNzE2ODI1OTUzLCJleHAiOjE3MTY4Mjk1NTN9.xPrdn0j2f7_ScLQt__dMbDUSD04SgEBxO5L5Iu4h7hs";
                viewModel.publishArticle(token, contentRequest);
            } catch (IOException e) {
                Toast.makeText(PublishInformativeContentActivity.this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private RegisterContentRequest createRequest() throws IOException{
        String title = binding.titleEditText.getText().toString().trim();
        String description = binding.descriptionEditText.getText().toString().trim();
        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        Date currentDate = new Date();

        RegisterContentRequest registerContent = new RegisterContentRequest(title, description, currentDate, imageBitmap);
        return registerContent;
    }

    private void setUpImageLayout(){
        binding.imageLayout.setOnClickListener(v -> {
            openGallery();
        });
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeriaARL.launch(intent);
    }

    private ActivityResultLauncher <Intent> galeriaARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == PublishInformativeContentActivity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        if(isImageValid()){
                            binding.imageSelected.setImageURI(uri);
                            binding.imageSelected.setVisibility(View.VISIBLE);
                            binding.imageLayout.setVisibility(View.GONE);
                            isImageSelected = true;
                        }else {
                            Toast.makeText(PublishInformativeContentActivity.this, "La imagen es demasiado grande", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(PublishInformativeContentActivity.this, "Es obligatoria la selección de una imagen", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    public boolean isImageValid() {
        Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        long imageSize = returnCursor.getLong(sizeIndex);
        returnCursor.close();
        final long MAX_SIZE = 5 * 1024 * 1024; 
        return imageSize <= MAX_SIZE;
    }

    private void setUpFieldsValidation() {
        viewModel.getIsTitleValid().observe(this, isTitleValid -> {
            if(isTitleValid) {
                binding.errorTitleTextView.setVisibility(View.GONE);
            }else {

                binding.errorTitleTextView.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getIsDescriptionValid().observe(this, isDescriptionValid -> {
            if(isDescriptionValid){
                binding.errorDescriptionTextView.setVisibility(View.GONE);
            }else{
                binding.errorDescriptionTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean validateFields() {
        String title = binding.titleEditText.getText().toString().trim();
        String description = binding.descriptionEditText.getText().toString().trim();

        viewModel.ValidateTitle(title);
        viewModel.ValidateDescription(description);

        return Boolean.TRUE.equals(viewModel.getIsTitleValid().getValue())
                && Boolean.TRUE.equals(viewModel.getIsDescriptionValid().getValue());
    }

    private void setUpStatusListener(){
        viewModel.getPublishArticleRequestStatus().observe(this, requestStatus -> {
            if(requestStatus == RequestStatus.DONE) {
                Toast.makeText(this, "Artículo publicado", Toast.LENGTH_SHORT).show();
            } else if(requestStatus == RequestStatus.ERROR) {
                ProcessErrorCodes errorCodes = viewModel.getPublishArticleErrorCode().getValue();
                if(errorCodes != null) {
                    ShowPublishArticleError(errorCodes);
                }
            }
        });
    }

    private void ShowPublishArticleError(ProcessErrorCodes errorCode){
        String message = "";
        switch (errorCode){
            case SERVICE_NOT_AVAILABLE_ERROR:
                message = getString(R.string.login_server_error_message);
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
        }

        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }


}