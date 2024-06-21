package com.ikariscraft.cyclecare.activities.view_sleep_chart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.model.SleepHoursInformation;
import com.ikariscraft.cyclecare.repository.ChartRepository;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.util.List;

public class ViewSleepChartViewModel extends ViewModel {

    private final MutableLiveData<RequestStatus> sleepChartRequestStatus = new MutableLiveData<>();
    private final MutableLiveData<List<SleepHoursInformation>> sleepHoursLiveData = new MutableLiveData<>();

    public ViewSleepChartViewModel(){

    }

    public LiveData<RequestStatus> getSleepChartRequestStatus() {return sleepChartRequestStatus;}

    public LiveData<List<SleepHoursInformation>> getSleepHours() {return sleepHoursLiveData;}

    public void getSleepHoursChart(String token){
        sleepChartRequestStatus.setValue(RequestStatus.LOADING);

       new ChartRepository().GetSleepStadistics(
               token,
               new IProcessStatusListener() {
                   @Override
                   public void onSuccess(Object data) {
                       List<SleepHoursInformation> sleepHours = (List<SleepHoursInformation>) data;
                       sleepHoursLiveData.setValue(sleepHours);
                       sleepChartRequestStatus.setValue(RequestStatus.DONE);
                   }

                   @Override
                   public void onError(ProcessErrorCodes errorCode) {
                       sleepHoursLiveData.setValue(null);
                       sleepChartRequestStatus.setValue(RequestStatus.ERROR);
                   }
               }
       );
    }

}
