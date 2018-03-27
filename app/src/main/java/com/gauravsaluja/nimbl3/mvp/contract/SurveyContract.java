package com.gauravsaluja.nimbl3.mvp.contract;

import com.gauravsaluja.nimbl3.baseclasses.BaseView;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;

import java.util.List;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveyContract {

    // functions available at View layer
    public interface View extends BaseView {
        void onErrorGetToken();

        void onErrorGetSurveys();

        void onSuccessGetToken(Token token);

        void onSuccessGetSurveys(List<Survey> surveys);

        void onCompleteGetToken();

        void onCompleteGetSurveys();

        void showProgressBar(int progressTextId);

        void hideProgressBar();
    }

    // functions available at Presenter layer
    public interface Actions {
        void generateToken();

        void fetchSurveys(int page, int per_page, String access_token);
    }
}