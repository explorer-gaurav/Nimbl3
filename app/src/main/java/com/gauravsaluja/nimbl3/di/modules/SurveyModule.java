package com.gauravsaluja.nimbl3.di.modules;

import com.gauravsaluja.nimbl3.mvp.model.ISurveysModel;
import com.gauravsaluja.nimbl3.mvp.model.ITokenModel;
import com.gauravsaluja.nimbl3.mvp.presenter.SurveyPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

@Module
public class SurveyModule {

    private ITokenModel mTokenModel;
    private ISurveysModel mSurveysModel;
    private SurveyPresenter mSurveyPresenter;

    public SurveyModule(ITokenModel tokenModel, ISurveysModel surveysModel) {
        mTokenModel = tokenModel;
        mSurveysModel = surveysModel;
        mSurveyPresenter = new SurveyPresenter(mTokenModel, mSurveysModel);
    }

    @Provides
    @Singleton
    public ITokenModel provideTokenModel() {
        return mTokenModel;
    }

    @Provides
    @Singleton
    public ISurveysModel provideSurveyModel() {
        return mSurveysModel;
    }

    @Provides
    @Singleton
    public SurveyPresenter provideSurveyPresenter() {
        return mSurveyPresenter;
    }
}