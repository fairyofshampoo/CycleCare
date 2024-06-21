package com.ikariscraft.cyclecare.api.interfaces;

import com.ikariscraft.cyclecare.api.requests.NewCycleLogBody;
import com.ikariscraft.cyclecare.api.responses.CalendarJSONResponse;
import com.ikariscraft.cyclecare.api.responses.PredictionJSONResponse;
import com.ikariscraft.cyclecare.model.CycleLog;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICycleService {
    @GET("logs/user-cycle-logs/{year}/{month}")
    Call<CalendarJSONResponse> getCycleLogsByUser(@Path("year") int year, @Path("month") int month, @Header("token") String token);

    @POST("logs/register-cycle-log")
    Call<Void> createCycleLog(@Header("token") String token, @Body NewCycleLogBody cycleLog);

    @GET("logs/user-cycle-logs/{year}/{month}/{day}")
    Call<CycleLog> getCycleLogByDay(@Path("year") int year, @Path("month") int month, @Path("day") int day, @Header("token") String token);

    @GET("logs/prediction-cycle")
    Call<PredictionJSONResponse> getPredictionCycle(@Header("token") String token);

    @POST("logs/update-cycle-log/{cycleLogId}")
    Call<Void> updateCycleLog(@Path("cycleLogId") int cycleLogId, @Header("token") String token, @Body NewCycleLogBody cycleLog);
}
