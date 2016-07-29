package com.hugobrisson.teammatetest.model.user.service;

import com.hugobrisson.teammatetest.common.model.ServiceCommon;
import com.hugobrisson.teammatetest.model.user.User;

public class UserService extends ServiceCommon<User> {

    private static final String TAG = UserService.class.getSimpleName();
    private static final String FIREBASE_KEY = "users";
    private static UserService mInstance;

    public UserService() {
        super(FIREBASE_KEY, User.class);
    }

    public static UserService getInstance() {
        if (mInstance == null) {
            mInstance = new UserService();
        }
        return mInstance;
    }
}
