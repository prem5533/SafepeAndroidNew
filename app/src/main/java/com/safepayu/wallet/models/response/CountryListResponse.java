package com.safepayu.wallet.models.response;

import java.util.List;

public class CountryListResponse {

    /**
     * status : true
     * message : Country List
     * data : [{"country_id":1,"sortname":"AF","name":"Afghanistan","phonecode":93}]
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
         * country_id : 1
         * sortname : AF
         * name : Afghanistan
         * phonecode : 93
         */

        private int country_id;
        private String sortname;
        private String name;
        private int phonecode;

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getSortname() {
            return sortname;
        }

        public void setSortname(String sortname) {
            this.sortname = sortname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(int phonecode) {
            this.phonecode = phonecode;
        }
    }
}
