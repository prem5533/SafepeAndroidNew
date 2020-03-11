package com.safepayu.wallet.ecommerce.model.request;

import java.util.List;

public class ReturnOrderRequest {


    /**
     * order_details_ids : ["947"]
     * order_id : 537
     * return_qty : ["1"]
     */

    private String order_id;
    private List<String> order_details_ids;
    private List<String> return_qty;

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

    public List<String> getReturn_qty() {
        return return_qty;
    }

    public void setReturn_qty(List<String> return_qty) {
        this.return_qty = return_qty;
    }
}
