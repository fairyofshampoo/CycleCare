package com.ikariscraft.cyclecare.activities.view_content_user_pov;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.activities.view_content_medic_pov.InformativeContentAdapter;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.databinding.FragmentInformativeContentBinding;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

import java.util.List;

public class InformativeContentFragment extends Fragment {

    private ViewContentViewModel viewModel;
    private FragmentInformativeContentBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public InformativeContentFragment() {

    }

    public static InformativeContentFragment newInstance(String param1, String param2) {
        InformativeContentFragment fragment = new InformativeContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformativeContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ViewContentViewModel.class);
        binding.contentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        SetUpListChanges();
        GetInformativeContent();
    }


    public void GetInformativeContent(){
        if(viewModel.getInformativeContentRequestStatus().getValue() != RequestStatus.LOADING) {
            SessionSingleton session = SessionSingleton.getInstance();
            String token = session.getToken();
            viewModel.GetInformativeContent(token);
        }
    }

    public void SetUpListChanges(){
        viewModel.getInformativeContentList().observe(getViewLifecycleOwner(), new Observer<List<InformativeContentJSONResponse>>(){
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
                    Toast.makeText(getContext(), "No hay artículos registrados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ShowInformativeContetData(InformativeContentJSONResponse informativeContentJSONResponse){
        Bundle bundle = new Bundle();
        bundle.putInt("id", informativeContentJSONResponse.getContentId());
        bundle.putString("title", informativeContentJSONResponse.getTitle());
        bundle.putString("description", informativeContentJSONResponse.getDescription());
        bundle.putString("creationDate", informativeContentJSONResponse.getCreationDate());
        bundle.putString("media", informativeContentJSONResponse.getImage());
        bundle.putString("username", informativeContentJSONResponse.getUsername());
        getParentFragmentManager().setFragmentResult("data", bundle);

        ViewContentFragment detailFragment = new ViewContentFragment();
        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();

    }

}