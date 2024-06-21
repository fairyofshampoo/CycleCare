package com.ikariscraft.cyclecare.activities.register_reminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.UserRemindersResponse;
import com.ikariscraft.cyclecare.databinding.FragmentRemindersBinding;
import com.ikariscraft.cyclecare.model.Reminder;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

public class RemindersFragment extends Fragment {
    FragmentRemindersBinding binding;
    RemindersViewModel viewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RemindersFragment() {
    }

    public static RemindersFragment newInstance(String param1, String param2) {
        RemindersFragment fragment = new RemindersFragment();
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
        binding = FragmentRemindersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RemindersViewModel.class);
        binding.remindersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        observeAndDisplayReminders();
        getReminders();
        setUpButtons();
    }

    private void setUpButtons() {
        binding.btnNewEntry.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegisterReminder())
                    .addToBackStack(null).commit();
        });
    }

    private void observeAndDisplayReminders() {
        viewModel.getRemindersResponse().observe(getViewLifecycleOwner(), new Observer<UserRemindersResponse>() {
            @Override
            public void onChanged(UserRemindersResponse userRemindersResponse) {
                if (userRemindersResponse != null) {
                    ReminderAdapter adapter = new ReminderAdapter();
                    binding.remindersRecycler.setAdapter(adapter);
                    adapter.setOnItemClickListener(reminder -> {
                        showReminderDetails(reminder);
                    });
                    binding.remindersNotFoundTV.setVisibility(View.GONE);
                    adapter.submitList(userRemindersResponse.getReminders());
                } else {
                    binding.remindersNotFoundTV.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getRemindersStatus().observe(getViewLifecycleOwner(), new Observer<RequestStatus>() {
            @Override
            public void onChanged(RequestStatus status) {
                if (status == RequestStatus.ERROR) {
                    Toast.makeText(getContext(), "Error al obtener los recordatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showReminderDetails(Reminder reminder) {
        Bundle bundle = new Bundle();
        bundle.putInt("reminderId", reminder.getReminderId());
        bundle.putString("title", reminder.getTitle());
        bundle.putString("description", reminder.getDescription());
        bundle.putString("creationDate", reminder.getCreationDate());
        Log.d("CreationDateInReminders", reminder.getCreationDate());
        bundle.putString("username", reminder.getUsername());
        bundle.putString("scheduleId" , reminder.getScheduleId());
        getParentFragmentManager().setFragmentResult("data", bundle);
        UpdateReminderFragment updateReminderFragment = new UpdateReminderFragment();
        updateReminderFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, updateReminderFragment)
                .addToBackStack(null).commit();
    }

    private void getReminders() {
        if (viewModel.getRemindersStatus().getValue() != RequestStatus.LOADING) {
            SessionSingleton session = SessionSingleton.getInstance();
            String token = session.getToken();
            viewModel.getReminders(token);
        }
    }
}