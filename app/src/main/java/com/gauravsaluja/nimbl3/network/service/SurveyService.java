package com.gauravsaluja.nimbl3.network.service;

import com.gauravsaluja.nimbl3.di.modules.NetworkModule;
import com.gauravsaluja.nimbl3.network.api.SurveyApi;
import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Gaurav Saluja on 27-Mar-18.
 */

public class SurveyService {
    private SurveyApi surveyApi;

    @Inject
    public SurveyService(@Named(NetworkModule.NIMBL3) Retrofit restAdapter) {
        surveyApi = restAdapter.create(SurveyApi.class);
    }

    public Observable<List<Survey>> resultAllSurveys(String access_token) {
        return surveyApi.getAllSurveys(access_token);
    }

    public Observable<List<Survey>> resultSurveys(int page, int per_page, String access_token) {
        return surveyApi.getSurveys(page, per_page, access_token);
    }

    public Observable<Token> resultToken(TokenBody tokenBody) {
        return surveyApi.getToken(tokenBody);
    }
}