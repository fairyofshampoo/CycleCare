package com.ikariscraft.cyclecare.activities.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.UserCredentialsBody;
import com.ikariscraft.cyclecare.repository.IEmptyProcessListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.PasswordUtilities;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isUserValid = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isPasswordValid = new MutableLiveData<>();

    private final MutableLiveData<RequestStatus> loginRequestStatus = new MutableLiveData<>();

    private final MutableLiveData<ProcessErrorCodes> loginErrorCode = new MutableLiveData<>();

    public LoginViewModel() {

    }

    public LiveData<Boolean> isUserValid() {return isUserValid;}

    public LiveData<Boolean> isPasswordValid() {return isPasswordValid;}

    public LiveData<RequestStatus> getLoginRequestStatus() {return loginRequestStatus;}

    public LiveData<ProcessErrorCodes> getLoginErrorCode() {return loginErrorCode;}

    public void validatePassword(String password){
        boolean validation = PasswordUtilities.isPasswordValid(password);

        isPasswordValid.setValue(validation);
    }

    public void validateUser (String user) {
        boolean validation = PasswordUtilities.isPasswordValid(user);

        isUserValid.setValue(validation);
    }

    public void login(String user, String password){
        loginRequestStatus.setValue(RequestStatus.LOADING);

        new UserCredentialsBody(user, password);

        new IEmptyProcessListener() {

            @Override
            public void onSuccess() {
                loginRequestStatus.setValue(RequestStatus.DONE);
            }

            @Override
            public void onError(ProcessErrorCodes errorCode) {
                loginErrorCode.setValue(errorCode);
                loginRequestStatus.setValue(RequestStatus.ERROR);
            }
        };
    }

}
