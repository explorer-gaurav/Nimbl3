package com.gauravsaluja.nimbl3.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class Helpers {

    // get Nunito Regular typeface
    public static Typeface getNunitoTypeFaceRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/nunito_regular.ttf");
    }

    // get Nunito Light typeface
    public static Typeface getNunitoTypeFaceLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/nunito_light.ttf");
    }
}