package com.test.sporttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void clickDataEnter(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void clickDataAnalysis(View view) {
        startActivity(new Intent(this, AnalysisDataActivity.class));
    }
}
