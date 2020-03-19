package com.fortuna.android.mobilecustomer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Stuarez on 11/30/2016.
 */
@SuppressLint("AppCompatCustomView")
public class MyCustomTextViewArial extends TextView {
    public MyCustomTextViewArial(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Arial.ttf"));
    }
}
