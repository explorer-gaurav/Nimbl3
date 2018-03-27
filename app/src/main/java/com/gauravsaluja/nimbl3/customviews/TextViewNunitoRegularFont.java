package com.gauravsaluja.nimbl3.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.gauravsaluja.nimbl3.utils.Helpers;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class TextViewNunitoRegularFont extends AppCompatTextView {

    public TextViewNunitoRegularFont(Context context) {
        super(context);
        setCustomFont(context);
    }

    public TextViewNunitoRegularFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public TextViewNunitoRegularFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context);
    }

    private void setCustomFont(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(Helpers.getNunitoTypeFaceRegular(context));
        }
    }
}