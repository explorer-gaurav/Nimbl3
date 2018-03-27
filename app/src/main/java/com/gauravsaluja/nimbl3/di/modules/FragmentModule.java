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

    private Fragment mFragment;
    private Activity mActivity;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
    }

    @Provides
    @FragmentScope
    public Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return mActivity;
    }
}