package com.gauravsaluja.nimbl3.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gauravsaluja.nimbl3.R;
import com.gauravsaluja.nimbl3.baseclasses.BaseActivity;
import com.gauravsaluja.nimbl3.customviews.EndDrawerToggle;
import com.gauravsaluja.nimbl3.fragments.SurveyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveyActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable
    @BindView(R.id.app_title_container)
    public RelativeLayout titleContainer;

    @Nullable
    @BindView(R.id.action_refresh)
    public ImageView refresh;

    @Nullable
    @BindView(R.id.toolbar_title)
    public TextView toolbarTitle;

    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Nullable
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;

    @Nullable
    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    private EndDrawerToggle toggle;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new EndDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        initFragment();
    }

    @Optional
    @OnClick(R.id.action_refresh)
    public void initFragment() {
        Fragment fragment = SurveyFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.survey_container, fragment, SurveyFragment.TAG_FRAGMENT).commit();
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

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}