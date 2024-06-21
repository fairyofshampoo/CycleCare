package com.ikariscraft.cyclecare.repository;

public interface IProcessStatusListener<T> {
    void onSuccess(T data);
    void onError(ProcessErrorCodes errorCode);
}
