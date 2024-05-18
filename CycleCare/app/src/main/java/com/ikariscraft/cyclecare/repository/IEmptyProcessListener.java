package com.ikariscraft.cyclecare.repository;

public interface IEmptyProcessListener {

    void onSuccess();

    void onError(ProcessErrorCodes errorCode);
}
