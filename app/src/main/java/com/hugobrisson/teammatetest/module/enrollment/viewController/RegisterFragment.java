package com.hugobrisson.teammatetest.module.enrollment.viewController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.TMDialogLoader;
import com.hugobrisson.teammatetest.common.listener.ResponseListener;
import com.hugobrisson.teammatetest.common.manager.PrefManager;
import com.hugobrisson.teammatetest.common.manager.SnackbarManager;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.hugobrisson.teammatetest.common.utils.TMNetworkUtils;
import com.hugobrisson.teammatetest.model.user.User;
import com.hugobrisson.teammatetest.model.user.service.UserService;
import com.hugobrisson.teammatetest.module.enrollment.CompleteRegisterActivity;
import com.hugobrisson.teammatetest.module.enrollment.manager.TMAuthenticationManager;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = RegisterFragment.class.getSimpleName();

    private AppCompatButton mBtSignIn;
    private AppCompatEditText mEtMail, mEtPassword, mEtConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tLayout = inflater.inflate(R.layout.fragment_register, container, false);
        mBtSignIn = (AppCompatButton) tLayout.findViewById(R.id.bt_sign_in);
        mEtMail = (AppCompatEditText) tLayout.findViewById(R.id.et_mail);
        mEtPassword = (AppCompatEditText) tLayout.findViewById(R.id.et_password);
        mEtConfirmPassword = (AppCompatEditText) tLayout.findViewById(R.id.et_confirm_password);
        return tLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sign_in:
                register();
                break;
        }
    }

    private void register() {
        Log.d(TAG, "register()");

        final String tMail = mEtMail.getText().toString();
        final String tPassword = mEtPassword.getText().toString();
        String tConfirmPassword = mEtConfirmPassword.getText().toString();

        final TMDialogLoader tLoader = new TMDialogLoader(getContext(), "Enregistrement...");
        if (TMNetworkUtils.isNetworkAvailable(getActivity())) {
            tLoader.show();
            TMAuthenticationManager.getInstance(getActivity()).registerAccount(getView(), tMail, tPassword, tConfirmPassword, new ResponseListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    final FirebaseUser firebaseUser = authResult.getUser();
                    UserService userService = UserService.getInstance();
                    final User tUser = new User(userService.getKey(), firebaseUser.getEmail());
                    userService.create(tUser, tUser.getId(), new ResponseListener<User>() {
                        @Override
                        public void onSuccess(User user) {
                            SharedPreferences sharedPreferences = PrefManager.getInstance(getContext());
                            sharedPreferences.edit().putString(TMConstantKey.PREF_MAIL, tMail).apply();
                            sharedPreferences.edit().putString(TMConstantKey.PREF_PASSWORD, tPassword).apply();
                            tLoader.hide();
                            startActivity(new Intent(getActivity(), CompleteRegisterActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onFailed(Exception e) {
                            tLoader.hide();
                            SnackbarManager.show(getView(), getString(R.string.error_register_no_user));
                        }
                    });
                }

                @Override
                public void onFailed(Exception e) {
                    tLoader.hide();
                    SnackbarManager.show(getView(), getString(R.string.error_authentication_no_user));
                }
            });
        } else {
            SnackbarManager.show(getView(), getString(R.string.error_no_internet));
        }
    }


}
