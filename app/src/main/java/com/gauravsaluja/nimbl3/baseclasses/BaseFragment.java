package com.gauravsaluja.nimbl3.baseclasses;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gauravsaluja.nimbl3.application.NimblApplication;
import com.gauravsaluja.nimbl3.di.components.ConfigurationPersistComponent;
import com.gauravsaluja.nimbl3.di.components.DaggerConfigurationPersistComponent;
import com.gauravsaluja.nimbl3.di.components.FragmentComponent;
import com.gauravsaluja.nimbl3.di.modules.FragmentModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class BaseFragment extends Fragment {

    private Context mContext;
    public Unbinder unbinder;

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final Map<Long, ConfigurationPersistComponent> sComponentsMap = new HashMap<>();
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private FragmentComponent mFragmentComponent;
    private long mFragmentId;

    /**
     * Gets context.
     *
     * @return the context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Sets context.
     *
     * @param mContext the m context
     */
    protected void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentId = savedInstanceState != null ? savedInstanceState.getLong(KEY_FRAGMENT_ID) : NEXT_ID.getAndIncrement();
        ConfigurationPersistComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mFragmentId)) {
            configPersistentComponent = DaggerConfigurationPersistComponent.builder()
                    .appComponent(NimblApplication.getAppComponent(getActivity()))
                    .build();

            sComponentsMap.put(mFragmentId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsMap.get(mFragmentId);
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));
        mFragmentComponent.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        if (!getActivity().isChangingConfigurations()) {
            sComponentsMap.remove(mFragmentId);
        }

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroyView();
    }

    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }
}