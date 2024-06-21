package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.api.requests.CreateReminderRequest;
import com.ikariscraft.cyclecare.api.requests.UpdateReminderRequest;
import com.ikariscraft.cyclecare.api.responses.UserRemindersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IReminderService {

    @POST("reminders/create-reminder")
    Call<Void> createReminder(
            @Header("token") String token,
            @Body CreateReminderRequest reminderRequest
            );

    @POST("reminders/update-reminder/{id}")
    Call<Void> updateReminder (
            @Header("token") String token,
            @Path("id") int reminderId,
            @Body UpdateReminderRequest updateReminderRequest
            );

    @GET("reminders/user-reminders")
    Call<UserRemindersResponse> getUserReminders(
            @Header("token") String token);
}
