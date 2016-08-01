package com.hugobrisson.teammatetest.common.listener;

import java.util.List;

public interface ServiceInterface<T> {

    void create(T t, String id, ResponseListener<T> responseListener);

    String getKey();

    void get(ResponseListener<List<T>> responseListener);

    void getById(String id, ResponseListener<T> responseListener);

    void getByValue(String value, String key, ResponseListener<List<T>> responseListener);

    void update(T t, String id, ResponseListener<T> responseListener);

    void delete(String id, ResponseListener<T> responseListener);

}
