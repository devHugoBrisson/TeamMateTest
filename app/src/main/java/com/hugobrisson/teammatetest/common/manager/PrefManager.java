package com.hugobrisson.teammatetest.common.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

/**
 * Preference manager.
 */
public class PrefManager{

    public static final String PREF = "SharedPref";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    private static SharedPreferences mInstance = null;

    public static SharedPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        }
        return mInstance;
    }

    public Double getLatitude() {
        return Double.longBitsToDouble(mInstance.getLong(LATITUDE, 0));
    }

    public Double getLongitude() {
        return Double.longBitsToDouble(mInstance.getLong(LONGITUDE, 0));
    }

    public void setLatitude(double val) {
        mInstance.edit().putLong(LATITUDE, Double.doubleToLongBits(val)).apply();
    }

    public void setLongitude(double val) {
        mInstance.edit().putLong(LONGITUDE, Double.doubleToLongBits(val)).apply();
    }
}
