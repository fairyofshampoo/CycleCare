package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.RegisterContentRequest;
import com.ikariscraft.cyclecare.repository.ContentRepository;
import com.ikariscraft.cyclecare.repository.IEmptyProcessListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.Validations;

public class PublishInformativeContentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isTitleValid = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isDescriptionValid = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isImageValid = new MutableLiveData<>();

    private final MutableLiveData<RequestStatus> publishArticleRequestStatus = new MutableLiveData<>();

    private final MutableLiveData<ProcessErrorCodes> publishArticleErrorCode = new MutableLiveData<>();

    public PublishInformativeContentViewModel() {

    }

    public LiveData<Boolean> getIsTitleValid() {return isTitleValid;}

    public LiveData<Boolean> getIsDescriptionValid(){return isDescriptionValid;}

    public LiveData<Boolean> getIsImageValid(){return isImageValid;}

    public LiveData<RequestStatus> getPublishArticleRequestStatus(){return publishArticleRequestStatus;}

    public LiveData<ProcessErrorCodes> getPublishArticleErrorCode(){return publishArticleErrorCode;}

    public void ValidateTitle(String title) {
        boolean validation  = Validations.isValidTitle(title);

        isTitleValid.setValue(validation);
    }

    public void ValidateDescription(String description){
        boolean validation = Validations.isDescriptionValid(description);

        isDescriptionValid.setValue(validation);
    }

    public void publishArticle (String token, RegisterContentRequest article){
        publishArticleRequestStatus.setValue(RequestStatus.LOADING);

        new ContentRepository().publishNewArticle(
                token,
                article,
        new IEmptyProcessListener() {
            @Override
            public void onSuccess() {
                publishArticleRequestStatus.setValue(RequestStatus.DONE);
            }

            @Override
            public void onError(ProcessErrorCodes errorCode) {
                publishArticleErrorCode.setValue(errorCode);
                publishArticleRequestStatus.setValue(RequestStatus.ERROR);
            }
        }
        );

    }


}
