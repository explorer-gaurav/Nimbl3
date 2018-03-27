package com.gauravsaluja.nimbl3.mvp.model;

import com.gauravsaluja.nimbl3.network.CommunicationLink;
import com.gauravsaluja.nimbl3.network.NimblService;
import com.gauravsaluja.nimbl3.network.response.Survey;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveysModel implements ISurveysModel {

    @Override
    public Observable<List<Survey>> resultSurveys(int page, int per_page, String access_token) {
        return CommunicationLink.actionGetSurveys(page, per_page, access_token);
    }
}