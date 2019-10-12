package com.safepayu.wallet.models.response;

import java.util.List;

public class BaseResponse1 {


    /**
     * status : false
     * message : The given data was invalid.
     * data : {"email":["The email has already been taken."],"mobile":["The mobile has already been taken."]}
     */

    private boolean status;
    private String message;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> email;
        private List<String> mobile;

        public List<String> getEmail() {
            return email;
        }

        public void setEmail(List<String> email) {
            this.email = email;
        }

        public List<String> getMobile() {
            return mobile;
        }

        public void setMobile(List<String> mobile) {
            this.mobile = mobile;
        }
    }
}
