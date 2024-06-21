package com.ikariscraft.cyclecare.activities.cycle_log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.R;
import com.ikariscraft.cyclecare.activities.main_screen.PrincipalScreen;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.NewCycleLogBody;
import com.ikariscraft.cyclecare.databinding.ActivityCycleLogBinding;
import com.ikariscraft.cyclecare.model.BirthControl;
import com.ikariscraft.cyclecare.model.Medication;
import com.ikariscraft.cyclecare.model.Mood;
import com.ikariscraft.cyclecare.model.Pill;
import com.ikariscraft.cyclecare.model.Symptom;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

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
                Toast.makeText(this, "Se ha registrado el ciclo",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PrincipalScreen.class);
                startActivity(intent);
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
                    binding.happyMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(1);
                    binding.happyMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
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
                    binding.sadMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(2);
                    binding.sadMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(2);
                binding.sadMood.setBackgroundColor(getResources().
                        getColor(R.color.dark_gray));
            }
        });

        binding.anxiousMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(3)) {
                    moodsSelected.remove(Integer.valueOf(3));
                    binding.anxiousMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(3);
                    binding.anxiousMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
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
                    binding.angryMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(4);
                    binding.angryMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
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
                    binding.tiredMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(5);
                    binding.tiredMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
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
                    binding.energeticMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(6);
                    binding.energeticMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(6);
                binding.energeticMood.setBackgroundColor(getResources().
                        getColor(R.color.dark_gray));
            }
        });

        binding.relaxedMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(7)) {
                    moodsSelected.remove(Integer.valueOf(7));
                    binding.relaxedMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(7);
                    binding.relaxedMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(7);
                binding.relaxedMood.setBackgroundColor(getResources().
                        getColor(R.color.dark_gray));
            }
        });

        binding.focusedMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(8)) {
                    moodsSelected.remove(Integer.valueOf(8));
                    binding.focusedMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(8);
                    binding.focusedMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
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
                    binding.stressedMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(9);
                    binding.stressedMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(9);
                binding.stressedMood.setBackgroundColor(getResources().
                        getColor(R.color.dark_gray));
            }
        });

        binding.irritableMood.setOnClickListener(v -> {
            if(!moodsSelected.isEmpty()){
                if(moodsSelected.contains(10)) {
                    moodsSelected.remove(Integer.valueOf(10));
                    binding.irritableMood.setBackgroundColor(getResources().
                            getColor(R.color.light_gray));
                } else {
                    moodsSelected.add(10);
                    binding.irritableMood.setBackgroundColor(getResources().
                            getColor(R.color.dark_gray));
                }
            } else {
                moodsSelected.add(10);
                binding.irritableMood.setBackgroundColor(getResources().
                        getColor(R.color.dark_gray));
            }
        });
    }

    private void setUpVaginalFlowButtons() {

        binding.dryFlow.setOnClickListener(v -> {
            int vaginalFlow = 1;
            if(vaginalFlowSelected == vaginalFlow) {
                vaginalFlowSelected = 0;
                binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                vaginalFlowSelected = vaginalFlow;
                binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });

        binding.stickyFlow.setOnClickListener(v -> {
            int vaginalFlow = 2;
            if (vaginalFlowSelected == vaginalFlow) {
                vaginalFlowSelected = 0;
                binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                vaginalFlowSelected = vaginalFlow;
                binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });

        binding.creamyFlow.setOnClickListener(v -> {
            int vaginalFlow = 3;
            if(vaginalFlowSelected == vaginalFlow) {
                vaginalFlowSelected = 0;
                binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                vaginalFlowSelected = vaginalFlow;
                binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });

        binding.aqueousFlow.setOnClickListener(v -> {
            int vaginalFlow = 4;
            if(vaginalFlowSelected == vaginalFlow) {
                vaginalFlowSelected = 0;
                binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                vaginalFlowSelected = vaginalFlow;
                binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });

        binding.elasticFlow.setOnClickListener(v -> {
            int vaginalFlow = 5;
            if(vaginalFlowSelected == vaginalFlow) {
                vaginalFlowSelected = 0;
                binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                vaginalFlowSelected = vaginalFlow;
                binding.dryFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.stickyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.creamyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.aqueousFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.elasticFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });
    }

    private void setUpMenstrualFlowButtons() {
        binding.lightFlow.setOnClickListener(v -> {
            int menstrualFlow = 1;
            if(menstrualFlowSelected == menstrualFlow) {
                menstrualFlowSelected = 0;
                binding.lightFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                menstrualFlowSelected = menstrualFlow;
                binding.lightFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                binding.mediumFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.heavyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });

        binding.mediumFlow.setOnClickListener(v -> {
            int menstrualFlow = 2;
            if (menstrualFlowSelected == menstrualFlow) {
                menstrualFlowSelected = 0;
                binding.mediumFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                menstrualFlowSelected = menstrualFlow;
                binding.lightFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.mediumFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                binding.heavyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });

        binding.heavyFlow.setOnClickListener(v -> {
            int menstrualFlow = 3;
            if (menstrualFlowSelected == menstrualFlow) {
                menstrualFlowSelected = 0;
                binding.heavyFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
            } else {
                menstrualFlowSelected = menstrualFlow;
                binding.lightFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.mediumFlow.setBackgroundColor(getResources().getColor(R.color.light_gray));
                binding.heavyFlow.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            }
        });
    }

    private void saveNewCycleLog() {
        SessionSingleton sessionSingleton = SessionSingleton.getInstance();
        String token = sessionSingleton.getToken();
        NewCycleLogBody newCycleLogBody = new NewCycleLogBody();
        newCycleLogBody.setCreationDate(selectedDate);

        if(binding.sleepHours.getSelectedItem() != null) {
            int sleepHours = binding.sleepHours.getSelectedItemPosition();
            if(sleepHours > 0) {
                newCycleLogBody.setSleepHours(sleepHours);
            }
        }

        if(binding.noteEditText.getText() != null) {
            newCycleLogBody.setNote(binding.noteEditText.getText().toString());
        }

        if(menstrualFlowSelected > 0) {
            newCycleLogBody.setMenstrualFlowId(menstrualFlowSelected);
        }

        if(vaginalFlowSelected > 0) {
            newCycleLogBody.setVaginalFlowId(vaginalFlowSelected);
        }

        List<Integer> symptomsId = getSymptomsIdSelected();
        if(!symptomsId.isEmpty()) {
            newCycleLogBody.setSymptoms(getSymptomsSelected(symptomsId));
        }

        if(!moodsSelected.isEmpty()) {
            newCycleLogBody.setMoods(getMoodsSelected());
        }

        List<Integer> medicationsId = getMedicationsIdSelected();
        if(!medicationsId.isEmpty()) {
            newCycleLogBody.setMedications(getMedicationsSelected(medicationsId));
        }

        List<Integer> pillsId = getPillsIdSelected();
        if(!pillsId.isEmpty()) {
            newCycleLogBody.setPills(getPillsSelected(pillsId));
        }

        List<Integer> birthControlId = getBirthControlIdSelected();
        if(!birthControlId.isEmpty()) {
            newCycleLogBody.setBirthControls(getBirthControlSelected(birthControlId));
        }

        viewModel.createNewCycleLog(token, newCycleLogBody);
    }

    private List<BirthControl> getBirthControlSelected(List<Integer> birthControlId) {
        List<BirthControl> birthControls = new ArrayList<>();
        for (Integer birthControl : birthControlId) {
            BirthControl birthControlToAdd = new BirthControl();
            birthControlToAdd.setBirthControlId(birthControl);
            birthControls.add(birthControlToAdd);
        }
        return birthControls;
    }

    private List<Integer> getBirthControlIdSelected() {
        List<Integer> birthControlSelected = new ArrayList<>();
        List<RadioButton> radioButtons = new ArrayList<>(
                List.of(binding.rbInserted, binding.rbRemoved)
        );

        for (RadioButton radioButton : radioButtons) {
            if(radioButton.isChecked()) {
                birthControlSelected.add(Integer.parseInt(radioButton.getTag().toString()));
            }
        }

        return birthControlSelected;
    }

    private List<Pill> getPillsSelected(List<Integer> pillsId) {
        List<Pill> pills = new ArrayList<>();
        for (Integer pillId : pillsId) {
            Pill pill = new Pill();
            pill.setPillId(pillId);
            pills.add(pill);
        }
        return pills;
    }

    private List<Integer> getPillsIdSelected() {
        List<Integer> pillsSelected = new ArrayList<>();
        List<CheckBox> checkBoxes = new ArrayList<>(
                List.of(binding.pillTakenCheckBox, binding.pillForgottenCheckBox,
                        binding.doubleDoseCheckBox, binding.noDoseCheckBox,
                        binding.lateDoseCheckBox)
        );

        for (CheckBox checkBox : checkBoxes) {
            if(checkBox.isChecked()) {
                pillsSelected.add(Integer.parseInt(checkBox.getTag().toString()));
            }
        }

        return pillsSelected;
    }

    private List<Medication> getMedicationsSelected(List<Integer> medicationsId) {
        List<Medication> medications = new ArrayList<>();
        for (Integer medicationId : medicationsId) {
            Medication medication = new Medication();
            medication.setMedicationId(medicationId);
            medications.add(medication);
        }
        return medications;
    }

    private List<Integer> getMedicationsIdSelected() {
        List<Integer> medicationsSelected = new ArrayList<>();
        List<CheckBox> checkBoxes = new ArrayList<>(
                List.of(binding.hormoneTherapyCheckBox, binding.emergencyPillCheckBox,
                        binding.painkillersCheckBox, binding.antidepressantsCheckBox,
                        binding.antibioticsCheckBox, binding.antihistaminesCheckBox)
        );

        for (CheckBox checkBox : checkBoxes) {
            if(checkBox.isChecked()) {
                medicationsSelected.add(Integer.parseInt(checkBox.getTag().toString()));
            }
        }

        return medicationsSelected;
    }

    private List<Symptom> getSymptomsSelected(List<Integer> symptomsId) {
        List<Symptom> symptoms = new ArrayList<>();
        for (Integer symptomId : symptomsId) {
            Symptom symptom = new Symptom();
            symptom.setSymptomId(symptomId);
            symptoms.add(symptom);
        }
        return symptoms;
    }

    private List<Integer> getSymptomsIdSelected() {
        List<Integer> symptomsSelected = new ArrayList<>();
        List<CheckBox> checkBoxes = new ArrayList<>(
                List.of(binding.abdominalPainCheckBox, binding.breastTendernessCheckBox,
                        binding.acneCheckBox, binding.bloatingCheckBox, binding.bloatingCheckBox,
                        binding.fatigueCheckBox, binding.headacheCheckBox, binding.nauseaCheckBox,
                        binding.dizzinessCheckBox, binding.cravingsCheckBox,
                        binding.constipationCheckBox, binding.diarrheaCheckBox,
                        binding.vaginalItchingCheckBox, binding. vulvaPainCheckBox)
        );

        for (CheckBox checkBox : checkBoxes) {
            if(checkBox.isChecked()) {
                symptomsSelected.add(Integer.parseInt(checkBox.getTag().toString()));
            }
        }

        return symptomsSelected;
    }

    private List<Mood> getMoodsSelected() {
        List<Mood> moods = new ArrayList<>();
        for (Integer moodId : moodsSelected) {
            Mood mood = new Mood();
            mood.setMoodId(moodId);
            moods.add(mood);
        }
        return moods;
    }
}