package com.safepayu.wallet.ecommerce.model.request;

public class SaveEcomAddressRequest {

    /**
     * post_code : 110014
     * flatno : B96
     * area : Sector 65
     * landmark : Power station
     * city : Noida
     * Pincode : 201301
     * country : India
     * type : home
     */

    private String post_code;
    private String flatno;
    private String area;
    private String landmark;
    private String city;
    private String Pincode;
    private String country;
    private String type;


    /**
     * name : Shaun Marsh
     * mobile : 89896589888
     */

    private String name;
    private String mobile;
    /**
     * state : up
     */

    private String state;
    /**
     * address_id : 6
     */

    private String address_id;


    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
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

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String Pincode) {
        this.Pincode = Pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
}
