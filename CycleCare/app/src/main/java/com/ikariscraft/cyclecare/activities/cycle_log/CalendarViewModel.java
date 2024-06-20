package com.ikariscraft.cyclecare.activities.cycle_log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.CalendarJSONResponse;
import com.ikariscraft.cyclecare.model.CycleLog;
import com.ikariscraft.cyclecare.repository.CycleLogRepository;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.util.List;

public class CalendarViewModel extends ViewModel {
    public CalendarViewModel() {
    }

    private final MutableLiveData<RequestStatus> calendarStatus = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> getCycleLogByDayStatus = new MutableLiveData<>();
    private final MutableLiveData<List<CalendarJSONResponse>> cycleLogs = new MutableLiveData<>();
    private final MutableLiveData<CycleLog> cycleLog = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> cycleLogErrorCode = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> calendarErrorCode = new MutableLiveData<>();
    public LiveData<List<CalendarJSONResponse>> getCycleLogs() {
        return cycleLogs;
    }
    public LiveData<CycleLog> getCycleLog() {
        return cycleLog;
    }
    public LiveData<RequestStatus> getCalendarRequestStatus() {
        return calendarStatus;
    }

    public LiveData<RequestStatus> getGetCycleLogByDayStatus() {
        return getCycleLogByDayStatus;
    }
    public LiveData<ProcessErrorCodes> getCalendarErrorCode() {
        return calendarErrorCode;
    }
    public LiveData<ProcessErrorCodes> getCycleLogErrorCode() {
        return cycleLogErrorCode;
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

    public void getCycleLogByDay(String token, int year, int month, int day) {
        calendarStatus.setValue(RequestStatus.LOADING);

        new CycleLogRepository().getCycleLogByDay(token, year, month, day, new IProcessStatusListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CycleLog log = (CycleLog) data;
                        calendarStatus.setValue(RequestStatus.DONE);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        calendarErrorCode.setValue(errorCode);
                        calendarStatus.setValue(RequestStatus.ERROR);
                    }
                }
        );
    }
}
