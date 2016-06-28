package com.hugobrisson.teammatetest.common;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hugobrisson.teammatetest.common.listener.ActivityListener;
import com.hugobrisson.teammatetest.common.manager.CFDFragmentManager;
import com.hugobrisson.teammatetest.common.model.TMFragmentType;

public abstract class ActivityController extends AppCompatActivity implements ActivityListener {

    private final static String TAG = ActivityController.class.getSimpleName();
    private boolean isAnimationFinished = false;
    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;

    protected abstract void onAnimationStarted();

    @Override
    public void onChangeActivity(Intent intent) {
        Log.v(TAG, "onChangeActivity");
        startActivity(intent);
    }

    @Override
    public void onChangeFragment(FragmentManager fragmentManager, Fragment fragment, TMFragmentType fragmentType) {
        Log.v(TAG, "onChangeFragment");
        if (getIntent() != null && getIntent().getExtras() != null) {
            fragment.setArguments(getIntent().getExtras());
        }
        switch (fragmentType) {
            case INIT:
                CFDFragmentManager.initFragment(fragmentManager, fragment);
                break;
            case NORMAL:
                CFDFragmentManager.changeFragment(fragmentManager, fragment);
                break;
            default:
                CFDFragmentManager.changeFragment(fragmentManager, fragment);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Log.v(this.getClass().getSimpleName(), "onBackPressed()");
        if (isAnimationFinished) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }
    }

    /**
     * @param sTitle
     * @param isHomeEnabled
     */
    public void showToolbarActivity(String sTitle, boolean isHomeEnabled) {
        mAppBar.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        ActionBar tActionBar = getSupportActionBar();
        if (tActionBar != null) {
            tActionBar.setDisplayHomeAsUpEnabled(isHomeEnabled);
            if (sTitle != null) {
                tActionBar.setDisplayShowTitleEnabled(true);
                tActionBar.setTitle(sTitle);
            }
        }
    }

    public void hideToolbarActivity() {
        mAppBar.setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
    }

}
