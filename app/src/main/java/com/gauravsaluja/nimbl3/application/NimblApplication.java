package com.gauravsaluja.nimbl3.application;

import android.app.Application;
import android.content.Context;

import com.gauravsaluja.nimbl3.di.components.AppComponent;
import com.gauravsaluja.nimbl3.di.components.DaggerAppComponent;
import com.gauravsaluja.nimbl3.di.modules.AppModule;
import com.gauravsaluja.nimbl3.di.modules.NetworkModule;
import com.gauravsaluja.nimbl3.utils.Constants;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class NimblApplication extends Application {

    private static AppComponent appComponent;
    private static Object objectLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AppComponent getAppComponent(Context context) {
        NimblApplication nimblApplication = (NimblApplication) context.getApplicationContext();
        if (appComponent == null)
            synchronized (objectLock) {
                if (appComponent == null) {
                    appComponent = DaggerAppComponent.builder()
                            .appModule(nimblApplication.getApplicationModule())
                            .networkModule(nimblApplication.getNetworkModule())
                            .build();
                }
            }
        return appComponent;
    }

    private AppModule getApplicationModule() {
        return new AppModule(this);
    }

    private NetworkModule getNetworkModule() {
        return new NetworkModule(Constants.API_BASE_URL);
    }
}