package com.gauravsaluja.nimbl3.mvp.model;

import com.gauravsaluja.nimbl3.network.response.Survey;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public interface ISurveysModel {
    Observable<List<Survey>> resultSurveys(int page, int per_page, String access_token);
}