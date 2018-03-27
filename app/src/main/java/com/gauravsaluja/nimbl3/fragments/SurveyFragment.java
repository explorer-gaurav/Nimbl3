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

public class SurveyFragment extends Fragment implements SurveyContract.View {

    public static String TAG_FRAGMENT = SurveyFragment.class.getSimpleName();

    @BindView(R.id.progress_parent)
    public View progressParent;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    @BindView(R.id.progress_text)
    public TextView progressText;

    @BindView(R.id.survey_pager)
    public VerticalViewPager mViewPager;

    @BindView(R.id.indicator_surveys)
    public CirclePageIndicator mIndicator;

    private Unbinder unbinder;
    private PagerAdapter mPagerAdapter;

    @Inject
    protected SurveyPresenter surveyPresenter;

    public static SurveyFragment newInstance() {
        SurveyFragment fragment = new SurveyFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        NimblApplication.getAppComponent(getActivity()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
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
        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroy();
    }

    @Override
    public void onErrorGetToken() {

    }

    @Override
    public void onErrorGetSurveys() {

    }

    @Override
    public void onSuccessGetToken(Token token) {
        // on success of get token, fetch surveys using the retrieved access token
        surveyPresenter.fetchSurveys(1, 10, token.getAccessToken());
    }

    @Override
    public void onSuccessGetSurveys(List<Survey> surveys) {

        // setup the view pager and indicator based on survey response
        mPagerAdapter = new SurveyAdapter(getFragmentManager(), surveys);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOrientation(LinearLayout.VERTICAL);
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
}