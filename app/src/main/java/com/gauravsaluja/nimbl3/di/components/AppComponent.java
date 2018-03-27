package com.gauravsaluja.nimbl3.di.components;

import android.app.Application;

import com.gauravsaluja.nimbl3.di.modules.AppModule;
import com.gauravsaluja.nimbl3.di.modules.SurveyModule;
import com.gauravsaluja.nimbl3.fragments.SurveyFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@Singleton
@Component(modules = {AppModule.class, SurveyModule.class})
public interface AppComponent {

    Application provideApplication();

    void inject(SurveyFragment surveyFragment);
}