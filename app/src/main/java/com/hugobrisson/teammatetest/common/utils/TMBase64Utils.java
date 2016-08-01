package com.hugobrisson.teammatetest.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Base 64 utils.
 */
public class TMBase64Utils {

    /**
     * encode bitmap to string base 64 value.
     *
     * @param sBitmap bitmap
     * @return base 64 string
     */
    public static String encode(Bitmap sBitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        sBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * encode Drawable to string base 64 value.
     *
     * @param sDrawable bitmap
     * @return base 64 string
     */
    public static String encode(Resources sResources, int sDrawable) {

        Bitmap sBitmap = BitmapFactory.decodeResource(sResources, sDrawable);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        sBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    /**
     * decode string base 64 value .
     *
     * @param sValueBase64 base 64
     * @return bitmap
     */
    public static Bitmap decode(String sValueBase64) {
        byte[] decodedString = Base64.decode(sValueBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    /**
     * decode string base 64 value .
     *
     * @param sValueBase64 base 64
     * @return bitmap
     */
    public static Drawable decode(Resources sResources,String sValueBase64) {
        byte[] decodedString = Base64.decode(sValueBase64, Base64.DEFAULT);
        Bitmap sBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return new BitmapDrawable(sResources,sBitmap);
    }
}
