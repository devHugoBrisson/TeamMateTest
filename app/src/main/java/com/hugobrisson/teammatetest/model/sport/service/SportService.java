package com.hugobrisson.teammatetest.model.sport.service;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hugobrisson.teammatetest.common.listener.ResponseListener;
import com.hugobrisson.teammatetest.common.manager.PrefManager;
import com.hugobrisson.teammatetest.common.model.ServiceCommon;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.hugobrisson.teammatetest.model.sport.Sport;
import com.hugobrisson.teammatetest.model.user.service.UserService;

import java.util.Calendar;
import java.util.List;

public class SportService extends ServiceCommon<Sport> {

    private static final String TAG = UserService.class.getSimpleName();
    private static final String FIREBASE_KEY = "sports";
    private static SportService mInstance;
    private static Context mContext;

    public SportService() {
        super(FIREBASE_KEY, Sport.class);
    }

    public static SportService getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new SportService();
        }
        return mInstance;
    }

    public void saveByDiff(long date, final ResponseListener<List<Sport>> responseListener) {
        if (date != 0) {
            date = PrefManager.getInstance(mContext).getLong(TMConstantKey.PREF_DIFF_DATE, 0);
        }
        mDataBaseReference.child(FIREBASE_KEY).child("created").startAt(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Sport sport = snapshot.getValue(Sport.class);
                        sport.save();
                    }
                }
                PrefManager.getInstance(mContext).edit().putLong(TMConstantKey.PREF_DIFF_DATE, Calendar.getInstance().getTimeInMillis()).apply();
                Log.d(TAG, "onSuccess()");
                responseListener.onSuccess(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onFailed() :" + databaseError.toException().getMessage());
                responseListener.onFailed(databaseError.toException());
            }
        });
    }

}
