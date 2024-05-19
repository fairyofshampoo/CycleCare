package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.api.requests.UserCredentialsBody;
import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.api.responses.LoginJSONResponse;
import com.ikariscraft.cyclecare.api.responses.RegisterUserJSONResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserService {
        @POST("users/login")
        Call<LoginJSONResponse> login(@Body UserCredentialsBody credentials);

        @POST("users/registerUser")
        Call<RegisterUserJSONResponse> registerUser(@Body UserRegisterData user);

}
