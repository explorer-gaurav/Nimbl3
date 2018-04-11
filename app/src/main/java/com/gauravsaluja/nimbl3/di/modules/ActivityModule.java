package com.gauravsaluja.nimbl3.di.modules;

import android.app.Activity;

import com.gauravsaluja.nimbl3.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return activity;
    }
}