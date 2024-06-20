package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.interfaces.ICycleService;
import com.ikariscraft.cyclecare.api.responses.CalendarJSONResponse;
import com.ikariscraft.cyclecare.model.CycleLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CycleLogRepository {

    public void getCycleLogs(String token, int year, int month, IProcessStatusListener listener){
        ICycleService cycleService = ApiClient.getInstance().getCycleService();

        cycleService.getCycleLogsByUser(year, month, token).enqueue(new Callback<CalendarJSONResponse>() {
            @Override
            public void onResponse(Call<CalendarJSONResponse> call, Response<CalendarJSONResponse> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        CalendarJSONResponse calendarResponse = new CalendarJSONResponse(
                                response.body().getCycleLogs()
                        );
                        listener.onSuccess(calendarResponse);
                    } else {
                        listener.onError(ProcessErrorCodes.FATAL_ERROR);
                    }
                } else {
                    switch (response.code()) {
                        case 400:
                            listener.onError(ProcessErrorCodes.REQUEST_FORMAT_ERROR);
                            break;
                        case 500:
                            listener.onError(ProcessErrorCodes.SERVICE_NOT_AVAILABLE_ERROR);
                            break;
                        default:
                            listener.onError(ProcessErrorCodes.FATAL_ERROR);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<CalendarJSONResponse> call, Throwable t) {
                listener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

    public void getCycleLogByDay(String token, int year, int month, int day, IProcessStatusListener listener){
        ICycleService cycleService = ApiClient.getInstance().getCycleService();

        cycleService.getCycleLogByDay(year, month, day, token).enqueue(new Callback<CycleLog>() {

            @Override
            public void onResponse(Call<CycleLog> call, Response<CycleLog> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        CycleLog cycleLog = new CycleLog(
                                response.body().getCycleLogId(),
                                response.body().getCreationDate(),
                                response.body().getBirthControl(),
                                response.body().getMedications(),
                                response.body().getSymptoms(),
                                response.body().getMoods(),
                                response.body().getMenstrualFlowId(),
                                response.body().getNote(),
                                response.body().getPills(),
                                response.body().getSleepHours(),
                                response.body().getUsername(),
                                response.body().getVaginalFlowId()
                        );
                        listener.onSuccess(response.body());
                    } else {
                        listener.onError(ProcessErrorCodes.FATAL_ERROR);
                    }
                } else {
                    switch (response.code()) {
                        case 404:
                            listener.onError(ProcessErrorCodes.NOT_FOUND_ERROR);
                            break;
                        case 500:
                            listener.onError(ProcessErrorCodes.SERVICE_NOT_AVAILABLE_ERROR);
                            break;
                        default:
                            listener.onError(ProcessErrorCodes.FATAL_ERROR);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<CycleLog> call, Throwable t) {
                listener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }
}
