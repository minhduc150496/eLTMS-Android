package com.project.xetnghiem.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.xetnghiem.R;

public class ResultHistoryActivity extends BaseActivity {

    @Override
    protected int getLayoutView() {
        return R.layout.activity_result_history;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getMainTitle() {
        return "Kết quả xét nghiệm";
    }

    @Override
    public void bindView() {

    }

    @Override
    public void updateUIData(Object obj) {

    }
}
