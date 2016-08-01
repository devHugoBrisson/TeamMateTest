package com.hugobrisson.teammatetest.common.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.hugobrisson.teammatetest.R;

public class TMDialogLoader extends Dialog {

    private AppCompatTextView mTvDesc;
    private ProgressBar mPbLoader;

    public TMDialogLoader(Context context, String sDesc) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_loader);
        setCancelable(false);
        mTvDesc = (AppCompatTextView) findViewById(R.id.tv_dialog_desc);
        mPbLoader = (ProgressBar) findViewById(R.id.pb_loader);
        if (sDesc != null) {
            mTvDesc.setVisibility(View.VISIBLE);
            mTvDesc.setText(sDesc);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        mPbLoader.getIndeterminateDrawable().setColorFilter(new PorterDuffColorFilter
                (ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN));
    }

}
