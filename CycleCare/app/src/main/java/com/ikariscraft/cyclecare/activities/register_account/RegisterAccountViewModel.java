package com.ikariscraft.cyclecare.activities.register_account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.utilities.Validations;

public class RegisterAccountViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isNameValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFirstLastNameValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSecondLastNameValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUserValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPasswordValid = new MutableLiveData<>();

    public RegisterAccountViewModel() {

    }

    public LiveData<Boolean> isNameValid() {return isNameValid;}
    public LiveData<Boolean> isFirstLastNameValid(){return isFirstLastNameValid;}
    public LiveData<Boolean> isSecondLastNameValid (){return isSecondLastNameValid;}
    public LiveData<Boolean> isUserValid () {return isUserValid;}
    public LiveData<Boolean> isPasswordValid() {return isPasswordValid;}

    public void ValidateName(String name) {
        boolean validation = Validations.isNameValid(name);

        isNameValid.setValue(validation);
    }

    public void ValidateFirstLastName(String firstLastName) {
        boolean validation = Validations.isNameValid(firstLastName);

        isFirstLastNameValid.setValue(validation);
    }

    public void ValidateSecondLastName(String secondLastName) {
        boolean validation = Validations.isNameValid(secondLastName);

        isSecondLastNameValid.setValue(validation);
    }

    public void ValidateUsername(String user) {
        boolean validation = Validations.isUsernameValid(user);

        isUserValid.setValue(validation);
    }

    public void ValidatePassword(String password) {
        boolean validation = Validations.isPasswordValid(password);

        isPasswordValid.setValue(validation);
    }

}
