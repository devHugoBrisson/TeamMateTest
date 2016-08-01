package com.hugobrisson.teammatetest.model.sport.dao;

import android.content.Context;

import com.hugobrisson.teammatetest.common.model.DaoCommon;
import com.hugobrisson.teammatetest.model.sport.Sport;

public class SportDao extends DaoCommon<Sport> {

    private static Context mContext;
    private static SportDao mInstance;

    public SportDao(Class<Sport> sClass) {
        super(sClass);
    }

    public static SportDao getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new SportDao(Sport.class);
        }
        return mInstance;
    }
}
