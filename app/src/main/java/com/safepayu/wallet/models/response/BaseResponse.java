package com.safepayu.wallet.models.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    Boolean status;
    @SerializedName("status_code")
    int statusCode;
    String message;
    String success;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
