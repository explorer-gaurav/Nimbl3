package com.gauravsaluja.nimbl3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gauravsaluja.nimbl3.R;
import com.gauravsaluja.nimbl3.adapters.SurveyAdapter;
import com.gauravsaluja.nimbl3.application.NimblApplication;
import com.gauravsaluja.nimbl3.customviews.CirclePageIndicator;
import com.gauravsaluja.nimbl3.customviews.VerticalViewPager;
import com.gauravsaluja.nimbl3.mvp.contract.SurveyContract;
import com.gauravsaluja.nimbl3.mvp.presenter.SurveyPresenter;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveysFragment extends Fragment implements SurveyContract.View, View.OnClickListener {

    public static String TAG_FRAGMENT = SurveysFragment.class.getSimpleName();

    @BindView(R.id.progress_parent)
    public View progressParent;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    @BindView(R.id.progress_text)
    public TextView progressText;

    @BindView(R.id.survey_pager)
    public VerticalViewPager viewPager;

    @BindView(R.id.indicator_surveys)
    public CirclePageIndicator indicator;

    @BindView(R.id.action_retry)
    public TextView actionRetry;

    private Unbinder unbinder;
    private PagerAdapter pagerAdapter;

    @Inject
    protected SurveyPresenter surveyPresenter;

    public static SurveysFragment newInstance() {
        SurveysFragment fragment = new SurveysFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        NimblApplication.getAppComponent(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        actionRetry.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        // set the view to presenter and start generate token request
        surveyPresenter.setView(this);
        surveyPresenter.setContext(getActivity());
        surveyPresenter.start();
        surveyPresenter.generateToken();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onErrorGetToken() {
        showProgressBar(R.string.error_generating_token);
        showRetryButton();
    }

    @Override
    public void onErrorGetSurveys() {
        showProgressBar(R.string.error_fetching_surveys);
        showRetryButton();
    }

    @Override
    public void onSuccessGetToken(Token token) {

        hideProgressBar();
        hideRetryButton();

        // on success of get token, fetch surveys using the retrieved access token
        surveyPresenter.fetchSurveys(1, 10, token.getAccessToken());
    }

    @Override
    public void onSuccessGetSurveys(List<Survey> surveys) {

        hideProgressBar();
        hideRetryButton();

        // setup the view pager and indicator based on survey response
        pagerAdapter = new SurveyAdapter(getFragmentManager(), surveys);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
        indicator.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void onCompleteGetToken() {

    }

    @Override
    public void onCompleteGetSurveys() {

    }

    @Override
    public void showProgressBar(int progressTextId) {
        progressParent.setVisibility(View.VISIBLE);
        progressText.setText(progressTextId);
    }

    @Override
    public void hideProgressBar() {
        progressParent.setVisibility(View.GONE);
    }

    @Override
    public void showRetryButton() {
        actionRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryButton() {
        actionRetry.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_retry:
                surveyPresenter.generateToken();
                break;
        }
    }
}