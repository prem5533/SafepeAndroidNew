package com.safepayu.wallet.models.response;

import java.util.List;

public class StateListResponse {

    /**
     * status : true
     * message : State List
     * data : [{"state_id":1,"name":"Andaman and Nicobar Islands","country_id":101,"stateCode":2}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * state_id : 1
         * name : Andaman and Nicobar Islands
         * country_id : 101
         * stateCode : 2
         */

        private int state_id;
        private String name;
        private int country_id;
        private int stateCode;

        public int getState_id() {
            return state_id;
        }

        public void setState_id(int state_id) {
            this.state_id = state_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public int getStateCode() {
            return stateCode;
        }

        public void setStateCode(int stateCode) {
            this.stateCode = stateCode;
        }
    }
}
