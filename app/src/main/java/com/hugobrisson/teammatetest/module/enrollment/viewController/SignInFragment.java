package com.hugobrisson.teammatetest.module.enrollment.viewController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.TMDialog;
import com.hugobrisson.teammatetest.common.custom.TMDialogLoader;
import com.hugobrisson.teammatetest.common.listener.ResponseListener;
import com.hugobrisson.teammatetest.common.manager.FragmentManager;
import com.hugobrisson.teammatetest.common.manager.PrefManager;
import com.hugobrisson.teammatetest.common.manager.SnackbarManager;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.hugobrisson.teammatetest.common.utils.TMEmailUtils;
import com.hugobrisson.teammatetest.model.sport.Sport;
import com.hugobrisson.teammatetest.model.sport.dao.SportDao;
import com.hugobrisson.teammatetest.model.sport.service.SportService;
import com.hugobrisson.teammatetest.model.user.User;
import com.hugobrisson.teammatetest.model.user.service.UserService;
import com.hugobrisson.teammatetest.module.enrollment.CompleteRegisterActivity;
import com.hugobrisson.teammatetest.module.enrollment.manager.TMAuthenticationManager;
import com.hugobrisson.teammatetest.common.utils.TMNetworkUtils;
import com.hugobrisson.teammatetest.module.home.MainActivity;

import java.util.List;

public class SignInFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = SignInFragment.class.getSimpleName();

    private SharedPreferences mPref;
    private AppCompatEditText mEtMail, mEtPassword;
    private FloatingActionButton mBtFacebook, mBtGoogle;
    private AppCompatButton mBtLogIn;
    private AppCompatTextView mTvForgotPassword, mTvSignIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tLayout = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mEtMail = (AppCompatEditText) tLayout.findViewById(R.id.et_mail);
        mEtPassword = (AppCompatEditText) tLayout.findViewById(R.id.et_password);
        mBtLogIn = (AppCompatButton) tLayout.findViewById(R.id.bt_log_in);
        mBtFacebook = (FloatingActionButton) tLayout.findViewById(R.id.bt_facebook);
        mBtGoogle = (FloatingActionButton) tLayout.findViewById(R.id.bt_google_more);
        mTvForgotPassword = (AppCompatTextView) tLayout.findViewById(R.id.tv_forgot_password);
        mTvSignIn = (AppCompatTextView) tLayout.findViewById(R.id.tv_register);
        return tLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtLogIn.setOnClickListener(this);
        mTvSignIn.setOnClickListener(this);
        mTvForgotPassword.setOnClickListener(this);
        mBtFacebook.setOnClickListener(this);
        mBtGoogle.setOnClickListener(this);

        mPref = PrefManager.getInstance(getContext());
        String mail = mPref.getString(TMConstantKey.PREF_MAIL, null);
        mEtMail.setText(mail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_log_in:
                signIn();
                break;
            case R.id.tv_register:
                FragmentManager.changeFragment(getActivity().getSupportFragmentManager(), new RegisterFragment());
                break;
            case R.id.tv_forgot_password:
                forgotPassword();
                break;
            case R.id.bt_facebook:
                signInWithFacebook();
                break;
            case R.id.bt_google_more:
                //  signInWithGoogle();
                break;
        }
    }

    /**
     * call api firebase for authentication
     */
    private void signIn() {
        Log.d(TAG, "signIn()");
        final String tMail = mEtMail.getText().toString();
        final String tPassword = mEtPassword.getText().toString();
        if (TMNetworkUtils.isNetworkAvailable(getContext())) {
            TMAuthenticationManager.getInstance(getActivity()).signInAccount(getView(), tMail, tPassword, new ResponseListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    UserService.getInstance().getByValue(tMail, User.COLUMN_MAIL, new ResponseListener<List<User>>() {
                        @Override
                        public void onSuccess(List<User> users) {
                            mPref.edit().putString(TMConstantKey.PREF_MAIL, tMail).apply();
                            mPref.edit().putString(TMConstantKey.PREF_PASSWORD, tPassword).apply();
                            User user = users.get(0);
                            user.save();
                            if (!user.getTutorialFinished()) {
                                startActivity(new Intent(getContext(), CompleteRegisterActivity.class));
                                getActivity().finish();
                            } else {
                                long tDate = mPref.getInt(TMConstantKey.PREF_DIFF_DATE,0);
                                SportService.getInstance(getContext()).saveByDiff(tDate, new ResponseListener<List<Sport>>() {
                                    @Override
                                    public void onSuccess(List<Sport> sports) {
                                        startActivity(new Intent(getContext(), MainActivity.class));
                                    }

                                    @Override
                                    public void onFailed(Exception e) {
                                        // TODO impossible de charger les sport ;
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailed(Exception e) {
                            SnackbarManager.show(getView(), getString(R.string.error_authentication_no_user));
                        }
                    });
                }

                @Override
                public void onFailed(Exception e) {
                    SnackbarManager.show(getView(), getString(R.string.error_authentication_no_user));
                }
            });
        } else {
            SnackbarManager.show(getView(), getString(R.string.error_no_internet));
        }
    }

    private void forgotPassword() {
        Log.d(TAG, "forgotPassword()");
        final TMDialog dialogMail = new TMDialog(getContext(), R.string.dialog_reset_password, R.string.dialog_reset_password_desc, R.string.mail);
        final TMDialogLoader loader = new TMDialogLoader(getContext(), null);
        dialogMail.addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = dialogMail.getText();
                if (mail != null && TMEmailUtils.isCorrectAddressMail(mail)) {
                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    if (TMNetworkUtils.isNetworkAvailable(getContext())) {
                        loader.show();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                loader.hide();
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "onSuccess()");
                                    dialogMail.dismiss();
                                    new TMDialog(getContext(), R.string.dialog_info_title, R.string.dialog_info_password_desc, false, null);
                                } else {
                                    Log.d(TAG, "onFailed() " + task.getException().getMessage());
                                    new TMDialog(getContext(), R.string.dialog_error, R.string.dialog_error_mail, false, null);
                                }

                            }
                        });
                    } else {
                        new TMDialog(getContext(), R.string.dialog_error, R.string.error_no_internet, false, null);
                    }
                } else {
                    dialogMail.setTextError(getString(R.string.error_format_mail));
                }
            }
        });
    }

    private void signInWithFacebook() {
        if (TMNetworkUtils.isNetworkAvailable(getContext())) {
            TMAuthenticationManager.getInstance(getActivity()).signInWithFacebook(this, new ResponseListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    //TODO sharePref mail and password;
                    //TODO start main activity
                }

                @Override
                public void onFailed(Exception e) {
                    if (e != null) {
                        SnackbarManager.show(getView(), getString(R.string.error_authentication_no_user));
                    }

                }
            });
        } else {
            SnackbarManager.show(getView(), getString(R.string.error_no_internet));
        }
    }

    private void signInWithGoogle() {
        if (TMNetworkUtils.isNetworkAvailable(getContext())) {
        } else {
            SnackbarManager.show(getView(), getString(R.string.error_no_internet));
        }
    }


}
