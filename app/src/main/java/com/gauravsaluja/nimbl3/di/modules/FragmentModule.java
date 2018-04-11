package com.gauravsaluja.nimbl3.di.modules;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gauravsaluja.nimbl3.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@Module
public class FragmentModule {

    private Fragment fragment;
    private Activity activity;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
        activity = fragment.getActivity();
    }

    @Provides
    @FragmentScope
    public Fragment provideFragment() {
        return fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return activity;
    }
}