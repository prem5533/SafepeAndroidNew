package com.safepayu.wallet.ecommerce.model.request;

public class AddToCartRequest {


    /**
     * product_id : 20
     * merchant_id : 2
     * venue_id : 201911011148462
     * modifier_id : 20
     * quantities : 10
     * offer_id : 12
     */

    private int product_id;
    private int merchant_id;
    private String venue_id;
    private String modifier_id;
    private String quantities;
    private int offer_id;

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

    public String getModifier_id() {
        return modifier_id;
    }

    public void setModifier_id(String modifier_id) {
        this.modifier_id = modifier_id;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }
}
