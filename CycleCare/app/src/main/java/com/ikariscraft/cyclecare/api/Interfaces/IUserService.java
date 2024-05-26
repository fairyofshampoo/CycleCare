package com.ikariscraft.cyclecare.api.interfaces;

import com.ikariscraft.cyclecare.api.requests.PasswordResetRequest;
import com.ikariscraft.cyclecare.api.requests.UserCredentialsBody;
import com.ikariscraft.cyclecare.api.requests.UserRegisterData;
import com.ikariscraft.cyclecare.api.responses.LoginJSONResponse;
import com.ikariscraft.cyclecare.api.responses.RegisterUserJSONResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserService {
        @POST("users/login")
        Call<LoginJSONResponse> login(@Body UserCredentialsBody credentials);

        @POST("users/registerUser")
        Call<RegisterUserJSONResponse> registerUser(@Body UserRegisterData user);

        @POST("users/request-reset/{email}")
        Call<Void> sendVerificationCode(@Path("email") String email);

        @POST("users/reset-password/{email}")
        Call<Void> resetPassword(@Path("email") String email, @Body PasswordResetRequest passwordResetRequest);

}
