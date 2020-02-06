package com.safepayu.wallet.ecommerce.model.request;

public class NotifyMeRequest {

    /**
     * email : as@ff.com
     * product_id : 70
     * modifier_id : 135
     * venue_id : 201911011148462
     * merchant_id : 2
     */

    private String email;
    private String product_id;
    private String modifier_id;
    private String venue_id;
    private String merchant_id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getModifier_id() {
        return modifier_id;
    }

    public void setModifier_id(String modifier_id) {
        this.modifier_id = modifier_id;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }
}
