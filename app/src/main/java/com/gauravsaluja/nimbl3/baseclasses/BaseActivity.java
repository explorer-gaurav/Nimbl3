package com.gauravsaluja.nimbl3.baseclasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.gauravsaluja.nimbl3.application.NimblApplication;
import com.gauravsaluja.nimbl3.di.components.ActivityComponent;
import com.gauravsaluja.nimbl3.di.components.ConfigurationPersistComponent;
import com.gauravsaluja.nimbl3.di.components.DaggerConfigurationPersistComponent;
import com.gauravsaluja.nimbl3.di.modules.ActivityModule;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static HashMap<Long, ConfigurationPersistComponent> componentsMap = new HashMap<>();
    private static AtomicLong nextId = new AtomicLong(0);
    private ActivityComponent activityComponent;
    private long activityId;

    private boolean activityStopped;
    private boolean activityDestroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID) : nextId.getAndIncrement();
        ConfigurationPersistComponent configurationPersistComponent;

        if (!componentsMap.containsKey(activityId)) {
            configurationPersistComponent = DaggerConfigurationPersistComponent.builder()
                    .appComponent(NimblApplication.getAppComponent(this))
                    .build();

            componentsMap.put(activityId, configurationPersistComponent);
        } else {
            configurationPersistComponent = componentsMap.get(activityId);
        }

        activityComponent = configurationPersistComponent.activityComponent(new ActivityModule(this));
        activityComponent.inject(this);
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    /**
     * Is activity destroyed.
     *
     * @return the boolean
     */
    public boolean isActivityDestroyed() {
        return activityDestroyed;
    }

    /**
     * Sets activity destroyed.
     *
     * @param activityDestroyed the activity destroyed
     */
    protected void setActivityDestroyed(boolean activityDestroyed) {
        this.activityDestroyed = activityDestroyed;
    }

    @Override

    protected void onStart() {
        super.onStart();
        activityDestroyed = false;
        activityStopped = false;
    }

    @Override
    protected void onStop() {
        activityStopped = true;
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, activityId);
    }

    @Override
    protected void onDestroy() {
        activityDestroyed = true;

        if (!isChangingConfigurations()) {
            componentsMap.remove(activityId);
        }
        super.onDestroy();
    }

    /**
     * Sets activity stopped.
     *
     * @param isActivityStopped the is a stopped
     */
    protected void setActivityStopped(boolean isActivityStopped) {
        this.activityStopped = isActivityStopped;
    }

    /**
     * Function to check that if Activity has already called on stop or not
     *
     * @return true if activity is stopped
     */
    protected boolean isActivityStopped() {
        return activityStopped;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}