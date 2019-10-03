package com.safepayu.wallet.models.response;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserResponse extends BaseResponse   {
    @SerializedName("user")
    User user;
    @SerializedName("referral_user")
    ReferralUser referralUser;

    public UserResponse() {
    }

    public UserResponse(User user, ReferralUser referralUser) {
        this.user = user;
        this.referralUser = referralUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReferralUser getReferralUser() {
        return referralUser;
    }

    public void setReferralUser(ReferralUser referralUser) {
        this.referralUser = referralUser;
    }
}
