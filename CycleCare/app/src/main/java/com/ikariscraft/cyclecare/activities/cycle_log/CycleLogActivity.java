package com.ikariscraft.cyclecare.activities.cycle_log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.NewCycleLogBody;
import com.ikariscraft.cyclecare.databinding.ActivityCycleLogBinding;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CycleLogActivity extends AppCompatActivity {
    private ActivityCycleLogBinding binding;
    private NewCycleLogViewModel viewModel;
    public static final String SELECTED_DATE_KEY = "selected_date_key";
    public String selectedDate;
    public int menstrualFlowSelected = 0;
    public int vaginalFlowSelected = 0;
    public List<Integer> moodsSelected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCycleLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NewCycleLogViewModel.class);

        Bundle extras = getIntent().getExtras();
        selectedDate = extras.getString("selected_date_key");

        binding.tvDate.setText(selectedDate);
        setUpButtons();
        setUpOperationStatusListener();
    }

    private void setUpOperationStatusListener() {
        viewModel.getCreateNewCycleLogRequestStatus().observe(this, requestStatus -> {
            if (requestStatus == RequestStatus.DONE) {
                Toast.makeText(this, "Se ha registrado el ciclo", Toast.LENGTH_SHORT).show();
                finish();
            }

            if (requestStatus == RequestStatus.ERROR) {
                ProcessErrorCodes errorCode = viewModel.getCreateNewCycleLogErrorCode().getValue();

                if(errorCode != null) {
                    showNewCycleLogError(errorCode);
                }

            }
        });
    }

    private void showNewCycleLogError(ProcessErrorCodes errorCode) {
        String message = "";

        switch (errorCode){
            case SERVICE_NOT_AVAILABLE_ERROR:
                message = getString(R.string.login_server_error_message);
                break;
            default:
                message = getString(R.string.login_fatal_error_message);
                break;
        }

        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    private void setUpButtons() {
        binding.btnSave.setOnClickListener(v -> {
            if(viewModel.getCreateNewCycleLogRequestStatus().getValue() != RequestStatus.LOADING) {
                saveNewCycleLog();
            }
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        setUpMenstrualFlowButtons();
        setUpVaginalFlowButtons();
        setUpMoodButtons();
    }

    private void setUpMoodButtons() {
        binding.happyMood.setOnClickListener(v -> {
            if(moodsSelected.size() > 0){
                if(moodsSelected.contains(1)) {
                    moodsSelected.remove(Integer.valueOf(1));
                    binding.happyMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(1);
                    binding.happyMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(1);
                binding.happyMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.sadMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(2)) {
                    moodsSelected.remove(Integer.valueOf(2));
                    binding.sadMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(2);
                    binding.sadMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(2);
                binding.sadMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.anxiousMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(3)) {
                    moodsSelected.remove(Integer.valueOf(3));
                    binding.anxiousMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(3);
                    binding.anxiousMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(3);
                binding.anxiousMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.angryMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(4)) {
                    moodsSelected.remove(Integer.valueOf(4));
                    binding.angryMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(4);
                    binding.angryMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(4);
                binding.angryMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.tiredMood.setOnClickListener(v -> {
            if (!moodsSelected.isEmpty()) {
                if (moodsSelected.contains(5)) {
                    moodsSelected.remove(Integer.valueOf(5));
                    binding.tiredMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(5);
                    binding.tiredMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(5);
                binding.tiredMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.energeticMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(6)) {
                    moodsSelected.remove(Integer.valueOf(6));
                    binding.energeticMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(6);
                    binding.energeticMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(6);
                binding.energeticMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.relaxedMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(7)) {
                    moodsSelected.remove(Integer.valueOf(7));
                    binding.relaxedMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(7);
                    binding.relaxedMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(7);
                binding.relaxedMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.focusedMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(8)) {
                    moodsSelected.remove(Integer.valueOf(8));
                    binding.focusedMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(8);
                    binding.focusedMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(8);
                binding.focusedMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.stressedMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(9)) {
                    moodsSelected.remove(Integer.valueOf(9));
                    binding.stressedMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(9);
                    binding.stressedMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(9);
                binding.stressedMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });

        binding.irritableMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(10)) {
                    moodsSelected.remove(Integer.valueOf(10));
                    binding.irritableMood.setBackgroundColor(getResources().getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(10);
                    binding.irritableMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(10);
                binding.irritableMood.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });
    }

    private void setUpVaginalFlowButtons() {

        binding.dryFlow.setOnClickListener(v -> {
            int vaginalFlow = 1;
            vaginalFlowSelected = vaginalFlow;
            binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
        });

        binding.stickyFlow.setOnClickListener(v -> {
            int vaginalFlow = 2;
            vaginalFlowSelected = vaginalFlow;
            binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
        });

        binding.creamyFlow.setOnClickListener(v -> {
            int vaginalFlow = 3;
            vaginalFlowSelected = vaginalFlow;
            binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
        });

        binding.aqueousFlow.setOnClickListener(v -> {
            int vaginalFlow = 4;
            vaginalFlowSelected = vaginalFlow;
            binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
        });

        binding.elasticFlow.setOnClickListener(v -> {
            int vaginalFlow = 5;
            vaginalFlowSelected = vaginalFlow;
            binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        });
    }

    private void setUpMenstrualFlowButtons() {
        binding.lightFlow.setOnClickListener(v -> {
            int menstrualFlow = 1;
            menstrualFlowSelected = menstrualFlow;
            binding.lightFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            binding.mediumFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.heavyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
        });

        binding.mediumFlow.setOnClickListener(v -> {
            int menstrualFlow = 2;
            menstrualFlowSelected = menstrualFlow;
            binding.lightFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.mediumFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            binding.heavyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
        });

        binding.heavyFlow.setOnClickListener(v -> {
            int menstrualFlow = 3;
            menstrualFlowSelected = menstrualFlow;
            binding.lightFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.mediumFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            binding.heavyFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        });
    }

    private void saveNewCycleLog() {
        NewCycleLogBody newCycleLogBody = new NewCycleLogBody();
        newCycleLogBody.setCreationDate(selectedDate);

        if(binding.sleepHours.getText() != null) {
            newCycleLogBody.setSleepHours(Integer.parseInt(binding.sleepHours.getText().toString()));
        }

        if(binding.noteEditText.getText() != null) {
            newCycleLogBody.setNote(binding.noteEditText.getText().toString());
        }
    }
}