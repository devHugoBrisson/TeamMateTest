package com.hugobrisson.teammatetest.common.manager;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarManager {

    public static void show(View sView, String sText) {
        if (sView != null) {
            Snackbar.make(sView, sText, Snackbar.LENGTH_SHORT).show();
        }
    }
}
