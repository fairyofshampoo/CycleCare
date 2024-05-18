package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.api.requests.UserCredentialsBody;
import com.ikariscraft.cyclecare.api.responses.LoginJSONResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserService {
        @POST("users/login")
        Call<LoginJSONResponse> login(@Body UserCredentialsBody credentials);

}
