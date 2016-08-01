package com.hugobrisson.teammatetest.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.hugobrisson.teammatetest.R;
import com.hugobrisson.teammatetest.model.sport.Sport;
import com.hugobrisson.teammatetest.model.sport.service.SportService;


public class GenerateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        AppCompatButton btGenerate = (AppCompatButton) findViewById(R.id.bt_generate);
        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   SportService sportService = SportService.getInstance();
                String id = sportService.getKey();
                SportService.getInstance().create(new Sport(id,"fill","fill"), id, null);*/
            }
        });
    }

}
