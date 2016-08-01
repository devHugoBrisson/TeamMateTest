package com.hugobrisson.teammatetest.common.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hugobrisson.teammatetest.common.listener.ServiceInterface;
import com.hugobrisson.teammatetest.common.listener.ResponseListener;
import com.hugobrisson.teammatetest.common.manager.PrefManager;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceCommon<T extends BaseModel> implements ServiceInterface<T> {

    private static final String TAG = ServiceCommon.class.getSimpleName();

    protected DatabaseReference mDataBaseReference;
    private String mTableKey;
    private Class<T> mClass;
    protected Context mContext;

    public ServiceCommon(String sTableKey, Class<T> sClass) {
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        mTableKey = sTableKey;
        mClass = sClass;
    }

    @Override
    public void create(final T t, final String id, final ResponseListener<T> responseListener) {
        Log.d(TAG, "create() :" + id);
        mDataBaseReference.child(mTableKey).child(id).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onSuccess()");
                    responseListener.onSuccess(t);
                    t.save();
                } else {
                    Log.d(TAG, "onFailed() :" + task.getException().getMessage());
                    responseListener.onFailed(task.getException());
                }
            }
        });
    }

    @Override
    public String getKey() {
        Log.d(TAG, "getKey()");
        return mDataBaseReference.push().getKey();
    }

    @Override
    public void get(final ResponseListener<List<T>> responseListener) {
        Log.d(TAG, "get()");
        mDataBaseReference.child(mTableKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> tList = new ArrayList<>();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        T t = snapshot.getValue(mClass);
                        tList.add(t);
                    }
                }
                Log.d(TAG, "onSuccess()");
                responseListener.onSuccess(tList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onFailed() :" + databaseError.toException().getMessage());
                responseListener.onFailed(databaseError.toException());
            }
        });
    }

    @Override
    public void getById(String id, final ResponseListener<T> responseListener) {
        Log.d(TAG, "get(): " + id);
        mDataBaseReference.child(mTableKey).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onSuccess()");
                responseListener.onSuccess(dataSnapshot.getValue(mClass));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onFailed() :" + databaseError.toException().getMessage());
                responseListener.onFailed(databaseError.toException());
            }
        });
    }

    @Override
    public void getByValue(String value, String key, final ResponseListener<List<T>> responseListener) {
        Query query = mDataBaseReference.child(mTableKey).orderByChild(key).equalTo(value);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> tList = new ArrayList<>();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        T t = snapshot.getValue(mClass);
                        tList.add(t);
                    }
                } else {
                    T t = dataSnapshot.getValue(mClass);
                    tList.add(t);
                }
                Log.d(TAG, "onSuccess()");
                responseListener.onSuccess(tList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                responseListener.onFailed(databaseError.toException());
            }
        });
    }

    @Override
    public void update(T t, String id, final ResponseListener<T> responseListener) {
        Log.d(TAG, "update(): " + id);
        mDataBaseReference.child(mTableKey).child(id).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onSuccess()");
                    responseListener.onSuccess(null);
                } else {
                    Log.d(TAG, "onFailed() :" + task.getException().getMessage());
                    responseListener.onFailed(task.getException());
                }
            }
        });
    }

    @Override
    public void delete(String id, final ResponseListener<T> responseListener) {
        Log.d(TAG, "delete(): " + id);
        mDataBaseReference.child(mTableKey).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onSuccess()");
                    responseListener.onSuccess(null);
                } else {
                    Log.d(TAG, "onFailed() :" + task.getException().getMessage());
                    responseListener.onFailed(task.getException());
                }
            }
        });
    }
}

