package com.gauravsaluja.nimbl3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.gauravsaluja.nimbl3.fragments.SurveyItemFragment;
import com.gauravsaluja.nimbl3.network.response.Survey;

import java.util.List;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class SurveyAdapter extends FragmentStatePagerAdapter {

    private List<Survey> surveys;
    private static SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public SurveyAdapter(FragmentManager fm, List<Survey> surveys) {
        super(fm);
        this.surveys = surveys;
    }

    @Override
    public Fragment getItem(int position) {
        return SurveyItemFragment.newInstance(surveys.get(position));
    }

    @Override
    public int getCount() {
        return surveys.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
}