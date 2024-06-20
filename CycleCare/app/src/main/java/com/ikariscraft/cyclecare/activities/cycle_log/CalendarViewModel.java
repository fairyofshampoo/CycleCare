package com.ikariscraft.cyclecare.activities.cycle_log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.CalendarJSONResponse;
import com.ikariscraft.cyclecare.repository.CycleLogRepository;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.util.List;

public class CalendarViewModel extends ViewModel {
    public CalendarViewModel() {
    }

    private final MutableLiveData<RequestStatus> calendarStatus = new MutableLiveData<>();
    private final MutableLiveData<List<CalendarJSONResponse>> cycleLogs = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> calendarErrorCode = new MutableLiveData<>();
    public LiveData<List<CalendarJSONResponse>> getCycleLogs() {
        return cycleLogs;
    }
    public LiveData<RequestStatus> getCalendarRequestStatus() {
        return calendarStatus;
    }
    public LiveData<ProcessErrorCodes> getCalendarErrorCode() {
        return calendarErrorCode;
    }

    public void getCycleLogs(String token, int year, int month) {
        calendarStatus.setValue(RequestStatus.LOADING);

        new CycleLogRepository().getCycleLogs(token, year, month, new IProcessStatusListener() {
                    @Override
                    public void onSuccess(Object data) {
                        List<CalendarJSONResponse> logs = (List<CalendarJSONResponse>) data;
                        cycleLogs.setValue(logs);
                        calendarStatus.setValue(RequestStatus.DONE);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        cycleLogs.setValue(null);
                        calendarErrorCode.setValue(errorCode);
                        calendarStatus.setValue(RequestStatus.ERROR);
                    }
                }
        );
    }
}
