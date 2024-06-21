package com.ikariscraft.cyclecare.activities.register_reminder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.EditArticleRequest;
import com.ikariscraft.cyclecare.api.requests.UpdateReminderRequest;
import com.ikariscraft.cyclecare.databinding.FragmentUpdateReminderBinding;
import com.ikariscraft.cyclecare.model.Reminder;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class UpdateReminderFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RemindersViewModel viewModel;
    private FragmentUpdateReminderBinding binding;
    private Reminder reminderData;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText titleEditText, descriptionEditText;

    public UpdateReminderFragment() {
    }

    public static UpdateReminderFragment newInstance(String param1, String param2) {
        UpdateReminderFragment fragment = new UpdateReminderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RemindersViewModel.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateReminderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reminderData = new Reminder();
        titleEditText = view.findViewById(R.id.titleEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);

        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                int reminderId = result.getInt("reminderId");
                String title = result.getString("title");
                String description = result.getString("description");
                String creationDate = result.getString("creationDate");
                String username = result.getString("username");
                String scheduleId = result.getString("scheduleId");

                reminderData.setReminderId(reminderId);
                reminderData.setTitle(title);
                reminderData.setDescription(description);
                reminderData.setCreationDate(creationDate);
                reminderData.setUsername(username);
                reminderData.setScheduleId(scheduleId);

                Log.d("UpdateReminderFragment", "onFragmentResult: " + reminderData.getTitle());

                if (reminderData != null) {
                    titleEditText.setText(reminderData.getTitle());
                    descriptionEditText.setText(reminderData.getDescription());

                    // Parse the ISO 8601 date-time string
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

                    if (reminderData.getCreationDate() != null) {
                        LocalDateTime dateTime = LocalDateTime.parse(reminderData.getCreationDate(), formatter);

                        // Get the year, month, day, hour, and minute
                        int year = dateTime.getYear();
                        int month = dateTime.getMonthValue() - 1; // DatePicker months are 0-indexed
                        int day = dateTime.getDayOfMonth();
                        int hour = dateTime.getHour();
                        int minute = dateTime.getMinute();

                        datePicker.updateDate(year, month, day);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            timePicker.setHour(hour);
                            timePicker.setMinute(minute);
                        } else {
                            timePicker.setCurrentHour(hour);
                            timePicker.setCurrentMinute(minute);
                        }
                    }
                }
            }
        });

        setUpSaveReminderListener();
        setUpFieldsValidations();
        setUpButtons();
    }

    private void setUpSaveReminderListener() {
        viewModel.getUpdateReminderStatus().observe(getViewLifecycleOwner(), requestStatus -> {
            if (requestStatus == RequestStatus.DONE) {
                Toast.makeText(getContext(), "Recordatorio actualizado", Toast.LENGTH_SHORT).show();
                startRemindersFragment();
            } else if (requestStatus == RequestStatus.ERROR) {
                ProcessErrorCodes errorCode = viewModel.getUpdateReminderErrorCode().getValue();
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
        String message;

        switch (errorCode) {
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
            binding.errorTitleTextView.setVisibility(isTitleValid ? View.GONE : View.VISIBLE);
        });

        viewModel.isDescriptionValid().observe(getViewLifecycleOwner(), isDescriptionValid -> {
            binding.errorDescriptionTextView.setVisibility(isDescriptionValid ? View.GONE : View.VISIBLE);
        });

        viewModel.isDateValid().observe(getViewLifecycleOwner(), isDateValid -> {
            binding.errorDateTextView.setVisibility(isDateValid ? View.GONE : View.VISIBLE);
        });

        viewModel.isTimeValid().observe(getViewLifecycleOwner(), isTimeValid -> {
            binding.errorTimeTextView.setVisibility(isTimeValid ? View.GONE : View.VISIBLE);
        });
    }

    private void setUpButtons() {
        binding.btnSaveReminder.setOnClickListener(v -> {
            if (validateFields()) {
                saveReminder();
            }
        });
    }

    private void saveReminder() {
        if (viewModel.getUpdateReminderStatus().getValue() != RequestStatus.LOADING) {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            Calendar calendar = Calendar.getInstance();
            calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            String dateString = dateFormat.format(calendar.getTime());

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            }

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String timeString = timeFormat.format(calendar.getTime());

            SessionSingleton session = SessionSingleton.getInstance();
            String token = session.getToken();
            UpdateReminderRequest request = new UpdateReminderRequest();
            request.setTitle(title);
            request.setDescription(description);
            request.setCreationDate(dateString + " " + timeString);
            request.setScheduleId(reminderData.getScheduleId());

            viewModel.updateReminder(reminderData.getReminderId(), token, request);
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