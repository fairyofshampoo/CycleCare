package com.ikariscraft.cyclecare.activities.register_reminder;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.CreateReminderRequest;
import com.ikariscraft.cyclecare.api.requests.UpdateReminderRequest;
import com.ikariscraft.cyclecare.api.responses.UserRemindersResponse;
import com.ikariscraft.cyclecare.repository.IEmptyProcessListener;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.repository.ReminderRepository;
import com.ikariscraft.cyclecare.utilities.Validations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RemindersViewModel extends ViewModel {

    public RemindersViewModel() {
    }

    private final MutableLiveData<RequestStatus> remindersStatus = new MutableLiveData<>();
    private final MutableLiveData<UserRemindersResponse> remindersResponse = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> remindersErrorCode = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> createReminderStatus = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> createReminderErrorCode = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> updateReminderStatus = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> updateReminderErrorCode = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isTitleValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isDateValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isTimeValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isDescriptionValid = new MutableLiveData<>();

    public LiveData<Boolean> isTitleValid() {
        return isTitleValid;
    }

    public LiveData<Boolean> isDateValid() {
        return isDateValid;
    }

    public LiveData<Boolean> isTimeValid() {
        return isTimeValid;
    }

    public LiveData<Boolean> isDescriptionValid() {
        return isDescriptionValid;
    }

    public LiveData<RequestStatus> getRemindersStatus() {
        return remindersStatus;
    }

    public LiveData<UserRemindersResponse> getRemindersResponse() {
        return remindersResponse;
    }

    public LiveData<ProcessErrorCodes> getRemindersErrorCode() {
        return remindersErrorCode;
    }

    public LiveData<RequestStatus> getCreateReminderStatus() {
        return createReminderStatus;
    }

    public LiveData<ProcessErrorCodes> getCreateReminderErrorCode() {
        return createReminderErrorCode;
    }

    public LiveData<RequestStatus> getUpdateReminderStatus() {
        return updateReminderStatus;
    }

    public LiveData<ProcessErrorCodes> getUpdateReminderErrorCode() {
        return updateReminderErrorCode;
    }

    public void validateTitle(String title) {
        boolean validation = Validations.isTitleValid(title);

        isTitleValid.setValue(validation);
    }

    public void validateDate(String date) {
        if (date == null || date.isEmpty()) {
            isDateValid.setValue(false);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false);

        try {
            Date inputDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = calendar.getTime();

            isDateValid.setValue(!inputDate.before(today));
        } catch (ParseException e) {
            isDateValid.setValue(false);
        }
    }

    public void validateTime(String date, String time) {
        if (date == null || date.isEmpty() || time == null || time.isEmpty()) {
            isTimeValid.setValue(false);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        sdf.setLenient(false);

        try {
            Date inputDateTime = sdf.parse(date + " " + time);
            Date now = new Date();

            isTimeValid.setValue(!inputDateTime.before(now));
        } catch (ParseException e) {
            isTimeValid.setValue(false);
        }
    }

    public void validateDescription(String description) {
        boolean validation = Validations.isDescriptionValid(description);

        isDescriptionValid.setValue(validation);
    }

    public void getReminders(String token) {
        remindersStatus.setValue(RequestStatus.LOADING);

        new ReminderRepository().getReminders(token, new IProcessStatusListener() {
            @Override
            public void onSuccess(Object data) {
                UserRemindersResponse response = (UserRemindersResponse) data;
                remindersResponse.setValue(response);
                remindersStatus.setValue(RequestStatus.DONE);
            }

            @Override
            public void onError(ProcessErrorCodes errorCode) {
                remindersErrorCode.setValue(errorCode);
                remindersStatus.setValue(RequestStatus.ERROR);
            }
        });
    }

    public void updateReminder(int reminderId, String token, UpdateReminderRequest request) {
        updateReminderStatus.setValue(RequestStatus.LOADING);

        new ReminderRepository().updateReminder(token, reminderId, request, new IEmptyProcessListener() {
            @Override
            public void onSuccess() {
                updateReminderStatus.setValue(RequestStatus.DONE);
            }

            @Override
            public void onError(ProcessErrorCodes errorCode) {
                updateReminderErrorCode.setValue(errorCode);
                updateReminderStatus.setValue(RequestStatus.ERROR);
            }
        });
    }

    public void createReminder(String token, CreateReminderRequest request) {
        createReminderStatus.setValue(RequestStatus.LOADING);

        new ReminderRepository().registerReminder(token, request, new IEmptyProcessListener() {
            @Override
            public void onSuccess() {
                createReminderStatus.setValue(RequestStatus.DONE);
            }

            @Override
            public void onError(ProcessErrorCodes errorCode) {
                createReminderErrorCode.setValue(errorCode);
                createReminderStatus.setValue(RequestStatus.ERROR);
            }
        });
    }
}
