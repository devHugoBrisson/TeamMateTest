package com.hugobrisson.teammatetest.common.listener;

public interface ResponseListener<T> {
    void onSuccess(T t);
    void onFailed(Exception e);
}
