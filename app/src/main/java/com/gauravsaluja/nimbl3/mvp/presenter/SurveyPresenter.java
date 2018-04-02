package com.gauravsaluja.nimbl3.mvp.presenter;

import com.gauravsaluja.nimbl3.R;
import com.gauravsaluja.nimbl3.application.NimblApplication;
import com.gauravsaluja.nimbl3.baseclasses.BasePresenter;
import com.gauravsaluja.nimbl3.mvp.contract.SurveyContract;
import com.gauravsaluja.nimbl3.network.service.SurveyService;
import com.gauravsaluja.nimbl3.network.UseCaseObserver;
import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;
import com.gauravsaluja.nimbl3.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveyPresenter extends BasePresenter<SurveyContract.View> implements SurveyContract.Actions {

    private DisposableObserver tokenObserver;
    private DisposableObserver surveysObserver;

    @Inject
    public SurveyService surveyService;

    public SurveyPresenter() {

    }

    @Override
    public void start() {
        NimblApplication.getAppComponent(NimblApplication.getContext()).inject(this);
    }

    // observer for getting access token
    private DisposableObserver<Token> createTokenObserver() {
        disposeTokenObserver();

        return new UseCaseObserver<Token>() {
            @Override
            public void onError(Throwable exception) {
                view.onErrorGetToken();
            }

            @Override
            public void onNext(Token token) {
                view.hideProgressBar();
                view.onSuccessGetToken(token);
            }

            @Override
            public void onComplete() {
                view.onCompleteGetToken();
            }
        };
    }

    // observer for getting survey list
    private DisposableObserver<List<Survey>> createSurveysObserver() {
        disposeSurveysObserver();

        return new UseCaseObserver<List<Survey>>() {
            @Override
            public void onError(Throwable exception) {
                view.onErrorGetSurveys();
            }

            @Override
            public void onNext(List<Survey> surveys) {
                view.onSuccessGetSurveys(surveys);
            }

            @Override
            public void onComplete() {
                view.onCompleteGetSurveys();
                view.hideProgressBar();
            }
        };
    }

    @Override
    public void generateToken() {
        view.showProgressBar(R.string.generating_token);

        // generate body with hardcoded parameters
        TokenBody tokenBody = new TokenBody();
        tokenBody.setGrantType(Constants.VALUE_GRANT_TYPE);
        tokenBody.setUsername(Constants.VALUE_USERNAME);
        tokenBody.setPassword(Constants.VALUE_PASSWORD);

        // create an observer and make the call to the model
        tokenObserver = createTokenObserver();
        try {
            surveyService.resultToken(tokenBody)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tokenObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fetchSurveys(int page, int per_page, String access_token) {
        view.showProgressBar(R.string.fetching_surveys);

        // create an observer and make the call to the model
        surveysObserver = createSurveysObserver();
        try {
            /*surveyService.resultSurveys(page, per_page, access_token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(surveysObserver);*/

            surveyService.resultAllSurveys(access_token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(surveysObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
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