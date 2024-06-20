package com.ikariscraft.cyclecare.repository;

import android.util.Log;
import android.widget.Switch;

import com.ikariscraft.cyclecare.api.ApiClient;
import com.ikariscraft.cyclecare.api.Interfaces.IContentService;
import com.ikariscraft.cyclecare.api.requests.RateInformativeContentRequest;
import com.ikariscraft.cyclecare.api.requests.RegisterContentRequest;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.api.responses.RateContentJSONResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentRepository {

    public void RateContent(String toke, int contentId, RateInformativeContentRequest rating, IProcessStatusListener listener){

        IContentService contentService = ApiClient.getInstance().getContentService();

        contentService.rateInformativeContent(toke, contentId, rating).enqueue(new Callback<RateContentJSONResponse>() {
            @Override
            public void onResponse(Call<RateContentJSONResponse> call, Response<RateContentJSONResponse> response) {
                if(response.body() != null && response.isSuccessful()) {
                    RateContentJSONResponse contentResponse = new RateContentJSONResponse(
                            response.body().getError(),
                            response.body().getStatusCode(),
                            response.body().getDetails()
                    );
                    listener.onSuccess(contentResponse);
                }else{
                    listener.onError(ProcessErrorCodes.FATAL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<RateContentJSONResponse> call, Throwable t) {
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
