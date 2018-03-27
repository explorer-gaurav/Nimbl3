package com.gauravsaluja.nimbl3.di.modules;

import com.gauravsaluja.nimbl3.mvp.presenter.SurveyPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@Module
public class SurveyModule {

    private SurveyPresenter mSurveyPresenter;

    public SurveyModule() {
        mSurveyPresenter = new SurveyPresenter();
    }

    @Provides
    @Singleton
    public SurveyPresenter provideSurveyPresenter() {
        return mSurveyPresenter;
    }
}