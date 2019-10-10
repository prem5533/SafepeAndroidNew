package com.safepayu.wallet.models.response;

public class SaveAddressResponse {

    /**
     * status : true
     * message : Address saved successfully !
     * data : {"user_id":"1","location":"Varanasi","state":"UP","city":"Varanasi","country":"india","pin":"221311","updated_at":"2019-09-26 03:23:48","created_at":"2019-09-26 03:23:48","id":1}
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
        /**
         * user_id : 1
         * location : Varanasi
         * state : UP
         * city : Varanasi
         * country : india
         * pin : 221311
         * updated_at : 2019-09-26 03:23:48
         * created_at : 2019-09-26 03:23:48
         * id : 1
         */

        private String user_id;
        private String location;
        private String state;
        private String city;
        private String country;
        private String pin;
        private String updated_at;
        private String created_at;
        private int id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
