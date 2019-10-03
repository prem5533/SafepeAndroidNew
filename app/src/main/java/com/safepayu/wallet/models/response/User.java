package com.safepayu.wallet.models.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User  {
    @SerializedName("userid")
    String userId;
    @SerializedName("id")
    String id;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("email_verified_at")
    String emailVerifiedAt;
    @SerializedName("email_verified")
    String emailVerified;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("mobile_verified")
    String mobileVerified;
    @SerializedName("dob")
    String dob;
    @SerializedName("referral_code")
    String referralCode;
    @SerializedName("referral_recieved")
    String referralRecieved;
    @SerializedName("sponsor")
    String sponsor;
    @SerializedName("blocked")
    String blocked;
    @SerializedName("is_binary")
    String is_binary;
    @SerializedName("passcode")
    String passCode;
    @SerializedName("device_type")
    String deviceType;
    @SerializedName("device_id")
    String deviceId;
    @SerializedName("location")
    String location;
    @SerializedName("city")
    String city;
    @SerializedName("state")
    String state;
    @SerializedName("country")
    String country;
    @SerializedName("pin")
    String pin;

    public User() {
    }

    public User(String userId, String id, String firstName, String lastName, String email, String emailVerifiedAt, String emailVerified, String mobile, String mobileVerified, String dob, String referralCode, String referralRecieved, String sponsor, String blocked, String is_binary, String passCode, String deviceType, String deviceId, String location, String city, String state, String country, String pin) {
        this.userId = userId;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.emailVerifiedAt = emailVerifiedAt;
        this.emailVerified = emailVerified;
        this.mobile = mobile;
        this.mobileVerified = mobileVerified;
        this.dob = dob;
        this.referralCode = referralCode;
        this.referralRecieved = referralRecieved;
        this.sponsor = sponsor;
        this.blocked = blocked;
        this.is_binary = is_binary;
        this.passCode = passCode;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.location = location;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(String mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferralRecieved() {
        return referralRecieved;
    }

    public void setReferralRecieved(String referralRecieved) {
        this.referralRecieved = referralRecieved;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getIs_binary() {
        return is_binary;
    }

    public void setIs_binary(String is_binary) {
        this.is_binary = is_binary;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", emailVerifiedAt='" + emailVerifiedAt + '\'' +
                ", emailVerified='" + emailVerified + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobileVerified='" + mobileVerified + '\'' +
                ", dob='" + dob + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", referralRecieved='" + referralRecieved + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", blocked='" + blocked + '\'' +
                ", is_binary='" + is_binary + '\'' +
                ", passCode='" + passCode + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", location='" + location + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}
