package com.yordy.ecoresi.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import com.andreabaccega.widget.FormEditText;
import com.yordy.ecoresi.R;

public class CustomFontFormEditText extends FormEditText {
    public CustomFontFormEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomFontFormEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomFontFormEditText(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
            setFont(a.getString(0));
            a.recycle();
        }
    }

    public void setFont(String fontName) {
        if (fontName != null) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName));
        }
    }
}
