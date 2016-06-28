package com.hugobrisson.teammate;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class TMApplication extends Application {

    /**
     * Called when we create application
     */
    @Override
    public void onCreate() {
        super.onCreate();

        /** Init database */
        FlowManager.init(this);
    }
}
