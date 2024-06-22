package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.RatingAverageJSONResponse;
import com.ikariscraft.cyclecare.repository.ContentRepository;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

public class ViewContentByMedicViewModel  extends ViewModel {
    private final MutableLiveData<RatingAverageJSONResponse> averageRatingResponse = new MutableLiveData<>();
    private final MutableLiveData<ProcessErrorCodes> averageRatingErrorCode = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> averageRatingRequestStatus = new MutableLiveData<>();

    public ViewContentByMedicViewModel() {

    }

    public LiveData<RatingAverageJSONResponse> getAverageRatingResponse() {
        return averageRatingResponse;
    }

    public LiveData<ProcessErrorCodes> getAverageRatingErrorCode() {
        return averageRatingErrorCode;
    }

    public LiveData<RequestStatus> getAverageRatingRequestStatus() {
        return averageRatingRequestStatus;
    }

    public void getAverageRating(String token, int contentId) {
        averageRatingRequestStatus.setValue(RequestStatus.LOADING);
        new ContentRepository().getAverageRating(token, contentId, new IProcessStatusListener() {
            @Override
            public void onSuccess(Object response) {
                averageRatingResponse.setValue((RatingAverageJSONResponse) response);
                averageRatingRequestStatus.setValue(RequestStatus.DONE);
            }

            @Override
            public void onError(ProcessErrorCodes errorCode) {
                averageRatingErrorCode.setValue(errorCode);
                averageRatingRequestStatus.setValue(RequestStatus.ERROR);
            }
        });
    }
}
