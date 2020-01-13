package com.safepayu.wallet.ecommerce.model.request;

public class ProductByModifierRequest {


    /**
     * product_id : 20
     * modifier_name : 15
     * venue_id : 201911011148462
     */

    private String product_id;
    private String modifier_name;
    private String venue_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getModifier_name() {
        return modifier_name;
    }

    public void setModifier_name(String modifier_name) {
        this.modifier_name = modifier_name;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }
}
