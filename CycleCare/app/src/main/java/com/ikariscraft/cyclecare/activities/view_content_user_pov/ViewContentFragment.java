package com.ikariscraft.cyclecare.activities.view_content_user_pov;

import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.RateInformativeContentRequest;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.databinding.FragmentViewContentBinding;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;
import com.squareup.picasso.Picasso;

public class ViewContentFragment extends Fragment {
    private ViewContentViewModel viewModel;
    private FragmentViewContentBinding binding;
    private InformativeContentJSONResponse informativeContent;

    private float rate = -1;

    TextView txtTitle, txtDescription, txtUser;

    ImageView imgView;

    RatingBar startRating;

    public static final String REGISTER = "register_key";


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ViewContentFragment() {
        // Required empty public constructor
    }

    public static ViewContentFragment newInstance(String param1, String param2) {
        ViewContentFragment fragment = new ViewContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewContentViewModel.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        informativeContent = new InformativeContentJSONResponse();
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtUser = view.findViewById(R.id.txtAuthor);
        imgView = view.findViewById(R.id.imageView);
        startRating = view.findViewById(R.id.starsRatingBar);

        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                int id = bundle.getInt("id");
                String title = bundle.getString("title");
                String description = bundle.getString("description");
                String media = bundle.getString("media");
                String creationDate = bundle.getString("creationDate");
                String username = bundle.getString("username");

                SessionSingleton sessionSingleton = SessionSingleton.getInstance();
                String token = sessionSingleton.getToken();

                txtTitle.setText(title);
                txtDescription.setText(description);
                txtUser.setText("Publicado por: " + username);
                ApiClient apiClient = ApiClient.getInstance();
                String baseIP = apiClient.getBaseIp();
                Picasso.get().load(baseIP + "/images/" + media).into(imgView);

                informativeContent.setContentId(id);
                informativeContent.setTitle(title);
                informativeContent.setDescription(description);
                informativeContent.setImage(media);
                informativeContent.setCreationDate(creationDate);
                informativeContent.setUsername(username);

                viewModel.GetRateContent(token, informativeContent.getContentId());

            }
        });
        startRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (viewModel.getRateContentRequestStatus().getValue() != RequestStatus.LOADING) {
                int starsRating = (int) rating;
                SessionSingleton sessionSingleton = SessionSingleton.getInstance();
                String token = sessionSingleton.getToken();
                if (viewModel.getRateContentRequestStatus().getValue() == RequestStatus.DONE ) {
                    RateInformativeContentRequest rateInformativeContentRequest = new RateInformativeContentRequest(starsRating);
                    viewModel.UpdateRateContent(token, informativeContent.getContentId(), rateInformativeContentRequest);
                } else{
                    RateInformativeContentRequest rateInformativeContentRequest = new RateInformativeContentRequest(starsRating);
                    viewModel.rateContent(token, informativeContent.getContentId(), rateInformativeContentRequest);
                }
            }
        });

        viewModel.getRateContentRequestStatus().observe(getViewLifecycleOwner(), requestStatus -> {
            if (requestStatus == RequestStatus.ERROR) {
                Toast.makeText(getContext(), "Error al calificar el contenido", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getContext(), "Se ha calificado el contenido", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getExistingRateContentRequestStatus().observe(getViewLifecycleOwner(), requestStatus -> {

            if(requestStatus == RequestStatus.DONE) {
                viewModel.getRate().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                            startRating.setRating((float)integer);
                            rate = (float) integer;
                    }
                });
            }
        });

    }
}