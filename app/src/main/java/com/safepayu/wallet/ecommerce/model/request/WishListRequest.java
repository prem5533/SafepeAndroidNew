package com.safepayu.wallet.ecommerce.model.request;

public class WishListRequest {

    /**
     * modifier_id : 46
     * product_id : 35
     * merchant_id : 2
     * venue_id : 201911011148462
     */

    private int modifier_id;
    private int product_id;
    private int merchant_id;
    private String venue_id;

    public int getModifier_id() {
        return modifier_id;
    }

    public void setModifier_id(int modifier_id) {
        this.modifier_id = modifier_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }
}
