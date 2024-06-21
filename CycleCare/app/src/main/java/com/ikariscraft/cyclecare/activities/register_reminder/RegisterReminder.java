package com.ikariscraft.cyclecare.activities.register_reminder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.CreateReminderRequest;
import com.ikariscraft.cyclecare.databinding.FragmentRegisterReminderBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterReminder extends Fragment {
    RemindersViewModel viewModel;
    FragmentRegisterReminderBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RegisterReminder() {
    }

    public static RegisterReminder newInstance(String param1, String param2) {
        RegisterReminder fragment = new RegisterReminder();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterReminderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RemindersViewModel.class);
        setUpButtons();
        setUpFieldsValidations();
        setUpSaveReminderListener();
    }

    private void setUpSaveReminderListener() {
        viewModel.getCreateReminderStatus().observe(getViewLifecycleOwner(), requestStatus -> {
            if (requestStatus == RequestStatus.DONE) {
                Toast.makeText(getContext(), "Recordatorio creado", Toast.LENGTH_SHORT).show();
                startRemindersFragment();
            } else if (requestStatus == RequestStatus.ERROR) {
                ProcessErrorCodes errorCode = viewModel.getCreateReminderErrorCode().getValue();
                if (errorCode != null) {
                    showErrorMessage(errorCode);
                }
            }
        });
    }

    private void startRemindersFragment() {
        RemindersFragment fragment = new RemindersFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void showErrorMessage(ProcessErrorCodes errorCode) {
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

    private void setUpFieldsValidations() {
        viewModel.isTitleValid().observe(getViewLifecycleOwner(), isTitleValid -> {
            if(isTitleValid){
                binding.errorTitleTextView.setVisibility(View.GONE);
            } else {
                binding.errorTitleTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isDescriptionValid().observe(getViewLifecycleOwner(), isDescriptionValid -> {
            if(isDescriptionValid){
                binding.errorDescriptionTextView.setVisibility(View.GONE);
            } else {
                binding.errorDescriptionTextView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.isDateValid().observe(getViewLifecycleOwner(), isDateValid -> {
            if(isDateValid){
                binding.errorDateTextView.setVisibility(View.GONE);
            } else {
                binding.errorDateTextView.setVisibility(android.view.View.VISIBLE);
            }
        });

        viewModel.isTimeValid().observe(getViewLifecycleOwner(), isTimeValid -> {
            if(isTimeValid){
                binding.errorTimeTextView.setVisibility(android.view.View.GONE);
            } else {
                binding.errorTimeTextView.setVisibility(android.view.View.VISIBLE);
            }
        });
    }

    private void setUpButtons() {
        binding.btnSaveReminder.setOnClickListener(v -> {
            if(validateFields()){
                saveReminder();
            }
        });
    }

    private void saveReminder() {
        if (viewModel.getCreateReminderStatus().getValue() != RequestStatus.LOADING) {
            String title = binding.titleEditText.getText().toString();
            String description = binding.descriptionEditText.getText().toString();

            DatePicker datePicker = binding.datePicker;
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            String dateString = dateFormat.format(calendar.getTime());

            TimePicker timePicker = binding.timePicker;
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String timeString = timeFormat.format(calendar.getTime());

            SessionSingleton session = SessionSingleton.getInstance();
            String token = session.getToken();
            CreateReminderRequest request = new CreateReminderRequest();
            request.setTitle(title);
            request.setDescription(description);
            request.setCreationDate(dateString + " " + timeString); // Concatenación de fecha y hora

            viewModel.createReminder(token, request);
        }
    }


    private boolean validateFields() {
        String title = binding.titleEditText.getText().toString();
        String description = binding.descriptionEditText.getText().toString();

        DatePicker datePicker = binding.datePicker;
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());

        TimePicker timePicker = binding.timePicker;
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeString = timeFormat.format(calendar.getTime());

        viewModel.validateTitle(title);
        viewModel.validateDescription(description);
        viewModel.validateDate(dateString);
        viewModel.validateTime(dateString, timeString);

        Boolean isTitleValid = viewModel.isTitleValid().getValue();
        Boolean isDescriptionValid = viewModel.isDescriptionValid().getValue();
        Boolean isDateValid = viewModel.isDateValid().getValue();
        Boolean isTimeValid = viewModel.isTimeValid().getValue();

        return Boolean.TRUE.equals(isTitleValid) && Boolean.TRUE.equals(isDescriptionValid) && Boolean.TRUE.equals(isDateValid) && Boolean.TRUE.equals(isTimeValid);
    }


}