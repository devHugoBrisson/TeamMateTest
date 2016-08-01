package com.hugobrisson.teammatetest.module.enrollment.viewController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.FragmentTutorialController;
import com.hugobrisson.teammatetest.common.utils.TMBase64Utils;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class RegisterProfilePictureFragment extends FragmentTutorialController implements View.OnClickListener {

    private CircularImageView mCivProfilePhoto;
    private final int SELECT_PHOTO = 1;
    private Bitmap mBitmap;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO set user man
            if (intent != null) {
                int position = intent.getExtras().getInt(TMConstantKey.EXTRA_NEXT);
                if (position == 3) {
                    mCurrentUser = getCurrentUserTutorial();
                    if (mBitmap != null) {
                        String photoBase64 = TMBase64Utils.encode(mBitmap);
                        mCurrentUser.setProfilePhoto(photoBase64);
                        setCurrentUserTutorial(mCurrentUser);
                    }
                    mTutorialListener.onDone();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View tLayout = inflater.inflate(R.layout.fragment_register_photo, container, false);
        mCivProfilePhoto = (CircularImageView) tLayout.findViewById(R.id.civ_photo);
        return tLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCivProfilePhoto.setOnClickListener(this);
        mCurrentUser = getCurrentUserTutorial();
        String photoBase64 = mCurrentUser.getProfilePhoto();
        if (photoBase64 != null) {
            mBitmap = TMBase64Utils.decode(photoBase64);
            mCivProfilePhoto.setImageBitmap(mBitmap);
        }
    }

    @Override
    public void onResume() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, new IntentFilter(TMConstantKey.ACTION_NEXT));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_photo:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        mBitmap = BitmapFactory.decodeStream(imageStream);
                        mCivProfilePhoto.setImageBitmap(mBitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}

