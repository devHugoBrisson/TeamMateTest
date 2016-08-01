package com.hugobrisson.teammatetest.module.enrollment.viewController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.FragmentTutorialController;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;

import java.util.Calendar;

public class RegisterBirthdayFragment extends FragmentTutorialController {

    private DatePicker mDatePicker;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int position = intent.getExtras().getInt(TMConstantKey.EXTRA_NEXT);
                if (position == 2) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
                    mCurrentUser = getCurrentUserTutorial();
                    mCurrentUser.setBirthDate(calendar.getTime().getTime());
                    setCurrentUserTutorial(mCurrentUser);
                    mTutorialListener.onNext();
                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tLayout = inflater.inflate(R.layout.fragment_register_birthday, container, false);
        mDatePicker = (DatePicker) tLayout.findViewById(R.id.dp_global);
        return tLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 10);
        mDatePicker.setMaxDate(calendar.getTimeInMillis());
        calendar.set(Calendar.YEAR, 1940);
        mDatePicker.setMinDate(calendar.getTimeInMillis());
        mCurrentUser = getCurrentUserTutorial();
        if (mCurrentUser.getBirthDate() != 0) {
            Calendar myDate = Calendar.getInstance();
            myDate.setTimeInMillis(mCurrentUser.getBirthDate());
            mDatePicker.init(myDate.get(Calendar.YEAR), myDate.get(Calendar.MONTH), myDate.get(Calendar.DAY_OF_MONTH), null);
        }
    }

    @Override
    public void onResume() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, new IntentFilter(TMConstantKey.ACTION_NEXT));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }
}

