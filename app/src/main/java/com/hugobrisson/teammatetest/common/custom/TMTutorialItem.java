package com.hugobrisson.teammatetest.common.custom;


import android.support.v4.app.Fragment;


public class TMTutorialItem {
    private Fragment fragment;
    private int color;

    public TMTutorialItem(Fragment fragment, int color) {
        this.fragment = fragment;
        this.color = color;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
