package com.hugobrisson.teammatetest.module.enrollment;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.TMDialogLoader;
import com.hugobrisson.teammatetest.common.custom.TMNoSwipeableViewPager;
import com.hugobrisson.teammatetest.common.custom.TMTutorialItem;
import com.hugobrisson.teammatetest.common.listener.ResponseListener;
import com.hugobrisson.teammatetest.common.listener.TutorialListener;
import com.hugobrisson.teammatetest.common.manager.SnackbarManager;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.hugobrisson.teammatetest.model.user.User;
import com.hugobrisson.teammatetest.model.user.dao.UserDao;
import com.hugobrisson.teammatetest.model.user.service.UserService;
import com.hugobrisson.teammatetest.module.enrollment.adapter.RegisterPagerAdapter;
import com.hugobrisson.teammatetest.module.enrollment.viewController.RegisterBirthdayFragment;
import com.hugobrisson.teammatetest.module.enrollment.viewController.RegisterGenderFragment;
import com.hugobrisson.teammatetest.module.enrollment.viewController.RegisterProfilePictureFragment;
import com.hugobrisson.teammatetest.module.enrollment.viewController.RegisterUsernameFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompleteRegisterActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, TutorialListener {

    private TMNoSwipeableViewPager mVpGlobal;
    private AppCompatTextView mTvBack, mTvNext;
    private RelativeLayout mLlGlobal;
    private LinearLayout mLlDots;
    private List<AppCompatImageView> mDots;
    private List<TMTutorialItem> mTutorialItems;
    private int mCurrentPage = 0;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);
        mLlGlobal = (RelativeLayout) findViewById(R.id.rl_global);
        mVpGlobal = (TMNoSwipeableViewPager) findViewById(R.id.vp_global);
        mTvBack = (AppCompatTextView) findViewById(R.id.tv_back);
        mTvNext = (AppCompatTextView) findViewById(R.id.tv_next);
        mLlDots = (LinearLayout) findViewById(R.id.ll_dots);

        mTutorialItems = new ArrayList<>(Arrays.asList(new TMTutorialItem(new RegisterUsernameFragment(), R.color.colorUsername),
                new TMTutorialItem(new RegisterGenderFragment(), R.color.colorGender), new TMTutorialItem(new RegisterBirthdayFragment(), R.color.colorBirthDay),
                new TMTutorialItem(new RegisterProfilePictureFragment(), R.color.colorPhoto)));
        mVpGlobal.setAdapter(new RegisterPagerAdapter(getSupportFragmentManager(), mTutorialItems));
        mVpGlobal.addOnPageChangeListener(this);
        mTvNext.setOnClickListener(this);
        mTvBack.setOnClickListener(this);
        mUser = UserDao.getInstance(this).getCurrentUser();
        addDots();
        selectDot(0);
        updateUi(0);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPage == 0) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        } else {
            mVpGlobal.setCurrentItem(mCurrentPage - 1);
        }
    }

    public User getCurrentUserTutorial() {
        return mUser;
    }

    public void setCurrentUserTutorial(User user) {
        mUser = user;
    }


    private void addDots() {
        mDots = new ArrayList<>();
        for (int i = 0; i < mTutorialItems.size(); i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 5, 0);
            AppCompatImageView dot = new AppCompatImageView(this);
            dot.setLayoutParams(lp);
            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pager_dot_not_selected));
            mLlDots.addView(dot);
            mDots.add(dot);
        }
    }

    private void selectDot(int idx) {
        for (int i = 0; i <= mTutorialItems.size() - 1; i++) {
            int drawableId = (i == idx) ? (R.drawable.pager_dot_selected) : (R.drawable.pager_dot_not_selected);
            mDots.get(i).setImageDrawable(ContextCompat.getDrawable(this, drawableId));
        }
    }

    private void updateUi(int position) {
        mCurrentPage = position;
        if (position == 0) {
            mTvBack.setVisibility(View.GONE);
        } else if (position == mTutorialItems.size() - 1) {
            mTvNext.setText(R.string.register_done);
        } else {
            mTvBack.setVisibility(View.VISIBLE);
            mTvNext.setText(R.string.register_next);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position < (mTutorialItems.size() - 1)) {
            mLlGlobal.setBackgroundColor((Integer) new ArgbEvaluator().evaluate(positionOffset, ContextCompat.getColor(this, mTutorialItems.get(position).getColor())
                    , ContextCompat.getColor(this, mTutorialItems.get(position + 1).getColor())));
        } else {
            mLlGlobal.setBackgroundColor((Integer) new ArgbEvaluator().evaluate(positionOffset, ContextCompat.getColor(this, mTutorialItems.get(position).getColor())
                    , ContextCompat.getColor(this, mTutorialItems.get(position - 1).getColor())));
        }
    }

    @Override
    public void onPageSelected(int position) {
        updateUi(position);
        selectDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(TMConstantKey.ACTION_NEXT)
                        .putExtra(TMConstantKey.EXTRA_NEXT, mCurrentPage));
                break;
            case R.id.tv_back:
                mVpGlobal.setCurrentItem(mCurrentPage - 1);
                break;
        }
    }

    @Override
    public void onNext() {
        mVpGlobal.setCurrentItem(mCurrentPage + 1);
    }

    @Override
    public void onDone() {
        final TMDialogLoader loader = new TMDialogLoader(this, null);
        loader.show();
        mUser.setTutorialFinished(true);
        UserService.getInstance().update(mUser, mUser.getId(), new ResponseListener<User>() {
            @Override
            public void onSuccess(User user) {
                mUser.save();
                loader.hide();
                //TODO launch main activity
            }

            @Override
            public void onFailed(Exception e) {
                loader.hide();
                SnackbarManager.show(mLlGlobal, getString(R.string.error_complete_account));
            }
        });
    }
}
