package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.Interfaces.IReminderService;
import com.ikariscraft.cyclecare.api.requests.CreateReminderRequest;
import com.ikariscraft.cyclecare.api.requests.UpdateReminderRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderRepository {

    public void RegisterReminder(String token, CreateReminderRequest reminderRequest, IEmptyProcessListener listener){
        IReminderService reminderService = ApiClient.getInstance().getReminderService();
        reminderService.createReminder(token, reminderRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    listener.onSuccess();
                }else {
                    switch (response.code()){
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
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

    public void UpdateReminder(String token, int reminderId, UpdateReminderRequest updateReminderRequest, IEmptyProcessListener listener){
        IReminderService reminderService = ApiClient.getInstance().getReminderService();
        reminderService.updateReminder(token, reminderId, updateReminderRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    listener.onSuccess();
                }else{
                    switch (response.code()){
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
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }


}
