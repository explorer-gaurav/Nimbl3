package com.gauravsaluja.nimbl3.application;

import android.app.Application;
import android.content.Context;

import com.gauravsaluja.nimbl3.di.components.AppComponent;
import com.gauravsaluja.nimbl3.di.components.DaggerAppComponent;
import com.gauravsaluja.nimbl3.di.modules.AppModule;
import com.gauravsaluja.nimbl3.di.modules.NetworkModule;
import com.gauravsaluja.nimbl3.di.modules.SurveyModule;
import com.gauravsaluja.nimbl3.utils.Constants;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class NimblApplication extends Application {

    private static Context context;
    private static AppComponent sAppComponent;
    private static Object sObjectLock = new Object();

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NimblApplication.context = getApplicationContext();
    }

    public static AppComponent getAppComponent(Context context) {
        NimblApplication nimblApplication = (NimblApplication) context.getApplicationContext();
        if (sAppComponent == null)
            synchronized (sObjectLock) {
                if (sAppComponent == null) {
                    sAppComponent = DaggerAppComponent.builder()
                            .appModule(nimblApplication.getApplicationModule())
                            .networkModule(nimblApplication.getNetworkModule())
                            .surveyModule(nimblApplication.getSurveyModule())
                            .build();
                }
            }
        return sAppComponent;
    }

    private AppModule getApplicationModule() {
        return new AppModule(this);
    }

    private SurveyModule getSurveyModule() {
        return new SurveyModule();
    }

    private NetworkModule getNetworkModule() {
        return new NetworkModule(Constants.API_BASE_URL);
    }
}