package com.gauravsaluja.nimbl3.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gauravsaluja.nimbl3.R;
import com.gauravsaluja.nimbl3.baseclasses.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakeSurveyActivity extends BaseActivity {

    @BindView(R.id.toolbar_take_survey)
    public Toolbar toolbar;

    public static void openTakeSurveyActivity(Context context) {
        Intent intent = new Intent(context, TakeSurveyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_survey);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}