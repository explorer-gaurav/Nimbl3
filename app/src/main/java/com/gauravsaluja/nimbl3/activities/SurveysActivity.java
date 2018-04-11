package com.gauravsaluja.nimbl3.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gauravsaluja.nimbl3.R;
import com.gauravsaluja.nimbl3.baseclasses.BaseActivity;
import com.gauravsaluja.nimbl3.customviews.EndDrawerToggle;
import com.gauravsaluja.nimbl3.fragments.SurveysFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveysActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.action_refresh)
    public ImageView refresh;

    @BindView(R.id.toolbar_survey)
    public Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;

    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    private EndDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new EndDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(SurveysActivity.this);
        initFragment();
    }

    @Optional
    @OnClick(R.id.action_refresh)
    public void initFragment() {
        Fragment fragment = SurveysFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.survey_container, fragment, SurveysFragment.TAG_FRAGMENT).commit();
    }

    @Optional
    @OnClick(R.id.action_navigate)
    public void navigate() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}