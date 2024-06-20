package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.activities.register_account.PeriodInformationActivity;
import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.databinding.ActivityViewContentByMedicBinding;
import com.squareup.picasso.Picasso;

public class ViewContentByMedic extends AppCompatActivity {

    private ActivityViewContentByMedicBinding binding;

    public InformativeContentJSONResponse article;

    public static final String REGISTER = "article_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewContentByMedicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        article = extras.getParcelable("article_key");

        showData();
        setUpEditButton();
    }

    private void showData(){
        binding.txtAuthor.setText("Por: "+article.getUsername());
        binding.txtTitle.setText(article.getTitle());
        binding.txtContent.setText(article.getDescription());
        ApiClient apiClient = ApiClient.getInstance();
        String baseIP = apiClient.getBaseIp();
        Picasso.get().load(baseIP + "/images/"+ article.getImage()).into(binding.imageView);
    }

    private void setUpEditButton() {
        binding.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditInformativeContentActivity.class);
            intent.putExtra(EditInformativeContentActivity.REGISTER, article);
            startActivity(intent);
        });
    }
}