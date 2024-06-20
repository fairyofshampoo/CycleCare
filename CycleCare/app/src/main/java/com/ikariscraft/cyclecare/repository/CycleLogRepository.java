package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.interfaces.ICycleService;
import com.ikariscraft.cyclecare.api.responses.CalendarJSONResponse;

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
}
