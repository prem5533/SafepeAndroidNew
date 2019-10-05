package com.safepayu.wallet.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAddress {

    @SerializedName("user_id")
    @Expose
    String user_id;
    @SerializedName("location")
    @Expose
    String location;
    @SerializedName("city")
    @Expose
    String city;
    @SerializedName("state")
    @Expose
    String state;
    @SerializedName("country")
    @Expose
    String country;
    @SerializedName("pin")
    @Expose
    String pin;


    public UpdateAddress(String user_id, String location, String city, String state, String country, String pin) {
        this.user_id = user_id;
        this.location = location;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pin = pin;
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
}
