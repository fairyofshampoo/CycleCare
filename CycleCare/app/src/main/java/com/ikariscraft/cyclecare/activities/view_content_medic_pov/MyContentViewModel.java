package com.ikariscraft.cyclecare.activities.view_content_medic_pov;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.InformativeContentJSONResponse;
import com.ikariscraft.cyclecare.repository.ContentRepository;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.util.List;

public class MyContentViewModel extends ViewModel {

    private  final MutableLiveData<List<InformativeContentJSONResponse>> myInformativeContent = new MutableLiveData<>();

    private final MutableLiveData<RequestStatus> myContentRequestStatus = new MutableLiveData<>();

    private final MutableLiveData<ProcessErrorCodes> myContentErrorCode = new MutableLiveData<>();

    public MyContentViewModel() {

    }

    public LiveData<RequestStatus> getMyContentRequestStatus() {return myContentRequestStatus;}

    public LiveData<ProcessErrorCodes> getMyContentetErrorCode(){return myContentErrorCode;}

    public LiveData<List<InformativeContentJSONResponse>> getMyInformativeContent() {return myInformativeContent;}

    public void getMyContent(String token) {
        myContentRequestStatus.setValue(RequestStatus.LOADING);

        new ContentRepository().getInformativeContentByUsername(
                token,
                new IProcessStatusListener() {
                    @Override
                    public void onSuccess(Object data) {
                        List<InformativeContentJSONResponse> content = (List<InformativeContentJSONResponse>) data;
                        myInformativeContent.setValue(content);
                        myContentRequestStatus.setValue(RequestStatus.DONE);
                    }

                    @Override
                    public void onError(ProcessErrorCodes errorCode) {
                        myInformativeContent.setValue(null);
                        myContentErrorCode.setValue(errorCode);
                        myContentRequestStatus.setValue(RequestStatus.ERROR);
                    }
                }
        );
    }

}
