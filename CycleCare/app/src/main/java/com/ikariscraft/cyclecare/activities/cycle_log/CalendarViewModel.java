package com.ikariscraft.cyclecare.activities.cycle_log;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.CalendarJSONResponse;
import com.ikariscraft.cyclecare.api.responses.PredictionJSONResponse;
import com.ikariscraft.cyclecare.model.CycleLog;
import com.ikariscraft.cyclecare.repository.CycleLogRepository;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.util.List;

public class CalendarViewModel extends ViewModel {
    public CalendarViewModel() {
    }

    private final MutableLiveData<RequestStatus> calendarStatus = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> cycleLogByDayStatus = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> predictedCycleStatus = new MutableLiveData<>();
    private final MutableLiveData<PredictionJSONResponse> predictionResponse = new MutableLiveData<>();
    private final MutableLiveData<CalendarJSONResponse> calendarResponse = new MutableLiveData<>();
    private final MutableLiveData<CycleLog> cycleLog = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> predictionErrorCode = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> cycleLogErrorCode = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> calendarErrorCode = new MutableLiveData<>();

    public LiveData<CalendarJSONResponse> getCalendarResponse() {
        return calendarResponse;
    }

    public LiveData<PredictionJSONResponse> getPredictionResponse() {
        return predictionResponse;
    }
    public LiveData<CycleLog> getCycleLog() {
        return cycleLog;
    }
    public LiveData<RequestStatus> getCalendarRequestStatus() {
        return calendarStatus;
    }

    public LiveData<RequestStatus> getPredictedCycleStatus() {
        return predictedCycleStatus;
    }

    public LiveData<RequestStatus> getGetCycleLogByDayStatus() {
        return cycleLogByDayStatus;
    }
    public LiveData<ProcessErrorCodes> getCalendarErrorCode() {
        return calendarErrorCode;
    }
    public LiveData<ProcessErrorCodes> getCycleLogErrorCode() {
        return cycleLogErrorCode;
    }
    public LiveData<ProcessErrorCodes> getPredictionErrorCode() {
        return predictionErrorCode;
    }
    public void getCycleLogs(String token, int year, int month) {
        calendarStatus.setValue(RequestStatus.LOADING);

        new CycleLogRepository().getCycleLogs(token, year, month, new IProcessStatusListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CalendarJSONResponse calendarJSONResponse = (CalendarJSONResponse) data;
                        calendarResponse.setValue(calendarJSONResponse);
                        calendarStatus.setValue(RequestStatus.DONE);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        calendarResponse.setValue(null);
                        calendarErrorCode.setValue(errorCode);
                        calendarStatus.setValue(RequestStatus.ERROR);
                    }
                }
        );
    }

    public void getCycleLogByDay(String token, int year, int month, int day) {
        cycleLogByDayStatus.setValue(RequestStatus.LOADING);
        new CycleLogRepository().getCycleLogByDay(token, year, month, day, new IProcessStatusListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CycleLog log = (CycleLog) data;
                        cycleLogByDayStatus.setValue(RequestStatus.DONE);
                        cycleLog.setValue(log);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        cycleLogErrorCode.setValue(errorCode);
                        cycleLogByDayStatus.setValue(RequestStatus.ERROR);
                    }
                }
        );
    }

    public void getCyclePrediction(String token){
        predictedCycleStatus.setValue(RequestStatus.LOADING);
        new CycleLogRepository().getCyclePrediction(token, new IProcessStatusListener() {
            @Override
            public void onSuccess(Object data) {
                PredictionJSONResponse predictionJSONResponse = (PredictionJSONResponse) data;
                predictionResponse.setValue(predictionJSONResponse);
                predictedCycleStatus.setValue(RequestStatus.DONE);
            }

            @Override
            public void onError(ProcessErrorCodes errorCode) {
                predictionErrorCode.setValue(errorCode);
                predictedCycleStatus.setValue(RequestStatus.ERROR);
            }
        });

    }
}
