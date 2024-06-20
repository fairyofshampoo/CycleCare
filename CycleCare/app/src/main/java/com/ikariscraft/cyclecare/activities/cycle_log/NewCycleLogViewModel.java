package com.ikariscraft.cyclecare.activities.cycle_log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.NewCycleLogBody;
import com.ikariscraft.cyclecare.repository.CycleLogRepository;
import com.ikariscraft.cyclecare.repository.IEmptyProcessListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

public class NewCycleLogViewModel extends ViewModel {
    private final MutableLiveData<ProcessErrorCodes> createNewCycleLogErrorCode = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> createNewCycleLogRequestStatus = new MutableLiveData<>();

    public NewCycleLogViewModel() {

    }

    public LiveData<ProcessErrorCodes> getCreateNewCycleLogErrorCode() {return createNewCycleLogErrorCode;}
    public LiveData<RequestStatus> getCreateNewCycleLogRequestStatus() {return createNewCycleLogRequestStatus;}

    public void createNewCycleLog(String token, NewCycleLogBody newCycleLogBody) {
        createNewCycleLogRequestStatus.setValue(RequestStatus.LOADING);

        new CycleLogRepository().createNewCycleLog(token, newCycleLogBody,
                new IEmptyProcessListener() {
                    @Override
                    public void onSuccess() {
                        createNewCycleLogRequestStatus.setValue(RequestStatus.DONE);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        createNewCycleLogErrorCode.setValue(errorCode);
                        createNewCycleLogRequestStatus.setValue(RequestStatus.ERROR);
                    }
                });
    }
}
