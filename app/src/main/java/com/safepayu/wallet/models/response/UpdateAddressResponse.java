package com.safepayu.wallet.models.response;

public class UpdateAddressResponse  {

    /**
     * status : true
     * message : Address Update successfully !
     * data : {"id":1,"user_id":"u8750110867","location":"Varanas","city":"Varanasi","state":"UP","country":"india","pin":"221311","created_at":"2019-10-02 08:12:45","updated_at":"2019-10-02 11:15:40"}
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
         * id : 1
         * user_id : u8750110867
         * location : Varanas
         * city : Varanasi
         * state : UP
         * country : india
         * pin : 221311
         * created_at : 2019-10-02 08:12:45
         * updated_at : 2019-10-02 11:15:40
         */

        private int id;
        private String user_id;
        private String location;
        private String city;
        private String state;
        private String country;
        private String pin;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
