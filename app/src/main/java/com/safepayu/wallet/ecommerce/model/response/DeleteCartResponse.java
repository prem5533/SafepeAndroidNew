package com.safepayu.wallet.ecommerce.model.response;

public class DeleteCartResponse {

    /**
     * status : true
     * total_carts : 1
     * message : Cart Deleted Successfully.
     */

    private boolean status;
    private int total_carts;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal_carts() {
        return total_carts;
    }

    public void setTotal_carts(int total_carts) {
        this.total_carts = total_carts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
