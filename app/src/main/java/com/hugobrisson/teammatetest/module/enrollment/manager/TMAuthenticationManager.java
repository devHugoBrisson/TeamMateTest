package com.hugobrisson.teammatetest.module.enrollment.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.TMDialogLoader;
import com.hugobrisson.teammatetest.common.listener.ResponseListener;
import com.hugobrisson.teammatetest.common.manager.SnackbarManager;
import com.hugobrisson.teammatetest.common.utils.TMEmailUtils;

import java.util.Arrays;

public class TMAuthenticationManager {

    private static final String TAG = TMAuthenticationManager.class.getSimpleName();
    private static Context mContext;
    private View mView;
    private String mMail, mPassword;
    private static TMDialogLoader mLoader;

    private static TMAuthenticationManager tmAuthenticationManager;

    public static TMAuthenticationManager getInstance(Context context) {
        mContext = context;
        if (tmAuthenticationManager == null) {
            tmAuthenticationManager = new TMAuthenticationManager();
        }
        mLoader = new TMDialogLoader(mContext, null);
        return tmAuthenticationManager;
    }

    private boolean isCorrectFields(View view, String mail, String password) {
        if (mail.equals("") || password.equals("")) {
            SnackbarManager.show(view, mContext.getString(R.string.error_authentication_fields));
        } else if (!TMEmailUtils.isCorrectAddressMail(mail)) {
            SnackbarManager.show(view, mContext.getString(R.string.error_password_size));
        } else if (password.length() < 6) {
            SnackbarManager.show(view, mContext.getString(R.string.error_format_mail));
        } else {
            return true;
        }
        return false;
    }

    private boolean isCorrectFields(View view, String mail, String password, String confirmPassword) {
        if (mail.equals("") || password.equals("") || confirmPassword.equals("")) {
            SnackbarManager.show(view, mContext.getString(R.string.error_authentication_fields));
        } else if (!TMEmailUtils.isCorrectAddressMail(mail)) {
            SnackbarManager.show(view, mContext.getString(R.string.error_password_size));
        } else if (password.length() < 6) {
            SnackbarManager.show(view, mContext.getString(R.string.error_format_mail));
        } else if (!password.equals(confirmPassword)) {
            SnackbarManager.show(view, mContext.getString(R.string.error_password_different));
        } else {
            return true;
        }
        return false;
    }

    public void signInAccount(View view, String mail, String password, final ResponseListener<AuthResult> responseListener) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (isCorrectFields(view, mail, password)) {
            mLoader.show();
            if (firebaseUser != null) {
                firebaseAuth.signOut();
            }
            firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "onFailed");
                        mLoader.hide();
                        responseListener.onFailed(task.getException());
                    } else {
                        Log.d(TAG, "onSuccess");
                        mLoader.hide();
                        responseListener.onSuccess(task.getResult());
                    }
                }
            });
        }
    }

    public void registerAccount(View view, String mail, String password, String confirmPassword, final ResponseListener<AuthResult> responseListener) {
        FirebaseAuth fireBaseAuth = FirebaseAuth.getInstance();
        if (isCorrectFields(view, mail, password, confirmPassword)) {
            fireBaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "onFailed");
                        responseListener.onFailed(task.getException());
                    } else {
                        Log.d(TAG, "onSuccess");
                        responseListener.onSuccess(task.getResult());
                    }
                }
            });
        }
    }

    public void signInWithFacebook(Fragment fragment, final ResponseListener<AuthResult> responseListener) {
        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(fragment, Arrays.asList("email", "public_profile"));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken(), responseListener);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                responseListener.onFailed(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError");
                responseListener.onFailed(error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token, final ResponseListener<AuthResult> responseListener) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    responseListener.onFailed(task.getException());
                } else {
                    responseListener.onSuccess(task.getResult());
                }
            }
        });
    }

    public void signInWithGoogle(Fragment fragment, Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct, final ResponseListener<AuthResult> responseListener) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential", task.getException());
                    responseListener.onFailed(task.getException());
                } else {
                    responseListener.onSuccess(task.getResult());
                }
            }
        });
    }
}
