package com.gauravsaluja.nimbl3.di.components;

import com.gauravsaluja.nimbl3.baseclasses.BaseActivity;
import com.gauravsaluja.nimbl3.di.modules.ActivityModule;
import com.gauravsaluja.nimbl3.di.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);
}