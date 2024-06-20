package com.ikariscraft.cyclecare.activities.view_content_user_pov;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.repository.ContentRepository;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.util.List;

public class InformativeContentViewModel extends ViewModel {

    public  InformativeContentViewModel(){

    }

    private final MutableLiveData<RequestStatus> informativeContentStatus = new MutableLiveData<>();

    private final MutableLiveData<List<InformativeContentJSONResponse>>  informativeContentList = new MutableLiveData<>();

    private final MutableLiveData<ProcessErrorCodes> informativeContentErrorCode = new MutableLiveData<>();

    public LiveData<List<InformativeContentJSONResponse>> getInformativeContentList() {return informativeContentList;}

    public LiveData<RequestStatus> getInformativeContentRequestStatus() {return informativeContentStatus;}

    public LiveData<ProcessErrorCodes> getInformativeContentErrorCode() {return informativeContentErrorCode;}

    public void GetInformativeContent(String token){
        informativeContentStatus.setValue(RequestStatus.LOADING);

        new ContentRepository().getInformativeContent(
                token,
                new IProcessStatusListener() {
                    @Override
                    public void onSuccess(Object data) {
                        List<InformativeContentJSONResponse> content = (List<InformativeContentJSONResponse>) data;
                        informativeContentList.setValue(content);
                        informativeContentStatus.setValue(RequestStatus.DONE);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        informativeContentList.setValue(null);
                        informativeContentErrorCode.setValue(errorCode);
                        informativeContentStatus.setValue(RequestStatus.ERROR);
                    }
                }
        );

    }


}
