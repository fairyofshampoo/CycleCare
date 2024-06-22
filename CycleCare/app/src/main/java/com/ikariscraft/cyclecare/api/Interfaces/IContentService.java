package com.ikariscraft.cyclecare.api.Interfaces;

import com.ikariscraft.cyclecare.api.requests.EditArticleRequest;
import com.ikariscraft.cyclecare.api.requests.RateInformativeContentRequest;
import com.ikariscraft.cyclecare.api.requests.RegisterContentRequest;
import com.ikariscraft.cyclecare.api.responses.GetRateJSONResponse;
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
            @Header("token") String token,
            @Path("id") int contentId,
            @Body RateInformativeContentRequest rating
    );

    @POST("content/update-rate/{id}")
    Call<RateContentJSONResponse> updateRateInformativeContent(
            @Header("token") String token,
            @Path("id") int contentId,
            @Body RateInformativeContentRequest rating
    );

    @GET("content/get-rate/{id}")
    Call<GetRateJSONResponse> getRate(
            @Header("token") String token,
            @Path("id") int contentId
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

    @POST("content/update-informative-content")
    Call<RateContentJSONResponse> updateArticle (
            @Header("token") String token,
            @Body EditArticleRequest newArticle
            );

}
