package com.ikariscraft.cyclecare.activities.cycle_log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.PredictionJSONResponse;
import com.ikariscraft.cyclecare.databinding.FragmentCalendarBinding;
import com.ikariscraft.cyclecare.model.CycleLog;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalendarFragment extends Fragment {
    private CalendarViewModel viewModel;
    private FragmentCalendarBinding binding;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate currentDate;
    private LocalDate selectedDate;
    private List<CycleLog> cycleLogs = new ArrayList<>();
    private PredictionJSONResponse predictionResponse;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {

    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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

        currentDate = LocalDate.now();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        monthYearText = binding.monthYearTV;
        calendarRecyclerView = binding.calendarRecyclerView;
        viewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        setUpPredictionRequestStatusListener();
        setUpCalendarRequestStatusListener();
        setPrediction();
        setUpButtons();
        getCycleLogs();
        setUpGetCycleByDayStatusListener();
    }

    private void setPrediction() {
        SessionSingleton session = SessionSingleton.getInstance();
        String token = session.getToken();
        viewModel.getCyclePrediction(token);
    }

    private void setUpPredictionRequestStatusListener() {
        viewModel.getPredictedCycleStatus().observe(getViewLifecycleOwner(), requestStatus -> {
            switch (requestStatus) {
                case DONE:
                    predictionResponse = viewModel.getPredictionResponse().getValue();
                    showPrediction();
                    break;
                case ERROR:
                    ProcessErrorCodes errorCode = viewModel.getPredictionErrorCode().getValue();
                    if (errorCode != null) {
                        showPredictionError(errorCode);
                    }
                    break;
            }
        });
    }

    private void showPredictionError(ProcessErrorCodes errorCode) {
        String message = "";
        switch (errorCode){
            case NOT_FOUND_ERROR:
                showEmptyPrediction();
                break;
            case SERVICE_NOT_AVAILABLE_ERROR:
                message = getString(R.string.login_server_error_message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmptyPrediction() {
        binding.tvPhaseTitle.setText(getString(R.string.cycle_log_no_prediction));
        binding.tvPhaseDescription.setText(getString(R.string.cycle_log_no_prediction_description));
    }

    private void showPrediction() {
        int daysToPeriod = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        if(predictionResponse != null){
            LocalDate nextPeriodStartDate = LocalDate.parse(predictionResponse.getNextPeriodStartDate(), formatter);
            LocalDate currentDate = LocalDate.now();

            if(currentDate.isBefore(nextPeriodStartDate)){
                daysToPeriod = (int) currentDate.until(nextPeriodStartDate).getDays();
            }
        }
        
        showDaysToPeriod(daysToPeriod);
    }

    private void showDaysToPeriod(int daysToPeriod) {
        if (daysToPeriod <= 0) {
            binding.tvPhaseTitle.setText(R.string.menstrual_phase);
            binding.tvPhaseDescription.setText(R.string.menstrual_phase_description);
        } else if (daysToPeriod <= 4) {
            binding.tvPhaseTitle.setText(R.string.follicular_phase);
            binding.tvPhaseDescription.setText(R.string.follicular_phase_description);
        } else if (daysToPeriod <= 14) {
            binding.tvPhaseTitle.setText(R.string.ovulation_phase);
            binding.tvPhaseDescription.setText(R.string.ovulation_phase_description);
        } else {
            binding.tvPhaseTitle.setText(R.string.luteal_phase);
            binding.tvPhaseDescription.setText(R.string.luteal_phase_description);
        }

        binding.tvDaysToPeriod.setText(String.valueOf(daysToPeriod));
    }

    private void setUpCalendarRequestStatusListener() {
        viewModel.getCalendarRequestStatus().observe(getViewLifecycleOwner(), requestStatus -> {
            switch (requestStatus) {
                case DONE:
                    cycleLogs = Objects.requireNonNull(viewModel.getCalendarResponse().getValue()).getCycleLogs();
                    setMonthView();
                    break;
                case ERROR:
                    ProcessErrorCodes errorCode = viewModel.getCalendarErrorCode().getValue();
                    if (errorCode != null) {
                        showCalendarError(errorCode);
                    }
                    break;
            }
        });
    }

    private void showCalendarError(ProcessErrorCodes errorCode) {
        String message = "";
        switch (errorCode){
            case NOT_FOUND_ERROR:
                setMonthView();
                break;
            case SERVICE_NOT_AVAILABLE_ERROR:
                message = getString(R.string.login_server_error_message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void getCycleLogs() {
        SessionSingleton session = SessionSingleton.getInstance();
        String token = session.getToken();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        viewModel.getCycleLogs(token, year, month);
    }

    private void setUpButtons() {
        binding.previousMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction(v);
            }
        });

        binding.nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction(v);
            }
        });

        binding.btnNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel.getGetCycleLogByDayStatus().getValue() != RequestStatus.LOADING){

                    LocalDate currentDate = LocalDate.now();
                    String day = currentDate.getDayOfMonth() + "";

                    showCycleLog(day, currentDate);
                }
            }
        });
    }


    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(currentDate));
        ArrayList<String> daysInMonth = daysInMonthArray(currentDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, cycleLogs, currentDate, predictionResponse);
        calendarAdapter.setOnItemClickListener((position, dayText) -> {
            openCycleLog(dayText);
        });
        int daysOfWeek = 7;
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), daysOfWeek));
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    private void openCycleLog(String dayText) {
        if (!dayText.isEmpty()) {
            if(viewModel.getGetCycleLogByDayStatus().getValue() != RequestStatus.LOADING){
                showCycleLog(dayText, currentDate);
            }
        }
    }

    private void showCycleLog(String dayText, LocalDate date){
        if(viewModel.getGetCycleLogByDayStatus().getValue() != RequestStatus.LOADING){
            int day = Integer.parseInt(dayText);
            int month = date.getMonthValue();
            int year = date.getYear();
            selectedDate = LocalDate.of(year, month, day);
            SessionSingleton session = SessionSingleton.getInstance();
            String token = session.getToken();
            viewModel.getCycleLogByDay(token, year, month, day);
        }
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        currentDate = currentDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        currentDate = currentDate.plusMonths(1);
        setMonthView();
    }

    private void setUpGetCycleByDayStatusListener(){
        viewModel.getGetCycleLogByDayStatus().observe(getViewLifecycleOwner(), requestStatus -> {
            switch (requestStatus){
                case DONE:
                    startUpdateCycleLogActivity(viewModel.getCycleLog().getValue());
                    break;
                case ERROR:
                    ProcessErrorCodes errorCode = viewModel.getCycleLogErrorCode().getValue();
                    if(errorCode != null){
                        showCycleLogError(errorCode);
                    }
                    break;
            }
        });
    }

    private void showCycleLogError(ProcessErrorCodes errorCode) {
        String message = "";
        switch (errorCode){
            case NOT_FOUND_ERROR:
                startNewCycleLogActivity();
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void startNewCycleLogActivity() {
        Intent intent = new Intent(requireActivity(), CycleLogActivity.class);
        intent.putExtra(CycleLogActivity.SELECTED_DATE_KEY, selectedDate.toString());
        startActivity(intent);
    }

    private void startUpdateCycleLogActivity(CycleLog cycleLog) {
        if(cycleLog != null){
            Intent intent = new Intent(requireActivity(), UpdateCycleLogActivity.class);
            intent.putExtra(UpdateCycleLogActivity.CYCLE_LOG_KEY, cycleLog);
            startActivity(intent);
        }

    }
}