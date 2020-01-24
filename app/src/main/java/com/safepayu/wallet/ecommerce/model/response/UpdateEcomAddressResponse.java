package com.safepayu.wallet.ecommerce.model.response;

public class UpdateEcomAddressResponse {

    /**
     * status : true
     * message : Address updated Successfully
     * address : {"id":6,"user_id":4,"name":"","mobile":"","pincode":"110014","flat":"b 96","area":"","landmark":"Powerstation","city":"London","state":"","country":"England","latitude":"","longitude":"","type":"other","update_token":"","created_at":"2019-11-05 11:27:29","updated_at":"2020-01-24 09:12:10"}
     * address1 : b 96 London England 110014
     */

    private boolean status;
    private String message;
    private AddressBean address;
    private String address1;

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

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public static class AddressBean {
        /**
         * id : 6
         * user_id : 4
         * name :
         * mobile :
         * pincode : 110014
         * flat : b 96
         * area :
         * landmark : Powerstation
         * city : London
         * state :
         * country : England
         * latitude :
         * longitude :
         * type : other
         * update_token :
         * created_at : 2019-11-05 11:27:29
         * updated_at : 2020-01-24 09:12:10
         */

        private int id;
        private int user_id;
        private String name;
        private String mobile;
        private String pincode;
        private String flat;
        private String area;
        private String landmark;
        private String city;
        private String state;
        private String country;
        private String latitude;
        private String longitude;
        private String type;
        private String update_token;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getFlat() {
            return flat;
        }

        public void setFlat(String flat) {
            this.flat = flat;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdate_token() {
            return update_token;
        }

        public void setUpdate_token(String update_token) {
            this.update_token = update_token;
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
