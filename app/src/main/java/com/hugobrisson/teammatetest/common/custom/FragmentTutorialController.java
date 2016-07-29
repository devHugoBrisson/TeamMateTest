package com.hugobrisson.teammatetest.common.custom;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.hugobrisson.teammatetest.common.listener.TutorialListener;
import com.hugobrisson.teammatetest.model.user.User;
import com.hugobrisson.teammatetest.module.enrollment.CompleteRegisterActivity;

public class FragmentTutorialController extends Fragment {

    private static final String TAG = FragmentTutorialController.class.getSimpleName();

    protected Context mContext;
    protected TutorialListener mTutorialListener;
    protected User mCurrentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "onAttach");
        super.onAttach(context);
        try {
            mContext = context;
            mTutorialListener = (TutorialListener) mContext;
        } catch (Exception e) {
            Log.v(TAG, "onAttach" + e.getMessage());
        }
    }

    protected User getCurrentUserTutorial() {
        return ((CompleteRegisterActivity) mContext).getCurrentUserTutorial();
    }

    protected void setCurrentUserTutorial(User user) {
        ((CompleteRegisterActivity) mContext).setCurrentUserTutorial(user);
    }
}
