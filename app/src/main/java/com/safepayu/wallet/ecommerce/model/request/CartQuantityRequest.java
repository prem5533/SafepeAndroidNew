package com.safepayu.wallet.ecommerce.model.request;

public class CartQuantityRequest {

    /**
     * cart_id : 87
     * quantities : 5
     */

    private int cart_id;
    private String quantities;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }
}
