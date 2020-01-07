package com.safepayu.wallet.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse {
    boolean status;
    @SerializedName("status_code")
    int statusCode;
    String message;
    String success;
    /**
     * data : {"mobile":["The mobile must be at least 10 characters."]}
     */

    private DataBean data;


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> mobile;

        public List<String> getMobile() {
            return mobile;
        }

        public void setMobile(List<String> mobile) {
            this.mobile = mobile;
        }
    }
}
