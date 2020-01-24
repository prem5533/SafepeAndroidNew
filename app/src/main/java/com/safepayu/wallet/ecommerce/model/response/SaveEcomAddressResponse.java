package com.safepayu.wallet.ecommerce.model.response;

public class SaveEcomAddressResponse {

    /**
     * status : true
     * message : Address Saved Successfully.
     * address : Sector 65, , Power stationNoida, India - 110014
     */

    private boolean status;
    private String message;
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
