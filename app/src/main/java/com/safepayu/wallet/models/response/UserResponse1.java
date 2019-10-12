package com.safepayu.wallet.models.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse1 extends BaseResponse1   {
    @SerializedName("user")
    User user;
    @SerializedName("referral_user")
    ReferralUser referralUser;

    public UserResponse1() {
    }

    public UserResponse1(User user, ReferralUser referralUser) {
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
