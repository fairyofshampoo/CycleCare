package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.api.responses.RateContentJSONResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IContentService {

    @POST("/content/create-rating/{id}")
    Call<RateContentJSONResponse> rateInformativeContent(
            @Header("toke") String token,
            @Path("id") int contentId,
            @Body int rating
    );

}
