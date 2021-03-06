package com.gauravsaluja.nimbl3.di.components;

import android.app.Application;

import com.gauravsaluja.nimbl3.di.modules.AppModule;
import com.gauravsaluja.nimbl3.di.modules.NetworkModule;
import com.gauravsaluja.nimbl3.fragments.SurveysFragment;
import com.gauravsaluja.nimbl3.network.service.SurveyService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    Application provideApplication();

    String provideBaseUrl();

    @Named(NetworkModule.NIMBL3)
    Retrofit provideRetrofitRestAdapter();

    SurveyService provideSurveyService();

    void inject(SurveysFragment surveysFragment);
}