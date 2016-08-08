package com.hugobrisson.teammatetest.module.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.hugobrisson.teammatetest.R;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;


public class MainActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {
    BottomNavigation mBottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBottomNavigation = (BottomNavigation) findViewById(R.id.nav_menu);

        if (mBottomNavigation != null) {
            mBottomNavigation.setOnMenuItemClickListener(this);
        }
    }


    @Override
    public void onMenuItemSelect(@IdRes int i, int i1) {
        switch (i){
            case R.id.action_home:
                break;
            case R.id.action_activities:
                break;
            case R.id.action_arround_me:
                break;
            case R.id.action_chat:
                break;
            case R.id.action_settings:
                break;
        }

    }

    @Override
    public void onMenuItemReselect(@IdRes int i, int i1) {

    }
}

