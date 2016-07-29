package com.hugobrisson.teammatetest.module.enrollment.viewController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.FragmentTutorialController;
import com.hugobrisson.teammatetest.common.manager.SnackbarManager;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;


public class RegisterUsernameFragment extends FragmentTutorialController {

    private AppCompatEditText mEtUsername;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int position = intent.getExtras().getInt(TMConstantKey.EXTRA_NEXT);
                if (position == 0) {
                    String username = mEtUsername.getText().toString();
                    if (username.equals("")) {
                        SnackbarManager.show(getView(), getString(R.string.error_all_fields));
                    } else if (username.length() < 6) {
                        SnackbarManager.show(getView(), getString(R.string.error_username_size));
                    } else {
                        mCurrentUser = getCurrentUserTutorial();
                        mCurrentUser.setName(username);
                        setCurrentUserTutorial(mCurrentUser);
                        mTutorialListener.onNext();
                    }
                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tLayout = inflater.inflate(R.layout.fragment_register_username, container, false);
        mEtUsername = (AppCompatEditText) tLayout.findViewById(R.id.et_username);
        return tLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCurrentUser = getCurrentUserTutorial();
        mEtUsername.setText(mCurrentUser.getName());

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
