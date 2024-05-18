package com.ikariscraft.cyclecare.repository;

import android.util.Log;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.Interfaces.IUserService;
import com.ikariscraft.cyclecare.api.requests.UserCredentialsBody;
import com.ikariscraft.cyclecare.api.responses.LoginJSONResponse;
import com.ikariscraft.cyclecare.model.Person;
import com.ikariscraft.cyclecare.utilities.Session;

import okhttp3.ResponseBody;
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

                            Session session = Session.getInstance();
                            session.setToken(body.getToken());
                            session.setPerson(person);
                            Log.e("Successful", "Exitoso");
                            statusListener.onSuccess();
                        } else {
                            statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
                            Log.e("Error primer else", "no exitoso");
                        }
                    } else {
                        statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
                        Log.e("Error segundo else", "no exitoso");
                    }
                }

                @Override
                public void onFailure(Call<LoginJSONResponse> call, Throwable t) {
                    statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
                    Log.e("Error on Failure", "no exitoso");
                }
            });
    }
}
