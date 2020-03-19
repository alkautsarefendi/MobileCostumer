package com.fortuna.android.mobilecustomer.button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Stuarez on 12/1/2016.
 */
@SuppressWarnings("ALL")
public class ButtonPlus extends Button {

    public ButtonPlus(Context context) {
        super(context);
    }

    public ButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public ButtonPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }
}
