package com.hugobrisson.teammatetest.common;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class TMApplicationTest extends Application {

    /**
     * Called when we create application
     */
    @Override
    public void onCreate() {
        super.onCreate();

        /** Init database */
//        FlowManager.init(this);
    }
}
