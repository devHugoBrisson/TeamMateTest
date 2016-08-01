package com.hugobrisson.teammatetest.module.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.model.user.User;
import com.hugobrisson.teammatetest.model.user.dao.UserDao;
import com.hugobrisson.teammatetest.module.enrollment.SignInActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private AppCompatTextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mTvTitle = (AppCompatTextView) findViewById(R.id.tv_title);
        if (mTvTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
        }
        mTvTitle.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                User user = UserDao.getInstance(SplashScreenActivity.this).getCurrentUser();
                if (firebaseUser == null || user == null || !user.getTutorialFinished()) {
                    startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
                } else {
                    // main activity
                    //  startActivity(new Intent(SplashScreenActivity.this, CompleteRegisterActivity.class));
                }
                finish();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}


