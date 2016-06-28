package com.hugobrisson.teammate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.hugobrisson.teammate.common.ActivityController;
import com.hugobrisson.teammate.module.enrollment.SignInActivity;

public class SplashscreenActivity extends ActivityController {

    AppCompatTextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        mTvTitle = (AppCompatTextView) findViewById(R.id.tv_title);
        assert mTvTitle != null;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onChangeActivity(new Intent(com.hugobrisson.teammate.SplashscreenActivity.this, SignInActivity.class));
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onAnimationStarted() {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }
}
