package com.gauravsaluja.nimbl3.di.components;

import com.gauravsaluja.nimbl3.di.modules.ActivityModule;
import com.gauravsaluja.nimbl3.di.modules.FragmentModule;
import com.gauravsaluja.nimbl3.di.scope.ConfigurationPersistScope;

import dagger.Component;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@ConfigurationPersistScope
@Component(dependencies = AppComponent.class)
public interface ConfigurationPersistComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}