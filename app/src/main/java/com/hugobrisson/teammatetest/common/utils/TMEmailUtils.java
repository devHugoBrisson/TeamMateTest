package com.hugobrisson.teammatetest.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email utils
 */
public class TMEmailUtils {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String TAG = TMEmailUtils.class.getSimpleName();

    /**
     * Allow to send email
     *
     * @param sContext Activity's context
     * @param sEmail   String email
     */
    public static void sendEmail(Context sContext, String sEmail) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", sEmail, null));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{sEmail});
        if (emailIntent.resolveActivity(sContext.getPackageManager()) != null) {
          //  sContext.startActivity(Intent.createChooser(emailIntent, sContext.getString(R.string.task_details_email_intent)));
        }
    }

    /**
     * verify address mail.
     *
     * @return boolean
     */
    public static boolean isCorrectAddressMail(String sText) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(sText);
        return matcher.matches();
    }

}
