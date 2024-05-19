package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.Interfaces.IUserService;
import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.api.responses.RegisterUserJSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserRepository {

    public void registerUser(UserRegisterData data, IEmptyProcessListener statusListener){
        IUserService userService = ApiClient.getInstance().getUserService();

        userService.registerUser(data).enqueue(new Callback<RegisterUserJSONResponse>() {
            @Override
            public void onResponse(Call<RegisterUserJSONResponse> call, Response<RegisterUserJSONResponse> response) {
                if(response.isSuccessful()){
                    statusListener.onSuccess();
                } else {
                    statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
                }

            }

            @Override
            public void onFailure(Call<RegisterUserJSONResponse> call, Throwable t) {
                statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

}
