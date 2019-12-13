package com.safepayu.wallet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpRequest {
    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("type")
    @Expose
    String type;
}
