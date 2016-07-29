package com.hugobrisson.teammatetest.common.listener;

import android.view.View;

import com.hugobrisson.teammatetest.common.model.TMActionType;

public interface ItemListener {
    void OnItemClick(View v, int position, TMActionType actionType);
}
