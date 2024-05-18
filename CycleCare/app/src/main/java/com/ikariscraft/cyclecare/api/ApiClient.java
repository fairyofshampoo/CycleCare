package com.ikariscraft.cyclecare.api;

import com.ikariscraft.cyclecare.api.Interfaces.IUserService;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClient {
    private static final ApiClient apiClient = new ApiClient();

    private final Retrofit retrofit;

    private IUserService userService;


    public static  ApiClient getInstance(){
        return apiClient;
    }

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8085/apicyclecare/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public IUserService getUserService(){
        if(userService == null){
            userService = retrofit.create(IUserService.class);
        }
        return userService;
    }

}
