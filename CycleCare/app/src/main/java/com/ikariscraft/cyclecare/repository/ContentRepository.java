package com.ikariscraft.cyclecare.repository;

import android.util.Log;
import android.widget.Switch;

import com.ikariscraft.cyclecare.activities.view_content_medic_pov.EditInformativeContentActivity;
import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.interfaces.IContentService;
import com.ikariscraft.cyclecare.api.requests.EditArticleRequest;
import com.ikariscraft.cyclecare.api.requests.RateInformativeContentRequest;
import com.ikariscraft.cyclecare.api.requests.RegisterContentRequest;
import com.ikariscraft.cyclecare.api.responses.GetRateJSONResponse;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.api.responses.RateContentJSONResponse;
import com.ikariscraft.cyclecare.api.responses.RatingAverageJSONResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentRepository {

    public void RateContent(String token, int contentId, RateInformativeContentRequest rating, IEmptyProcessListener listener){

        IContentService contentService = ApiClient.getInstance().getContentService();
        Log.e("Token 2", "Token en repository " + token);
        contentService.rateInformativeContent(token, contentId, rating).enqueue(new Callback<RateContentJSONResponse>() {
            @Override
            public void onResponse(Call<RateContentJSONResponse> call, Response<RateContentJSONResponse> response) {
                if(response.isSuccessful()) {
                    listener.onSuccess();
                }else{
                    switch (response.code()){
                        case 404:
                            listener.onError(ProcessErrorCodes.NOT_FOUND_ERROR);
                            break;
                        case 500:
                            listener.onError(ProcessErrorCodes.SERVICE_NOT_AVAILABLE_ERROR);
                            break;
                        default:
                            listener.onError(ProcessErrorCodes.FATAL_ERROR);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<RateContentJSONResponse> call, Throwable t) {
                listener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });

    }

    public void UpdateRateContent(String token, int contentId, RateInformativeContentRequest rating, IEmptyProcessListener listener){

        IContentService contentService = ApiClient.getInstance().getContentService();
        Log.e("Token 2", "Token en edición de repository " + token);
        contentService.updateRateInformativeContent(token, contentId, rating).enqueue(new Callback<RateContentJSONResponse>() {
            @Override
            public void onResponse(Call<RateContentJSONResponse> call, Response<RateContentJSONResponse> response) {
                if(response.isSuccessful()) {
                    listener.onSuccess();
                }else{
                    try {
                        Log.e("API_ERROR", "Response error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listener.onError(ProcessErrorCodes.FATAL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RateContentJSONResponse> call, Throwable t) {
                listener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });

    }

    public void getAverageRating(String token, int contentId, IProcessStatusListener statusListener){
        IContentService contentService = ApiClient.getInstance().getContentService();
        contentService.getAverageRating(token, contentId).enqueue(new Callback<RatingAverageJSONResponse>() {
            @Override
            public void onResponse(Call<RatingAverageJSONResponse> call, Response<RatingAverageJSONResponse> response) {
                if(response.body() != null && response.isSuccessful()){
                    statusListener.onSuccess(response.body());
                } else {
                    switch (response.code()){
                        case 400:
                            statusListener.onError(ProcessErrorCodes.REQUEST_FORMAT_ERROR);
                            break;
                        case 404:
                            statusListener.onError(ProcessErrorCodes.NOT_FOUND_ERROR);
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
            public void onFailure(Call<RatingAverageJSONResponse> call, Throwable t) {
                statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

    public void GetRateContent(String token, int contentId, IProcessStatusListener listener){
        IContentService contentService = ApiClient.getInstance().getContentService();
        contentService.getRate(token, contentId).enqueue(new Callback<GetRateJSONResponse>() {
            @Override
            public void onResponse(Call<GetRateJSONResponse> call, Response<GetRateJSONResponse> response) {
                if(response.isSuccessful()) {
                    listener.onSuccess(response.body().getValue());
                }else{
                    try {
                        Log.e("API_ERROR", "Response error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listener.onError(ProcessErrorCodes.FATAL_ERROR);
                }
            }
            
            @Override
            public void onFailure(Call<GetRateJSONResponse> call, Throwable t) {
                listener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });

    }

    public void publishNewArticle(String toke, RegisterContentRequest article, IEmptyProcessListener statusListener) {
        IContentService contentService = ApiClient.getInstance().getContentService();
        contentService.publishArticle(toke, article).enqueue(new Callback<RateContentJSONResponse>() {
            @Override
            public void onResponse(Call<RateContentJSONResponse> call, Response<RateContentJSONResponse> response) {
                if(response.isSuccessful()){
                    statusListener.onSuccess();
                }else{
                    switch (response.code()){
                        case 404:
                            statusListener.onError(ProcessErrorCodes.NOT_FOUND_ERROR);
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
            public void onFailure(Call<RateContentJSONResponse> call, Throwable t) {
                statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

    public void EditExistingArticle(String toke, EditArticleRequest article, IEmptyProcessListener statusListener) {
        IContentService contentService = ApiClient.getInstance().getContentService();
        contentService.updateArticle(toke, article).enqueue(new Callback<RateContentJSONResponse>() {
            @Override
            public void onResponse(Call<RateContentJSONResponse> call, Response<RateContentJSONResponse> response) {
                if(response.isSuccessful()){
                    statusListener.onSuccess();
                }else{
                    switch (response.code()){
                        case 404:
                            statusListener.onError(ProcessErrorCodes.NOT_FOUND_ERROR);
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
            public void onFailure(Call<RateContentJSONResponse> call, Throwable t) {
                statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

    public void getInformativeContent(String token, IProcessStatusListener statusListener){
        IContentService contentService = ApiClient.getInstance().getContentService();

        contentService.getInformativeContent(token).enqueue(new Callback<List<InformativeContentJSONResponse>>() {
            @Override
            public void onResponse(Call<List<InformativeContentJSONResponse>> call, Response<List<InformativeContentJSONResponse>> response) {
                if(response.body() != null && response.isSuccessful()){
                    statusListener.onSuccess(response.body());
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
            public void onFailure(Call<List<InformativeContentJSONResponse>> call, Throwable t) {
                statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });

    }

    public void getInformativeContentByUsername(String token, IProcessStatusListener statusListener){
        IContentService contentService = ApiClient.getInstance().getContentService();
        contentService.getMyContent(token).enqueue(new Callback<List<InformativeContentJSONResponse>>() {
            @Override
            public void onResponse(Call<List<InformativeContentJSONResponse>> call, Response<List<InformativeContentJSONResponse>> response) {
                if(response.body() != null && response.isSuccessful()){
                    statusListener.onSuccess(response.body());
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
            public void onFailure(Call<List<InformativeContentJSONResponse>> call, Throwable t) {
                statusListener.onError(ProcessErrorCodes.FATAL_ERROR);
            }
        });
    }

}
