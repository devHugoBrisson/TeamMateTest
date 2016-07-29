package com.hugobrisson.teammatetest.module.enrollment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hugobrisson.teammatetest.common.custom.TMTutorialItem;

import java.util.List;

public class RegisterPagerAdapter extends FragmentStatePagerAdapter {

    private List<TMTutorialItem> mTutorialItems;

    public RegisterPagerAdapter(FragmentManager fm, List<TMTutorialItem> tutorialItems) {
        super(fm);
        mTutorialItems = tutorialItems;
    }

    @Override
    public Fragment getItem(int position) {
        return mTutorialItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mTutorialItems.size();
    }
}
