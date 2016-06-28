package com.hugobrisson.teammatetest.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.hugobrisson.teammatetest.R;

public class SplashscreenActivity extends AppCompatActivity {

    AppCompatTextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mTvTitle = (AppCompatTextView) findViewById(R.id.tv_title);
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        assert mTvTitle != null;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  onChangeActivity(new Intent(SplashscreenActivity.this, SignInActivity.class));
              //  finish();
            }
        }, 3000);
    }

}
