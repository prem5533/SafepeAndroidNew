package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class AddressUserResponse {


    /**
     * status : true
     * message : List of customer Address.
     * addressess : [{"id":11,"user_id":11,"name":"Shaun Marsh","mobile":"89896589888","pincode":"WV3 0DS","flat":"1","area":"1 Oaklands Road","landmark":"65","city":"Wolverhampton","state":"London","country":"England","latitude":"52.57592","longitude":"-2.138813","type":"other","update_token":null,"created_at":"2019-11-08 07:54:14","updated_at":"2019-11-08 07:54:14","distance":"3,634.58"}]
     */

    private boolean status;
    private String message;
    private List<AddressessBean> addressess;

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

    public List<AddressessBean> getAddressess() {
        return addressess;
    }

    public void setAddressess(List<AddressessBean> addressess) {
        this.addressess = addressess;
    }

    public static class AddressessBean {
        /**
         * id : 11
         * user_id : 11
         * name : Shaun Marsh
         * mobile : 89896589888
         * pincode : WV3 0DS
         * flat : 1
         * area : 1 Oaklands Road
         * landmark : 65
         * city : Wolverhampton
         * state : London
         * country : England
         * latitude : 52.57592
         * longitude : -2.138813
         * type : other
         * update_token : null
         * created_at : 2019-11-08 07:54:14
         * updated_at : 2019-11-08 07:54:14
         * distance : 3,634.58
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
        private Object update_token;
        private String created_at;
        private String updated_at;
        private String distance;

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

        public Object getUpdate_token() {
            return update_token;
        }

        public void setUpdate_token(Object update_token) {
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
