package com.gauravsaluja.nimbl3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gauravsaluja.nimbl3.R;
import com.gauravsaluja.nimbl3.activities.TakeSurveyActivity;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveyItemFragment extends Fragment implements View.OnClickListener {

    private Unbinder unbinder;
    private static String KEY_SURVEY_DATA = "survey";

    private Survey surveyData;

    @BindView(R.id.item_cover)
    public ImageView itemCover;

    @BindView(R.id.item_title)
    public TextView itemTitle;

    @BindView(R.id.item_description)
    public TextView itemDescription;

    @BindView(R.id.item_action)
    public TextView itemAction;

    public static SurveyItemFragment newInstance(Survey survey) {
        SurveyItemFragment fragment = new SurveyItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SURVEY_DATA, survey);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if (b != null && b.containsKey(KEY_SURVEY_DATA)) {
            surveyData = b.getParcelable(KEY_SURVEY_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.survey_item, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        // load image in itemCover
        Picasso.with(getContext())
                .load(surveyData.getCoverImageUrl() + Constants.IDENTIFIER_HIGH_RES_IMAGE)
                .error(R.color.placeholder)
                .into(itemCover);

        // set title and description
        itemTitle.setText(surveyData.getTitle());
        itemDescription.setText(surveyData.getDescription());

        itemAction.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_action) {
            TakeSurveyActivity.openTakeSurveyActivity(getContext());
        }
    }
}