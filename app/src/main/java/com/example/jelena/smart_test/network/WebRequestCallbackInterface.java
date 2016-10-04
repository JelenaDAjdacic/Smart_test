package com.example.jelena.smart_test.network;

public interface WebRequestCallbackInterface<T> {
    void webRequestSuccess(boolean success, T t);

    void webRequestError(String error);
}

