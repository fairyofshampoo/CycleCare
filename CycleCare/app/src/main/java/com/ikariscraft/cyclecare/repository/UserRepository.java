package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.interfaces.IUserService;
import com.ikariscraft.cyclecare.api.requests.PasswordResetRequest;
import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.api.responses.RegisterUserJSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

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

    public void sendVerificationCode(String email, IEmptyProcessListener statusListener){
        IUserService userService = ApiClient.getInstance().getUserService();

        userService.sendVerificationCode(email).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    statusListener.onSuccess();
                } else {
                    switch (response.code()){
                        case 404:
                            statusListener.onError(ProcessErrorCodes.NOT_FOUND_ERROR);
                            break;
                        case 500:
                            statusListener.onError(ProcessErrorCodes.SERVICE_NOT_AVAILABLE_ERROR);
                            break;
                        default:
                            statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

}
