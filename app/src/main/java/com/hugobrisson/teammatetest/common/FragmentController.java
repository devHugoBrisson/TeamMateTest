package com.hugobrisson.teammatetest.common;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hugobrisson.teammatetest.common.listener.ActivityListener;

public class FragmentController extends Fragment {

    public static final String TAG = FragmentController.class.getSimpleName();

    protected Context mContext;
    protected ActivityListener mActivityListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "onAttach");
        super.onAttach(context);
        try {
            mContext = context;
            mActivityListener = (ActivityListener) mContext;
        } catch (Exception e) {
            Log.v(TAG, "onAttach" + e.getMessage());
        }
    }

    /**
     * custom toolbar from fragment (collapse ...)
     **/
    public void setToolbar(Toolbar sToolbar, boolean isHomeEnabled, String sTitle) {
        ((ActivityController) getActivity()).setSupportActionBar(sToolbar);
        ActionBar tActionBar = getToolbar();
        if (tActionBar != null) {
            tActionBar.setDisplayHomeAsUpEnabled(isHomeEnabled);
            if (sTitle != null) {
                tActionBar.setDisplayShowTitleEnabled(true);
                tActionBar.setTitle(sTitle);
            }
        }

    }

    public ActionBar getToolbar() {
        return ((ActivityController) getActivity()).getSupportActionBar();
    }

    public void reloadToolbarActivity(String sTitle, boolean isHomeEnabled) {
        ((ActivityController) getActivity()).showToolbarActivity(sTitle, isHomeEnabled);
    }
}
