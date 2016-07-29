package com.hugobrisson.teammatetest.common.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.hugobrisson.teammatetest.R;

public class TMImageView extends AppCompatImageView {

    public TMImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TMImageView);
            int colorIcon = a.getColor(R.styleable.TMImageView_colorIcon, Color.WHITE);
            int colorBackground = a.getColor(R.styleable.TMImageView_colorBackground, ContextCompat.getColor(context, R.color.colorAccent));
            if (getBackground() != null) {
                setBackgroundColor(colorBackground);
            }
            setIconColor(colorIcon);
            a.recycle();
        }
    }

    public void setIconColor(int color) {
        getDrawable().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public void setBackgroundColor(int color) {
        getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

}
