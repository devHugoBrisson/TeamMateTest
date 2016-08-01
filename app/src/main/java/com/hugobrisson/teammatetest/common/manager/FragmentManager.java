package com.hugobrisson.teammatetest.common.manager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.hugobrisson.teammatetest.R;

public class FragmentManager {

    public static String TAG = FragmentManager.class.getSimpleName();

    public static void initFragment(android.support.v4.app.FragmentManager fragmentManager, Fragment fragment){
        Log.v(TAG, "initFragment");
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public static void changeFragment(android.support.v4.app.FragmentManager fragmentManager, Fragment fragment){
        Log.v(TAG, "changeFragment");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public static void changeTransactionFragment(android.support.v4.app.FragmentManager fragmentManager, Fragment fragment, View view){
        Log.v(TAG, "changeFragment");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addSharedElement(view,"sharedImage");
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }
}
