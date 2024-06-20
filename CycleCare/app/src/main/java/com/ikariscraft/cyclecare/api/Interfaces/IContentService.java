package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.api.requests.RateInformativeContentRequest;
import com.ikariscraft.cyclecare.api.requests.RegisterContentRequest;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.api.responses.RateContentJSONResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IContentService {

    @POST("content/create-rating/{id}")
    Call<RateContentJSONResponse> rateInformativeContent(
            @Header("toke") String token,
            @Path("id") int contentId,
            @Body RateInformativeContentRequest rating
    );

    @POST("content/publish-article")
    Call<RateContentJSONResponse> publishArticle(
            @Header("token") String token,
            @Body RegisterContentRequest article
    );

    @GET("content/get-articles-by-medic")
    Call<List<InformativeContentJSONResponse>> getMyContent(@Header("token") String token);

    @GET("content/obtain-informative-content")
    Call<List<InformativeContentJSONResponse>> getInformativeContent(@Header("token") String token);

}
