package com.safepayu.wallet.ecommerce.model.request;

import java.util.List;

public class OrderSaveRequest {

    /**
     * merchant_id : 2
     * venue_id : 201911011148462
     * source_type : app
     * payment_mode : Card
     * payment_wallet : 0
     * payment_bank : 0
     * pos_order_id :
     * till_id : 0
     * staff_id : 0
     * card_id : card_1FosRKA4mS3BlT3IWFAz6V18
     * order_date : 2019-09-4 14:14:26
     * order_status :
     * user_addr_id : 30
     * total_items : 1
     * amt_without_tax_discount : 120
     * total_discount : 80
     * total_tax : 0
     * net_amount : 120.2
     * delivery_charge : 15
     * loyelty_used_amount : 20
     * is_gift : 1
     * reorder_status : 0
     * delivery_type : Click & Collect
     * delivery_time : 9:00am -12:am
     * products : [{"product_qty":1,"product_id":8,"modifier_id":"8","delivery_type":"Click & Collect","delivery_address":"27a Oaklands Road, Wolverhampton, England- WV3 0DS","billing_address":"Wake Green Road, Birmingham B13 9PG, UK","merchant_id":2,"venue_id":"201911011148462","attributes":"","cost":100,"buy_price":85,"discount_applied":40,"offer_id":2,"tax_applied":0,"tax_id":0,"net_amount":"60","item_status":"Complete"},{"product_qty":1,"product_id":8,"modifier_id":"8","delivery_type":"Click & Collect","delivery_address":"27a Oaklands Road, Wolverhampton, England- WV3 0DS","billing_address":"Wake Green Road, Birmingham B13 9PG, UK","merchant_id":2,"venue_id":"201911011148462","attributes":"","cost":100,"buy_price":85,"discount_applied":40,"offer_id":2,"tax_applied":0,"tax_id":0,"net_amount":"60","item_status":"Complete"}]
     */

    private String merchant_id;
    private String venue_id;
    private String source_type;
    private String payment_mode;
    private String payment_wallet;
    private String payment_bank;
    private String pos_order_id;
    private int till_id;
    private int staff_id;
    private String card_id;
    private String order_date;
    private String order_status;
    private String user_addr_id;
    private int total_items;
    private double amt_without_tax_discount;
    private double total_discount;
    private double total_tax;
    private double net_amount;
    private double delivery_charge;
    private int loyelty_used_amount;
    private String is_gift;
    private int reorder_status;
    private String delivery_type;
    private String delivery_time;
    private List<ProductsBean> products;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getPayment_wallet() {
        return payment_wallet;
    }

    public void setPayment_wallet(String payment_wallet) {
        this.payment_wallet = payment_wallet;
    }

    public String getPayment_bank() {
        return payment_bank;
    }

    public void setPayment_bank(String payment_bank) {
        this.payment_bank = payment_bank;
    }

    public String getPos_order_id() {
        return pos_order_id;
    }

    public void setPos_order_id(String pos_order_id) {
        this.pos_order_id = pos_order_id;
    }

    public int getTill_id() {
        return till_id;
    }

    public void setTill_id(int till_id) {
        this.till_id = till_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getUser_addr_id() {
        return user_addr_id;
    }

    public void setUser_addr_id(String user_addr_id) {
        this.user_addr_id = user_addr_id;
    }

    public int getTotal_items() {
        return total_items;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    public double getAmt_without_tax_discount() {
        return amt_without_tax_discount;
    }

    public void setAmt_without_tax_discount(double amt_without_tax_discount) {
        this.amt_without_tax_discount = amt_without_tax_discount;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }

    public double getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }

    public double getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(double net_amount) {
        this.net_amount = net_amount;
    }

    public double getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(double delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public int getLoyelty_used_amount() {
        return loyelty_used_amount;
    }

    public void setLoyelty_used_amount(int loyelty_used_amount) {
        this.loyelty_used_amount = loyelty_used_amount;
    }

    public String getIs_gift() {
        return is_gift;
    }

    public void setIs_gift(String is_gift) {
        this.is_gift = is_gift;
    }

    public int getReorder_status() {
        return reorder_status;
    }

    public void setReorder_status(int reorder_status) {
        this.reorder_status = reorder_status;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * product_qty : 1
         * product_id : 8
         * modifier_id : 8
         * delivery_type : Click & Collect
         * delivery_address : 27a Oaklands Road, Wolverhampton, England- WV3 0DS
         * billing_address : Wake Green Road, Birmingham B13 9PG, UK
         * merchant_id : 2
         * venue_id : 201911011148462
         * attributes :
         * cost : 100.0
         * buy_price : 85.0
         * discount_applied : 40
         * offer_id : 2
         * tax_applied : 0
         * tax_id : 0
         * net_amount : 60
         * item_status : Complete
         */

        private int product_qty;
        private int product_id;
        private String modifier_id;
        private String delivery_type;
        private String delivery_address;
        private String billing_address;
        private int merchant_id;
        private String venue_id;
        private String attributes;
        private double cost;
        private double buy_price;
        private int discount_applied;
        private int offer_id;
        private int tax_applied;
        private int tax_id;
        private String net_amount;
        private String item_status;

        public int getProduct_qty() {
            return product_qty;
        }

        public void setProduct_qty(int product_qty) {
            this.product_qty = product_qty;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(String modifier_id) {
            this.modifier_id = modifier_id;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public String getDelivery_address() {
            return delivery_address;
        }

        public void setDelivery_address(String delivery_address) {
            this.delivery_address = delivery_address;
        }

        public String getBilling_address() {
            return billing_address;
        }

        public void setBilling_address(String billing_address) {
            this.billing_address = billing_address;
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

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(double buy_price) {
            this.buy_price = buy_price;
        }

        public int getDiscount_applied() {
            return discount_applied;
        }

        public void setDiscount_applied(int discount_applied) {
            this.discount_applied = discount_applied;
        }

        public int getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(int offer_id) {
            this.offer_id = offer_id;
        }

        public int getTax_applied() {
            return tax_applied;
        }

        public void setTax_applied(int tax_applied) {
            this.tax_applied = tax_applied;
        }

        public int getTax_id() {
            return tax_id;
        }

        public void setTax_id(int tax_id) {
            this.tax_id = tax_id;
        }

        public String getNet_amount() {
            return net_amount;
        }

        public void setNet_amount(String net_amount) {
            this.net_amount = net_amount;
        }

        public String getItem_status() {
            return item_status;
        }

        public void setItem_status(String item_status) {
            this.item_status = item_status;
        }
    }
}
