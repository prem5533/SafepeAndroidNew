package com.safepayu.wallet.custom_view.passcodeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;

import com.safepayu.wallet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
//https://github.com/aabhasr1/OtpView
public class PasscodeTextView extends FrameLayout {

    private static final int DEFAULT_LENGTH = 4;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_WIDTH = 48;
    private static final int DEFAULT_SPACE = -1;
    private static final int DEFAULT_SPACE_LEFT = 4;
    private static final int DEFAULT_SPACE_RIGHT = 4;
    private static final int DEFAULT_SPACE_TOP = 4;
    private static final int DEFAULT_SPACE_BOTTOM = 4;

    private static final String PATTERN = "[1234567890]*";

    private Context context;
    private List<ItemView> itemViews;
    private PassCodeChildEditText passCodeChildEditText;
    private PassCodeListener passCodeListener;

    private int length;

    public PasscodeTextView(@NonNull Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public PasscodeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public PasscodeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray styles = getContext().obtainStyledAttributes(attrs, R.styleable.PassCodeTextView);
        styleEditTexts(styles, attrs);
        styles.recycle();
    }

    private void styleEditTexts(TypedArray styles, AttributeSet attrs) {
        length = styles.getInt(R.styleable.PassCodeTextView_length, DEFAULT_LENGTH);
        generateViews(styles, attrs);
    }

    private void generateViews(TypedArray styles, AttributeSet attrs) {
        itemViews = new ArrayList<>();
        if (length > 0) {
            String otp = styles.getString(R.styleable.PassCodeTextView_pass_code);
            Boolean focusable = styles.getBoolean(R.styleable.PassCodeTextView_pass_code_focusable, false);
            int width = (int) styles.getDimension(R.styleable.PassCodeTextView_width, Utils.getPixels(context, DEFAULT_WIDTH));
            int height = (int) styles.getDimension(R.styleable.PassCodeTextView_height, Utils.getPixels(context, DEFAULT_HEIGHT));
            int space = (int) styles.getDimension(R.styleable.PassCodeTextView_box_margin, Utils.getPixels(context, DEFAULT_SPACE));
            int spaceLeft = (int) styles.getDimension(R.styleable.PassCodeTextView_box_margin_left, Utils.getPixels(context, DEFAULT_SPACE_LEFT));
            int spaceRight = (int) styles.getDimension(R.styleable.PassCodeTextView_box_margin_right, Utils.getPixels(context, DEFAULT_SPACE_RIGHT));
            int spaceTop = (int) styles.getDimension(R.styleable.PassCodeTextView_box_margin_top, Utils.getPixels(context, DEFAULT_SPACE_TOP));
            int spaceBottom = (int) styles.getDimension(R.styleable.PassCodeTextView_box_margin_bottom, Utils.getPixels(context, DEFAULT_SPACE_BOTTOM));
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(width, height);
            if (space > 0) {
                params.setMargins(space, space, space, space);
            } else {
                params.setMargins(spaceLeft, spaceTop, spaceRight, spaceBottom);
            }

            LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editTextLayoutParams.gravity = Gravity.CENTER;
            passCodeChildEditText = new PassCodeChildEditText(context);
            passCodeChildEditText.setFilters(new InputFilter[]{getFilter()
                    , new InputFilter.LengthFilter(length)});
            passCodeChildEditText.setFocusable(focusable);
            setTextWatcher(passCodeChildEditText);
            addView(passCodeChildEditText, editTextLayoutParams);


            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = new LinearLayout(context);

            addView(linearLayout, linearLayoutParams);

            for (int i = 0; i < length; i++) {
                ItemView itemView = new ItemView(context, attrs);
                itemView.setViewState(ItemView.INACTIVE);
                linearLayout.addView(itemView, i, params);
                itemViews.add(itemView);
            }
            if (otp != null) {
                setPassCode(otp);
            } else {
                setPassCode("");
            }
        } else {
            throw new IllegalStateException("Please specify the length of the otp view");
        }
    }

    private void setTextWatcher(PassCodeChildEditText passCodeChildEditText) {
        passCodeChildEditText.addTextChangedListener(new TextWatcher() {
            /**
             * @param s
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /**
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passCodeListener != null) {
                    passCodeListener.onInteractionListener();
                    if (s.length() == length) {
                        passCodeListener.onOTPComplete(s.toString());
                    }
                }
                setOTP(s);
                setFocus(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setFocus(int length) {
        for (int i = 0; i < itemViews.size(); i++) {
            if (i == length) {
                itemViews.get(i).setViewState(ItemView.ACTIVE);
            } else {
                itemViews.get(i).setViewState(ItemView.INACTIVE);
            }
        }
        if (length == itemViews.size()) {
            itemViews.get(itemViews.size() - 1).setViewState(ItemView.ACTIVE);
        }
    }

    public void setOTP(CharSequence s) {
        for (int i = 0; i < itemViews.size(); i++) {
            if (i < s.length()) {
                itemViews.get(i).setText(String.valueOf(s.charAt(i)));
            } else {
                itemViews.get(i).setText("");
            }
        }
    }

    public PassCodeListener getOtpListener() {
        return passCodeListener;
    }

    public void setOtpListener(PassCodeListener passCodeListener) {
        this.passCodeListener = passCodeListener;
    }

    private InputFilter getFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile(
                            PATTERN)
                            .matcher(String.valueOf(source.charAt(i)))
                            .matches()) {
                        return "";
                    }
                }
                return null;
            }
        };
    }

    public void requestFocusOTP() {
        if (passCodeChildEditText != null) {
            passCodeChildEditText.requestFocus();
        }
    }

    public void showError() {
        if (itemViews != null) {
            for (ItemView itemView : itemViews) {
                itemView.setViewState(ItemView.ERROR);
            }
        }
    }

    public void resetState() {
        setFocus(getPassCode().length());
    }

    public void showSuccess() {
        if (itemViews != null) {
            for (ItemView itemView : itemViews) {
                itemView.setViewState(ItemView.SUCCESS);
            }
        }
    }

    public void setPassCode(String otp) {
        passCodeChildEditText.setText(otp);
    }

    public String getPassCode() {
        if (passCodeChildEditText != null && passCodeChildEditText.getText() != null)
            return passCodeChildEditText.getText().toString();
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
        passCodeChildEditText.setOnTouchListener(l);
    }
}