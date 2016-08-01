package com.hugobrisson.teammatetest.common.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class TMPathFileUtils {

    public static void launchBrowserFile(AppCompatActivity context) {
        int PICKFILE_RESULT_CODE = 1;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        context.startActivityForResult(intent, PICKFILE_RESULT_CODE);
    }


    public static Bitmap getBitmapFromPath(String sFilePath, File sImageName) {
        File sd = Environment.getExternalStorageDirectory();
        File image = new File(sImageName, sd + sFilePath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
    }
}

