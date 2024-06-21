package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.model.SleepHoursInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IChartService {
    @GET("chart/obtain-sleep-hours")
    Call<List<SleepHoursInformation>> getHoursChart(@Header("token") String token);
}
