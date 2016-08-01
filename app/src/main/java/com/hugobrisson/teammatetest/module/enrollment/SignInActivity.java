package com.hugobrisson.teammatetest.module.enrollment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.AuthResult;
import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.listener.ResponseListener;
import com.hugobrisson.teammatetest.common.manager.FragmentManager;
import com.hugobrisson.teammatetest.common.manager.SnackbarManager;
import com.hugobrisson.teammatetest.common.utils.TMNetworkUtils;
import com.hugobrisson.teammatetest.module.enrollment.manager.TMAuthenticationManager;
import com.hugobrisson.teammatetest.module.enrollment.viewController.RegisterFragment;
import com.hugobrisson.teammatetest.module.enrollment.viewController.SignInFragment;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mPref;
    private FloatingActionButton mBtFacebook, mBtGoogle;
    private boolean isAuthenticating = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        mBtFacebook = (FloatingActionButton) findViewById(R.id.bt_facebook);
        mBtGoogle = (FloatingActionButton) findViewById(R.id.bt_google_more);
        mBtFacebook.setOnClickListener(this);
        mBtGoogle.setOnClickListener(this);
        FragmentManager.changeFragment(getSupportFragmentManager(), new SignInFragment(), true);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_facebook:
                signInWithFacebook();
                break;
            case R.id.bt_google_more:
                //  signInWithGoogle();
                break;
        }
    }

    private void signInWithFacebook() {
        if (TMNetworkUtils.isNetworkAvailable(this) && !isAuthenticating) {
            isAuthenticating=true;
            TMAuthenticationManager.getInstance(this).signInWithFacebook(this, new ResponseListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    SnackbarManager.show(SignInActivity.this.getWindow().getDecorView(), "Authentifié avec facebook");
                    //TODO sharePref mail and password;
                    //TODO start main activity
                }

                @Override
                public void onFailed(Exception e) {
                    if (e != null) {
                        SnackbarManager.show(SignInActivity.this.getWindow().getDecorView(), getString(R.string.error_authentication_no_user));
                    }

                }
            });
        } else {
            SnackbarManager.show(this.getWindow().getDecorView(), getString(R.string.error_no_internet));
        }
    }

    private void signInWithGoogle() {
        if (TMNetworkUtils.isNetworkAvailable(this)) {
        } else {
            SnackbarManager.show(this.getWindow().getDecorView(), getString(R.string.error_no_internet));
        }
    }
}

