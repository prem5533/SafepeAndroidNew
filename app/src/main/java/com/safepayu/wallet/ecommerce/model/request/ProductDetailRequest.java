package com.safepayu.wallet.ecommerce.model.request;

public class ProductDetailRequest {

    /**
     * product_id : 70
     * offer_id : 1
     */

    private String product_id;
    private String offer_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }
}
