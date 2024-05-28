package com.ikariscraft.cyclecare.api;

import com.ikariscraft.cyclecare.api.Interfaces.IChartService;
import com.ikariscraft.cyclecare.api.Interfaces.IContentService;
import com.ikariscraft.cyclecare.api.Interfaces.IUserService;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


public class ApiClient {
    private static final ApiClient apiClient = new ApiClient();

    private final Retrofit retrofit;

    private IUserService userService;

    private IChartService chartService;

    private IContentService contentService;


    public static  ApiClient getInstance(){
        return apiClient;
    }

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.48.238:8085/apicyclecare/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    public IUserService getUserService(){
        if(userService == null){
            userService = retrofit.create(IUserService.class);
        }
        return userService;
    }

    public IChartService getChartService(){
        if(chartService == null){
            chartService = retrofit.create(IChartService.class);
        }
        return chartService;
    }

    public IContentService getContentService(){
        if(contentService == null) {
            contentService = retrofit.create(IContentService.class);
        }
        return contentService;
    }

}
