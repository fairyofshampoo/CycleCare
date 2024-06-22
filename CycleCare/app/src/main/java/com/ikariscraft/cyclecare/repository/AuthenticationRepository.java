package com.ikariscraft.cyclecare.repository;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.interfaces.IUserService;
import com.ikariscraft.cyclecare.api.requests.UserCredentialsBody;
import com.ikariscraft.cyclecare.api.responses.LoginJSONResponse;
import com.ikariscraft.cyclecare.model.Person;
import com.ikariscraft.cyclecare.utilities.SessionSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {

    public void login(UserCredentialsBody credentials, IEmptyProcessListener statusListener) {

            IUserService userService = ApiClient.getInstance().getUserService();
            userService.login(credentials).enqueue(new Callback<LoginJSONResponse>() {
                @Override
                public void onResponse(Call<LoginJSONResponse> call, Response<LoginJSONResponse> response) {
                    if (response.isSuccessful()){
                        LoginJSONResponse body = response.body();

                        if(body != null){
                            Person person = new Person(
                                    body.getEmail(),
                                    body.getName(),
                                    body.getFirstLastName(),
                                    body.getSecondLastName(),
                                    body.getRole()
                            );
                            SessionSingleton sessionSingleton = SessionSingleton.getInstance();
                            sessionSingleton.setToken(body.getToken());
                            sessionSingleton.setPerson(person);
                            statusListener.onSuccess();
                        } else {
                            statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
                        }
                    } else {
                        switch (response.code()){
                            case 400:
                                statusListener.onError(ProcessErrorCodes.REQUEST_FORMAT_ERROR);
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
                public void onFailure(Call<LoginJSONResponse> call, Throwable t) {
                    statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
                }
            });
    }
}
