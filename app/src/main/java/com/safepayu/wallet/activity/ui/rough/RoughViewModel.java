package com.safepayu.wallet.activity.ui.rough;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoughViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoughViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}