package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.databinding.ActivityViewContentByMedicBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;
import com.squareup.picasso.Picasso;

public class ViewContentByMedic extends AppCompatActivity {

    private ActivityViewContentByMedicBinding binding;

    public InformativeContentJSONResponse article;
    public ViewContentByMedicViewModel viewModel;

    public static final String REGISTER = "article_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewContentByMedicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ViewContentByMedicViewModel.class);

        Bundle extras = getIntent().getExtras();
        article = extras.getParcelable("article_key");

        setUpStatusListener();
        showData();
        setUpEditButton();
    }

    private void setUpStatusListener() {
        viewModel.getAverageRatingRequestStatus().observe(this, requestStatus -> {
            if(requestStatus == RequestStatus.DONE) {
                showAverageRating();
            }

            if(requestStatus == RequestStatus.ERROR) {
                ProcessErrorCodes errorCode = viewModel.getAverageRatingErrorCode().getValue();

                if(errorCode != null) {
                    showErrorMessage(errorCode);
                }
            }
        });
    }

    private void showErrorMessage(ProcessErrorCodes errorCode) {
        String message = "";

        switch (errorCode){
            case NOT_FOUND_ERROR:
                message = "No se encontró el contenido";
                break;
            case REQUEST_FORMAT_ERROR:
                message = "Error en el formato de la solicitud";
                break;
            case SERVICE_NOT_AVAILABLE_ERROR:
                message = getString(R.string.login_server_error_message);
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
                break;
        }

        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    private void showAverageRating() {
        double averageRating = viewModel.getAverageRatingResponse().getValue().getAverage();
        binding.txtRateAverage.setText("Calificación promedio: "+ averageRating);
    }

    private void showData(){
        binding.txtAuthor.setText("Por: "+ article.getUsername());
        binding.txtTitle.setText(article.getTitle());
        binding.txtContent.setText(article.getDescription());
        ApiClient apiClient = ApiClient.getInstance();
        String baseIP = apiClient.getBaseIp();
        Picasso.get().load(baseIP + "/images/"+ article.getImage()).into(binding.imageView);
        SessionSingleton session = SessionSingleton.getInstance();
        String token = session.getToken();
        viewModel.getAverageRating(token, article.getContentId());
    }

    private void setUpEditButton() {
        binding.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditInformativeContentActivity.class);
            intent.putExtra(EditInformativeContentActivity.REGISTER, article);
            startActivity(intent);
        });
    }
}