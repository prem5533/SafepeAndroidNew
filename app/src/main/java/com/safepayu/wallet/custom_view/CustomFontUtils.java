package com.safepayu.wallet.custom_view;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.safepayu.wallet.R;

public class CustomFontUtils {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_fontCustom);

        // check if a special textStyle was used (e.g. extra bold)
        int textStyle = attributeArray.getInt(R.styleable.CustomFontTextView_textStyleCustom, 0);

        // if nothing extra was used, fall back to regular android:textStyle parameter
        if (textStyle == 0) {
            textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        }

        Typeface customFont = selectTypeface(context, fontName, textStyle);
        customFontTextView.setTypeface(customFont);

        attributeArray.recycle();
    }

    private static Typeface selectTypeface(Context context, String fontName, int textStyle) {
        if (fontName != null) {
            if (fontName.contentEquals(context.getResources().getString(R.string.Roboto))) {
            /*
            information about the TextView textStyle:
            http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
            */
                switch (textStyle) {
                    case Typeface.BOLD:
                        return FontCache.getTypeface("fonts/Roboto-Bold.ttf", context);
                    case Typeface.NORMAL:
                        return FontCache.getTypeface("fonts/Roboto-Regular.ttf", context);
                    case Typeface.ITALIC:
                        return FontCache.getTypeface("fonts/Roboto-BlackItalic.ttf", context);
                    default:
                        return FontCache.getTypeface("fonts/Roboto-Regular.ttf", context);
                }
            } else {
                return null;
            }
        } else {
            return FontCache.getTypeface("fonts/Roboto_Regular.ttf", context);
        }
    }
}