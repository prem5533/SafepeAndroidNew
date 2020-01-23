package com.safepayu.wallet.ecommerce.model.response;

public class CartQuantityResponse {

    /**
     * status : true
     * message : Cart Quantity added Successfully.
     * cart : {"id":89,"guest":null,"customer_id":4,"type":1,"product_id":21,"modifier_id":21,"offer_id":0,"quantities":11,"cost_per_product":0,"coupon_code":null,"combo_no":null,"combo_group":null,"ip_address":null,"venue_id":"201911011148462","merchant_id":2,"created_at":"2020-01-22 11:24:04","updated_at":"2020-01-22 13:21:07"}
     */

    private boolean status;
    private String message;
    private CartBean cart;

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

    public CartBean getCart() {
        return cart;
    }

    public void setCart(CartBean cart) {
        this.cart = cart;
    }

    public static class CartBean {
        /**
         * id : 89
         * guest : null
         * customer_id : 4
         * type : 1
         * product_id : 21
         * modifier_id : 21
         * offer_id : 0
         * quantities : 11
         * cost_per_product : 0
         * coupon_code : null
         * combo_no : null
         * combo_group : null
         * ip_address : null
         * venue_id : 201911011148462
         * merchant_id : 2
         * created_at : 2020-01-22 11:24:04
         * updated_at : 2020-01-22 13:21:07
         */

        private int id;
        private Object guest;
        private int customer_id;
        private int type;
        private int product_id;
        private int modifier_id;
        private int offer_id;
        private int quantities;
        private int cost_per_product;
        private Object coupon_code;
        private Object combo_no;
        private Object combo_group;
        private Object ip_address;
        private String venue_id;
        private int merchant_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getGuest() {
            return guest;
        }

        public void setGuest(Object guest) {
            this.guest = guest;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(int modifier_id) {
            this.modifier_id = modifier_id;
        }

        public int getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(int offer_id) {
            this.offer_id = offer_id;
        }

        public int getQuantities() {
            return quantities;
        }

        public void setQuantities(int quantities) {
            this.quantities = quantities;
        }

        public int getCost_per_product() {
            return cost_per_product;
        }

        public void setCost_per_product(int cost_per_product) {
            this.cost_per_product = cost_per_product;
        }

        public Object getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(Object coupon_code) {
            this.coupon_code = coupon_code;
        }

        public Object getCombo_no() {
            return combo_no;
        }

        public void setCombo_no(Object combo_no) {
            this.combo_no = combo_no;
        }

        public Object getCombo_group() {
            return combo_group;
        }

        public void setCombo_group(Object combo_group) {
            this.combo_group = combo_group;
        }

        public Object getIp_address() {
            return ip_address;
        }

        public void setIp_address(Object ip_address) {
            this.ip_address = ip_address;
        }

        public String getVenue_id() {
            return venue_id;
        }

        public void setVenue_id(String venue_id) {
            this.venue_id = venue_id;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
