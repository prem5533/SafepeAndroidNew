package com.safepayu.wallet.ecommerce.model.response;

public class OrderSaveResponse {

    /**
     * status : true
     * message : Order Created Successfully.
     * order : {"unique_code":"202001280554244","merchant_id":"2","venue_id":"201911011148462","till_id":0,"staff_id":0,"cust_id":4,"payment_mode":"Card","source_type":"app","order_date":"2020-01-28 05:54:24","delivery_type":"Click & Collect","order_status":null,"user_addr_id":"30","total_items":1,"amt_without_tax_discount":120,"total_discount":80,"total_tax":0,"net_amount":120.2,"delivery_charge":15,"stamp_reward_consumed_id":0,"claim_stamp_reward_id":0,"claim_stamp_reward_amt":0,"loyalty_point_value":0.25,"loyalty_point":80,"loyelty_used_amount":20,"pos_order_id":0,"loyelty_consumed":10,"reorder_status":0,"stripe_capture_id":null,"status":0,"is_gift":"1","name":"Arpan Pundir","email":"arpan.pundir20@gmail.com","mobile":"8375890846","updated_at":"2020-01-28 05:54:24","created_at":"2020-01-28 05:54:24","id":565}
     */

    private boolean status;
    private String message;
    private OrderBean order;

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

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * unique_code : 202001280554244
         * merchant_id : 2
         * venue_id : 201911011148462
         * till_id : 0
         * staff_id : 0
         * cust_id : 4
         * payment_mode : Card
         * source_type : app
         * order_date : 2020-01-28 05:54:24
         * delivery_type : Click & Collect
         * order_status : null
         * user_addr_id : 30
         * total_items : 1
         * amt_without_tax_discount : 120
         * total_discount : 80
         * total_tax : 0
         * net_amount : 120.2
         * delivery_charge : 15
         * stamp_reward_consumed_id : 0
         * claim_stamp_reward_id : 0
         * claim_stamp_reward_amt : 0
         * loyalty_point_value : 0.25
         * loyalty_point : 80
         * loyelty_used_amount : 20
         * pos_order_id : 0
         * loyelty_consumed : 10
         * reorder_status : 0
         * stripe_capture_id : null
         * status : 0
         * is_gift : 1
         * name : Arpan Pundir
         * email : arpan.pundir20@gmail.com
         * mobile : 8375890846
         * updated_at : 2020-01-28 05:54:24
         * created_at : 2020-01-28 05:54:24
         * id : 565
         */

        private String unique_code;
        private String merchant_id;
        private String venue_id;
        private int till_id;
        private int staff_id;
        private int cust_id;
        private String payment_mode;
        private String source_type;
        private String order_date;
        private String delivery_type;
        private Object order_status;
        private String user_addr_id;
        private int total_items;
        private int amt_without_tax_discount;
        private int total_discount;
        private int total_tax;
        private double net_amount;
        private int delivery_charge;
        private int stamp_reward_consumed_id;
        private int claim_stamp_reward_id;
        private int claim_stamp_reward_amt;
        private double loyalty_point_value;
        private int loyalty_point;
        private int loyelty_used_amount;
        private int pos_order_id;
        private int loyelty_consumed;
        private int reorder_status;
        private Object stripe_capture_id;
        private int status;
        private String is_gift;
        private String name;
        private String email;
        private String mobile;
        private String updated_at;
        private String created_at;
        private int id;

        public String getUnique_code() {
            return unique_code;
        }

        public void setUnique_code(String unique_code) {
            this.unique_code = unique_code;
        }

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

        public int getCust_id() {
            return cust_id;
        }

        public void setCust_id(int cust_id) {
            this.cust_id = cust_id;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getSource_type() {
            return source_type;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public Object getOrder_status() {
            return order_status;
        }

        public void setOrder_status(Object order_status) {
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

        public int getAmt_without_tax_discount() {
            return amt_without_tax_discount;
        }

        public void setAmt_without_tax_discount(int amt_without_tax_discount) {
            this.amt_without_tax_discount = amt_without_tax_discount;
        }

        public int getTotal_discount() {
            return total_discount;
        }

        public void setTotal_discount(int total_discount) {
            this.total_discount = total_discount;
        }

        public int getTotal_tax() {
            return total_tax;
        }

        public void setTotal_tax(int total_tax) {
            this.total_tax = total_tax;
        }

        public double getNet_amount() {
            return net_amount;
        }

        public void setNet_amount(double net_amount) {
            this.net_amount = net_amount;
        }

        public int getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(int delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

        public int getStamp_reward_consumed_id() {
            return stamp_reward_consumed_id;
        }

        public void setStamp_reward_consumed_id(int stamp_reward_consumed_id) {
            this.stamp_reward_consumed_id = stamp_reward_consumed_id;
        }

        public int getClaim_stamp_reward_id() {
            return claim_stamp_reward_id;
        }

        public void setClaim_stamp_reward_id(int claim_stamp_reward_id) {
            this.claim_stamp_reward_id = claim_stamp_reward_id;
        }

        public int getClaim_stamp_reward_amt() {
            return claim_stamp_reward_amt;
        }

        public void setClaim_stamp_reward_amt(int claim_stamp_reward_amt) {
            this.claim_stamp_reward_amt = claim_stamp_reward_amt;
        }

        public double getLoyalty_point_value() {
            return loyalty_point_value;
        }

        public void setLoyalty_point_value(double loyalty_point_value) {
            this.loyalty_point_value = loyalty_point_value;
        }

        public int getLoyalty_point() {
            return loyalty_point;
        }

        public void setLoyalty_point(int loyalty_point) {
            this.loyalty_point = loyalty_point;
        }

        public int getLoyelty_used_amount() {
            return loyelty_used_amount;
        }

        public void setLoyelty_used_amount(int loyelty_used_amount) {
            this.loyelty_used_amount = loyelty_used_amount;
        }

        public int getPos_order_id() {
            return pos_order_id;
        }

        public void setPos_order_id(int pos_order_id) {
            this.pos_order_id = pos_order_id;
        }

        public int getLoyelty_consumed() {
            return loyelty_consumed;
        }

        public void setLoyelty_consumed(int loyelty_consumed) {
            this.loyelty_consumed = loyelty_consumed;
        }

        public int getReorder_status() {
            return reorder_status;
        }

        public void setReorder_status(int reorder_status) {
            this.reorder_status = reorder_status;
        }

        public Object getStripe_capture_id() {
            return stripe_capture_id;
        }

        public void setStripe_capture_id(Object stripe_capture_id) {
            this.stripe_capture_id = stripe_capture_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIs_gift() {
            return is_gift;
        }

        public void setIs_gift(String is_gift) {
            this.is_gift = is_gift;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
