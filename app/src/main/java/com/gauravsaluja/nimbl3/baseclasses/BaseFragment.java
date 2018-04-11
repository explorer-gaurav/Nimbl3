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

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class BaseFragment extends Fragment {

    private Context context;

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final Map<Long, ConfigurationPersistComponent> componentsMap = new HashMap<>();
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private FragmentComponent fragmentComponent;
    private long fragmentId;

    /**
     * Gets context.
     *
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Sets context.
     *
     * @param mContext the m context
     */
    protected void setContext(Context mContext) {
        this.context = mContext;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentId = savedInstanceState != null ? savedInstanceState.getLong(KEY_FRAGMENT_ID) : NEXT_ID.getAndIncrement();
        ConfigurationPersistComponent configPersistentComponent;
        if (!componentsMap.containsKey(fragmentId)) {
            configPersistentComponent = DaggerConfigurationPersistComponent.builder()
                    .appComponent(NimblApplication.getAppComponent(getActivity()))
                    .build();

            componentsMap.put(fragmentId, configPersistentComponent);
        } else {
            configPersistentComponent = componentsMap.get(fragmentId);
        }
        fragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));
        fragmentComponent.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (!getActivity().isChangingConfigurations()) {
            componentsMap.remove(fragmentId);
        }

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }
}