package com.gauravsaluja.nimbl3.mvp.presenter;

import com.gauravsaluja.nimbl3.R;
import com.gauravsaluja.nimbl3.baseclasses.BasePresenter;
import com.gauravsaluja.nimbl3.mvp.contract.SurveyContract;
import com.gauravsaluja.nimbl3.network.UseCaseObserver;
import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;
import com.gauravsaluja.nimbl3.network.service.SurveyService;
import com.gauravsaluja.nimbl3.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveyPresenter extends BasePresenter<SurveyContract.View> implements SurveyContract.Actions {

    private DisposableObserver tokenObserver;
    private DisposableObserver surveysObserver;

    @NonNull
    public SurveyService surveyService;
    @NonNull
    public Scheduler backgroundScheduler;
    @NonNull
    public Scheduler mainScheduler;

    @Inject
    public SurveyPresenter(SurveyService surveyService) {
        this.surveyService = surveyService;
        this.backgroundScheduler = Schedulers.newThread();
        this.mainScheduler = AndroidSchedulers.mainThread();
    }

    @Override
    public void start() {

    }

    // observer for getting access token
    private DisposableObserver<Token> createTokenObserver() {
        disposeTokenObserver();

        return new UseCaseObserver<Token>() {
            @Override
            public void onError(Throwable exception) {
                getView().onErrorGetToken();
            }

            @Override
            public void onNext(Token token) {
                getView().onSuccessGetToken(token);
            }

            @Override
            public void onComplete() {
                getView().onCompleteGetToken();
            }
        };
    }

    // observer for getting survey list
    private DisposableObserver<List<Survey>> createSurveysObserver() {
        disposeSurveysObserver();

        return new UseCaseObserver<List<Survey>>() {
            @Override
            public void onError(Throwable exception) {
                getView().onErrorGetSurveys();
            }

            @Override
            public void onNext(List<Survey> surveys) {
                getView().onSuccessGetSurveys(surveys);
            }

            @Override
            public void onComplete() {
                getView().onCompleteGetSurveys();
            }
        };
    }

    @Override
    public void generateToken() {
        getView().showProgressBar(R.string.generating_token);
        getView().hideRetryButton();

        // generate body with hardcoded parameters
        TokenBody tokenBody = new TokenBody(Constants.VALUE_GRANT_TYPE, Constants.VALUE_USERNAME, Constants.VALUE_PASSWORD);

        // create an observer and make the call to the model
        tokenObserver = createTokenObserver();
        surveyService.resultToken(tokenBody)
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(tokenObserver);
    }

    @Override
    public void fetchSurveys(int page, int per_page, String access_token) {
        getView().showProgressBar(R.string.fetching_surveys);
        getView().hideRetryButton();

        // create an observer and make the call to the model
        surveysObserver = createSurveysObserver();
        surveyService.resultSurveys(page, per_page, access_token)
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(surveysObserver);
    }

    // dispose the observer
    public void disposeTokenObserver() {
        if (tokenObserver != null && !tokenObserver.isDisposed()) {
            tokenObserver.dispose();
        }
    }

    // dispose the observer
    public void disposeSurveysObserver() {
        if (surveysObserver != null && !surveysObserver.isDisposed()) {
            surveysObserver.dispose();
        }
    }

    @Override
    public void onDestroy() {

        // before destroy, dispose all the observers
        disposeTokenObserver();
        disposeSurveysObserver();

        super.onDestroy();
    }
}