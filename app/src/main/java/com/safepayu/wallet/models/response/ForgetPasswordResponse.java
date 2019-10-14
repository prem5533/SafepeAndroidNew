package com.safepayu.wallet.models.response;

import java.util.List;

public class ForgetPasswordResponse {

    /**
     * status : false
     * message : The given data was invalid.
     * data : {"password":["The password must be at least 4 characters."],"password_confirmation":["The password confirmation must be at least 4 characters."]}
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
        private List<String> password;
        private List<String> password_confirmation;

        public List<String> getPassword() {
            return password;
        }

        public void setPassword(List<String> password) {
            this.password = password;
        }

        public List<String> getPassword_confirmation() {
            return password_confirmation;
        }

        public void setPassword_confirmation(List<String> password_confirmation) {
            this.password_confirmation = password_confirmation;
        }
    }
}
