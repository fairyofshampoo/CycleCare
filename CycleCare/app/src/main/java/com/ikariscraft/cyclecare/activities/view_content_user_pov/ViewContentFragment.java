package com.ikariscraft.cyclecare.activities.view_content_user_pov;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.databinding.FragmentViewContentBinding;

public class ViewContentFragment extends Fragment {
    private ViewContentViewModel viewModel;
    private FragmentViewContentBinding binding;
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
        binding = FragmentViewContentBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(ViewContentViewModel.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setUpRatingStarsClick();
        setUpStatusMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_content, container, false);
    }

    private void setUpRatingStarsClick(){
        binding.starsRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(viewModel.getRateContentRequestStatus().getValue() != RequestStatus.LOADING) {
                String token = "ASDFS";
                int contentId = 2;
                int starsRating = (int) rating;
                viewModel.rateContent(token, contentId, starsRating);
            }
            binding.starsRatingBar.setIsIndicator(true);
        });
    }

    private  void setUpStatusMessage() {
        viewModel.getRateContentRequestStatus().observe(this, requestStatus -> {
            if(requestStatus == RequestStatus.DONE) {
                Toast.makeText(getContext(), "Se ha calificado el contenido", Toast.LENGTH_SHORT).show();
            } else  {
                Toast.makeText(getContext(), "Ha ocurrido un error, inténtalo más tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }


}