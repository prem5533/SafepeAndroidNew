package com.safepayu.wallet.custom_view;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
public class FontCache {
    public static final String FA_FONT_REGULAR = "fa-regular-400.ttf";
    public static final String FA_FONT_SOLID = "fa-solid-900.ttf";
    public static final String FA_FONT_BRANDS = "fa-brands-400.ttf";
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }
}
