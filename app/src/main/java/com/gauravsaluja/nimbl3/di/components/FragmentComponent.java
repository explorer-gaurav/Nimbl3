package com.gauravsaluja.nimbl3.di.components;

import com.gauravsaluja.nimbl3.baseclasses.BaseFragment;
import com.gauravsaluja.nimbl3.di.modules.FragmentModule;
import com.gauravsaluja.nimbl3.di.scope.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@FragmentScope
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(BaseFragment fragment);
}