package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.api.responses.SleepChartJSONResponse;
import com.ikariscraft.cyclecare.model.SleepHoursInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IChartService {
    @GET("chart/obtain-sleep-hours")
    Call<List<SleepHoursInformation>> getHoursChart(@Header("token") String token);
}
