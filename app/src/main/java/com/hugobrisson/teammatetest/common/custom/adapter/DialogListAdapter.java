package com.hugobrisson.teammatetest.common.custom.adapter;


import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.common.listener.ItemListener;
import com.hugobrisson.teammatetest.common.model.TMActionType;

import java.util.List;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.DialogViewHolder> {

    private List<String> mInfoList;
    private ItemListener mItemListener;

    public DialogListAdapter(List<String> sNoTypeInfoList, ItemListener sItemListener) {
        mInfoList = sNoTypeInfoList;
        mItemListener = sItemListener;
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tInflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_list, parent, false);
        return new DialogViewHolder(tInflatedView);
    }

    @Override
    public void onBindViewHolder(DialogViewHolder holder, final int position) {
        String tInfo = mInfoList.get(position);

        holder.tvTitle.setText(tInfo);
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.OnItemClick(v, position, TMActionType.CLICK);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }


    public static class DialogViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvTitle;

        public DialogViewHolder(View itemView) {
            super(itemView);
            tvTitle = (AppCompatTextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
