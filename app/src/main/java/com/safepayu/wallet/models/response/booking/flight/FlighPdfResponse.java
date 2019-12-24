package com.safepayu.wallet.models.response.booking.flight;

public class FlighPdfResponse {

    /**
     * status : true
     * message : Ticet found
     * data : http://testing.safepeindia.com/uploaded/Booking/Flight/300270016738.pdf
     */

    private boolean status;
    private String message;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
