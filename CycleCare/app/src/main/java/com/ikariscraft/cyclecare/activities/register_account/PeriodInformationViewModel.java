package com.ikariscraft.cyclecare.activities.register_account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.repository.IEmptyProcessListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.repository.RegisterUserRepository;
import com.ikariscraft.cyclecare.utilities.Validations;

public class PeriodInformationViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isPeriodDurationValid = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isCycleDurationValid = new MutableLiveData<>();

    private final MutableLiveData<RequestStatus> registerUserRequestStatus = new MutableLiveData<>();

    private final MutableLiveData<ProcessErrorCodes> registerErrorCode = new MutableLiveData<>();

    public PeriodInformationViewModel(){

    }

    public LiveData<Boolean> isPeriodDurationValid() { return isPeriodDurationValid;}

    public LiveData<Boolean> isCycleDurationValid(){return isCycleDurationValid;}

    public LiveData<RequestStatus> getRegisterRequestStatus() {return registerUserRequestStatus;}

    public LiveData<ProcessErrorCodes> getRegisterErrorCode() {return registerErrorCode;}

    public  void ValidatePeriodDuration(String periodDuration) {
        boolean validation = Validations.isNotEmpty(periodDuration);

        isPeriodDurationValid.setValue(validation);
    }

    public void ValidateCycleDuration(String cycleDuration){
        boolean validation = Validations.isNotEmpty(cycleDuration);

        isCycleDurationValid.setValue(validation);
    }

    public void registerNewAccount(UserRegisterData data){
        registerUserRequestStatus.setValue(RequestStatus.LOADING);

        new RegisterUserRepository().registerUser(
                data,
                new IEmptyProcessListener() {
                    @Override
                    public void onSuccess() {
                        registerUserRequestStatus.setValue(RequestStatus.DONE);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        registerErrorCode.setValue(errorCode);
                        registerUserRequestStatus.setValue(RequestStatus.ERROR);
                    }
                }
        );
    }


}
