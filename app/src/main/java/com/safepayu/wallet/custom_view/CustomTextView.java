package com.safepayu.wallet.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.safepayu.wallet.R;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextView extends AppCompatTextView {
    private boolean isFaIcon, isFaSolidIcon;

    public CustomTextView(Context context) {
        super(context);

        CustomFontUtils.applyCustomFont(this, context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        CustomFontUtils.applyCustomFont(this, context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        CustomFontUtils.applyCustomFont(this, context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontTextView,
                0, 0);
        isFaSolidIcon = a.getBoolean(R.styleable.FontTextView_fa_solid_icon, false);
        isFaIcon = a.getBoolean(R.styleable.FontTextView_fa_brand_icon, false);
        init();
    }

    private void init() {
        if (isFaIcon)
            setTypeface(FontCache.getTypeface("fonts/fa-brands-400.ttf", getContext()));
        else if (isFaSolidIcon)
            setTypeface(FontCache.getTypeface("fonts/fa-solid-900.ttf", getContext()));
        else
            setTypeface(FontCache.getTypeface("fonts/fa-regular-400.ttf", getContext()));
    }

}
