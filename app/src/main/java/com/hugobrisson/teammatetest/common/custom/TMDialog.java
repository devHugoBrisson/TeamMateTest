package com.hugobrisson.teammatetest.common.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.custom.adapter.DialogListAdapter;
import com.hugobrisson.teammatetest.common.listener.ItemListener;
import com.hugobrisson.teammatetest.common.model.TMActionType;
import com.hugobrisson.teammatetest.common.model.TMDialogType;

import java.util.List;

public class TMDialog extends Dialog implements View.OnClickListener {

    private AppCompatTextView mTvTitle, mTvDesc, mTvValid, mTvCancel;
    private AppCompatEditText mEtDialog;
    private RecyclerView mRvDialog;
    private boolean mTwoButton;

    private Context mContext;
    private int mTitle, mDesc,mHint;
    private View.OnClickListener mClickListener;
    private ItemListener mItemClickListener;
    private List<String> mInfoList;

    public TMDialog(Context context, int sTitle, int sDesc, boolean sTwoButton, View.OnClickListener sClickListener) {
        super(context);
        mContext = context;
        mTitle = sTitle;
        mDesc = sDesc;
        mTwoButton = sTwoButton;
        mClickListener = sClickListener;
        initDialog(TMDialogType.INFO);
    }

    public TMDialog(Context context, int sTitle, int sDesc, int sHint) {
        super(context);
        mContext = context;
        mTitle = sTitle;
        mDesc = sDesc;
        mHint = sHint;
        initDialog(TMDialogType.EDIT);
    }

    public TMDialog(Context context, List<String> sInfoList, ItemListener sItemClickListener) {
        super(context);
        mContext = context;
        mInfoList = sInfoList;
        mItemClickListener = sItemClickListener;
        initDialog(TMDialogType.LIST);
    }

    private void initDialog(TMDialogType sDialogType) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_classic);
        mTvTitle = (AppCompatTextView) findViewById(R.id.tv_dialog_title);
        mTvValid = (AppCompatTextView) findViewById(R.id.tv_dialog_valid);
        mTvCancel = (AppCompatTextView) findViewById(R.id.tv_dialog_cancel);
        mTvTitle.setText(mTitle);
        mTvValid.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);

        switch (sDialogType) {
            case INFO:
                mTvDesc = (AppCompatTextView) findViewById(R.id.tv_dialog_desc);
                mTvDesc.setText(mDesc);
                mTvDesc.setVisibility(View.VISIBLE);
                if (!mTwoButton) {
                    mTvCancel.setVisibility(View.GONE);
                }
                break;
            case EDIT:
                if (mDesc != 0) {
                    mTvDesc = (AppCompatTextView) findViewById(R.id.tv_dialog_desc);
                    mTvDesc.setText(mDesc);
                    mTvDesc.setVisibility(View.VISIBLE);
                }
                mEtDialog = (AppCompatEditText) findViewById(R.id.et_dialog);
                mEtDialog.setHint(mHint);
                mEtDialog.setVisibility(View.VISIBLE);
                break;
            case LIST:
                mRvDialog = (RecyclerView) findViewById(R.id.rv_dialog);
                mTvTitle.setVisibility(View.GONE);
                mTvValid.setVisibility(View.GONE);
                mTvCancel.setVisibility(View.GONE);
                mRvDialog.setVisibility(View.VISIBLE);
                mRvDialog.setLayoutManager(new LinearLayoutManager(mContext));
                mRvDialog.setAdapter(new DialogListAdapter(mInfoList, new ItemListener() {
                    @Override
                    public void OnItemClick(View v, int position, TMActionType actionType) {
                        mItemClickListener.OnItemClick(v, position, TMActionType.CLICK);
                        dismiss();
                    }
                }));
                break;
        }

        show();
    }

    public void addClickListener(View.OnClickListener sClickListener){
        mClickListener = sClickListener;
    }

    public void setTextError(String sError) {
        mEtDialog.setError(sError);
    }

    public String getText() {
        return mEtDialog.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_valid:
                if (mEtDialog == null) {
                    if(mClickListener!=null){
                        mClickListener.onClick(v);
                    }
                    dismiss();
                } else {
                    mClickListener.onClick(mEtDialog);
                }
                break;
            case R.id.tv_dialog_cancel:
                dismiss();
                break;
        }
    }
}
