package com.hugobrisson.teammatetest.module.enrollment.viewController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.FragmentTutorialController;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.hugobrisson.teammatetest.model.user.Gender;


public class RegisterGenderFragment extends FragmentTutorialController implements View.OnClickListener {

    private FloatingActionButton mFabMan, mFabWoman;
    private AppCompatImageView mIvDoneMan, mIvDoneWoman;
    private Gender mGender = Gender.MAN;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO set user man
            if (intent!= null){
             int position =  intent.getExtras().getInt(TMConstantKey.EXTRA_NEXT);
                if(position ==1){
                    mCurrentUser = getCurrentUserTutorial();
                    mCurrentUser.setGenderAsEnum(mGender);
                    setCurrentUserTutorial(mCurrentUser);
                    mTutorialListener.onNext();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tLayout = inflater.inflate(R.layout.fragment_register_gender, container, false);
        mFabMan = (FloatingActionButton) tLayout.findViewById(R.id.fab_man);
        mFabWoman = (FloatingActionButton) tLayout.findViewById(R.id.fab_woman);
        mIvDoneMan = (AppCompatImageView) tLayout.findViewById(R.id.iv_done_man);
        mIvDoneWoman = (AppCompatImageView) tLayout.findViewById(R.id.iv_done_woman);
        return tLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFabMan.setOnClickListener(this);
        mFabWoman.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_man:
                if (mIvDoneWoman.getVisibility() == View.VISIBLE) {
                    mIvDoneWoman.setVisibility(View.INVISIBLE);
                    mIvDoneMan.setVisibility(View.VISIBLE);
                    mGender = Gender.MAN;
                }
                break;
            case R.id.fab_woman:
                if (mIvDoneMan.getVisibility() == View.VISIBLE) {
                    mIvDoneMan.setVisibility(View.INVISIBLE);
                    mIvDoneWoman.setVisibility(View.VISIBLE);
                    mGender = Gender.WOMAN;
                }
                break;
        }
    }
}

