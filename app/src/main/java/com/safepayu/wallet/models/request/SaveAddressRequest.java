package com.safepayu.wallet.models.request;

public class SaveAddressRequest {


    /**
     * user_id : 1
     * location : Varanasi
     * city : Varanasi
     * state : UP
     * country : india
     * pin : 221311
     */

    private String user_id;
    private String location;
    private String city;
    private String state;
    private String country;
    private String pin;

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
}
