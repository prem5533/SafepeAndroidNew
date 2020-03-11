package com.safepayu.wallet.ecommerce.model.request;

import java.util.List;

public class CancelOrderRequest {


    /**
     * order_id : 537
     * order_details_ids : ["949"]
     */

    private String order_id;
    private List<String> order_details_ids;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<String> getOrder_details_ids() {
        return order_details_ids;
    }

    public void setOrder_details_ids(List<String> order_details_ids) {
        this.order_details_ids = order_details_ids;
    }
}
