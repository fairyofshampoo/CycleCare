package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.requests.EditArticleRequest;
import com.ikariscraft.cyclecare.repository.ContentRepository;
import com.ikariscraft.cyclecare.repository.IEmptyProcessListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;
import com.ikariscraft.cyclecare.utilities.Validations;

public class EditInformativeContentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isTitleValid = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isDescriptionValid = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isImageValid = new MutableLiveData<>();

    private final MutableLiveData<RequestStatus> publishArticleRequestStatus = new MutableLiveData<>();

    private final MutableLiveData<ProcessErrorCodes> publishArticleErrorCode = new MutableLiveData<>();

    public EditInformativeContentViewModel() {

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

    public void EditArtice(String token, EditArticleRequest article){
        publishArticleRequestStatus.setValue(RequestStatus.LOADING);
        Log.e("Cargando publishArticlee", "se ha llamado el método publishArticle");

        new ContentRepository().editExistingArticle(
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
