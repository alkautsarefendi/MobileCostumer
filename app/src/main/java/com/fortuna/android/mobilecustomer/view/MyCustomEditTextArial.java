package com.fortuna.android.mobilecustomer.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class MyCustomEditTextArial extends EditText {
    public MyCustomEditTextArial(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Arial.ttf"));
    }
}