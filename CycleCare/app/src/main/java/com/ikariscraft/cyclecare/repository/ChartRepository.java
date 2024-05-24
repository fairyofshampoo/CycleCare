package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.Interfaces.IChartService;
import com.ikariscraft.cyclecare.api.responses.SleepChartJSONResponse;
import com.ikariscraft.cyclecare.model.SleepHoursInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartRepository {

    public void ObtainSleppStadistics(String token, IProcessStatusListener listener) {

        IChartService chartService = ApiClient.getInstance().getChartService();
        chartService.getHoursChart(token).enqueue(new Callback<List<SleepHoursInformation>>() {
            @Override
            public void onResponse(Call<List<SleepHoursInformation>> call, Response<List<SleepHoursInformation>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError(ProcessErrorCodes.FATAL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<SleepHoursInformation>> call, Throwable t) {

            }
        });

    }
}
