package com.hugobrisson.teammatetest.module.enrollment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.manager.FragmentManager;
import com.hugobrisson.teammatetest.module.enrollment.viewController.SignInFragment;


public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        FragmentManager.changeFragment(getSupportFragmentManager(),new SignInFragment());
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }else{
            finish();
        }
    }
}
