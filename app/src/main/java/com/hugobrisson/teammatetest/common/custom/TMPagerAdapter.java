package com.hugobrisson.teammatetest.common.custom;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class TMPagerAdapter extends FragmentStatePagerAdapter {

    private String mTabTitles[];
    private List<Fragment> mFragmentList;
    private Context mContext;

    public TMPagerAdapter(FragmentManager fm, String sTabTitles[], List<Fragment> sFragmentList) {
        super(fm);
        mTabTitles = sTabTitles;
        mFragmentList = sFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
