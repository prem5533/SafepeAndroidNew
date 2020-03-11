package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class MyOrderListResponse {

    /**
     * status : true
     * orderList : [{"id":534,"unique_code":"201912261355309","merchant_id":11,"venue_id":"2019121606413511","source_type":"ecom","payment_mode":"Card","order_date":"2019-12-26 13:55:30","order_status":"Rejected","total_items":2,"net_amount":0.42,"loyelty_used_amount":"0.00","status":1,"delivery_date":null,"venue_name":"Wolverhamton Morrions","end_days":3,"start_days":2,"products":[{"order_details_id":944,"product_qty":1,"order_id":534,"product_id":"94","attributes":null,"delivery_type":"Home Delivery","modifier_id":162,"net_amount":"0.22","item_status":null,"status":0,"return_day":1,"product_name":"Asparagus Tips","product_image":"uploaded/products/9938715764972390.jpeg","modifier_images":null,"brand_name":"Starx","acknowledgement":[]},{"order_details_id":945,"product_qty":1,"order_id":534,"product_id":"95","attributes":null,"delivery_type":"Home Delivery","modifier_id":163,"net_amount":"0.21","item_status":null,"status":0,"return_day":1,"product_name":"Trimmed Mangetout","product_image":"uploaded/products/2560815764973990.jpeg","modifier_images":null,"brand_name":"Starx","acknowledgement":[]}]}]
     */

    private boolean status;
    private List<OrderListBean> orderList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * id : 534
         * unique_code : 201912261355309
         * merchant_id : 11
         * venue_id : 2019121606413511
         * source_type : ecom
         * payment_mode : Card
         * order_date : 2019-12-26 13:55:30
         * order_status : Rejected
         * total_items : 2
         * net_amount : 0.42
         * loyelty_used_amount : 0.00
         * status : 1
         * delivery_date : null
         * venue_name : Wolverhamton Morrions
         * end_days : 3
         * start_days : 2
         * products : [{"order_details_id":944,"product_qty":1,"order_id":534,"product_id":"94","attributes":null,"delivery_type":"Home Delivery","modifier_id":162,"net_amount":"0.22","item_status":null,"status":0,"return_day":1,"product_name":"Asparagus Tips","product_image":"uploaded/products/9938715764972390.jpeg","modifier_images":null,"brand_name":"Starx","acknowledgement":[]},{"order_details_id":945,"product_qty":1,"order_id":534,"product_id":"95","attributes":null,"delivery_type":"Home Delivery","modifier_id":163,"net_amount":"0.21","item_status":null,"status":0,"return_day":1,"product_name":"Trimmed Mangetout","product_image":"uploaded/products/2560815764973990.jpeg","modifier_images":null,"brand_name":"Starx","acknowledgement":[]}]
         */

        private int id;
        private String unique_code;
        private int merchant_id;
        private String venue_id;
        private String source_type;
        private String payment_mode;
        private String order_date;
        private String order_status;
        private int total_items;
        private double net_amount;
        private String loyelty_used_amount;
        private int status;
        private String delivery_date;
        private String venue_name;
        private int end_days;
        private int start_days;
        private List<ProductsBean> products;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUnique_code() {
            return unique_code;
        }

        public void setUnique_code(String unique_code) {
            this.unique_code = unique_code;
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

        public int getTotal_items() {
            return total_items;
        }

        public void setTotal_items(int total_items) {
            this.total_items = total_items;
        }

        public double getNet_amount() {
            return net_amount;
        }

        public void setNet_amount(double net_amount) {
            this.net_amount = net_amount;
        }

        public String getLoyelty_used_amount() {
            return loyelty_used_amount;
        }

        public void setLoyelty_used_amount(String loyelty_used_amount) {
            this.loyelty_used_amount = loyelty_used_amount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDelivery_date() {
            return delivery_date;
        }

        public void setDelivery_date(String delivery_date) {
            this.delivery_date = delivery_date;
        }

        public String getVenue_name() {
            return venue_name;
        }

        public void setVenue_name(String venue_name) {
            this.venue_name = venue_name;
        }

        public int getEnd_days() {
            return end_days;
        }

        public void setEnd_days(int end_days) {
            this.end_days = end_days;
        }

        public int getStart_days() {
            return start_days;
        }

        public void setStart_days(int start_days) {
            this.start_days = start_days;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * order_details_id : 944
             * product_qty : 1
             * order_id : 534
             * product_id : 94
             * attributes : null
             * delivery_type : Home Delivery
             * modifier_id : 162
             * net_amount : 0.22
             * item_status : null
             * status : 0
             * return_day : 1
             * product_name : Asparagus Tips
             * product_image : uploaded/products/9938715764972390.jpeg
             * modifier_images : null
             * brand_name : Starx
             * acknowledgement : []
             */

            private int order_details_id;
            private int product_qty;
            private int order_id;
            private String product_id;
            private Object attributes;
            private String delivery_type;
            private int modifier_id;
            private String net_amount;
            private Object item_status;
            private int status;
            private int return_day;
            private String product_name;
            private String product_image;
            private Object modifier_images;
            private String brand_name;
            private List<?> acknowledgement;
            private String rattings;
            private String review;

            /**
             * unique_code : 201912261355309
             * venue_id : 2019121606413511
             * venue_name : Wolverhamton Morrions
             * merchant_id : 11
             * order_status : Rejected
             * delivery_date : 24 jan,2020
             * net_amount : 0.42
             * rattings : 2.3
             * review : hsajshajs
             */

            private String unique_code;
            private String venue_id;
            private String venue_name;
            private int merchant_id;
            private String order_status;
            private String delivery_date;

//            @SerializedName("net_amount")
//            private double net_amountX;


            public int getOrder_details_id() {
                return order_details_id;
            }

            public void setOrder_details_id(int order_details_id) {
                this.order_details_id = order_details_id;
            }

            public int getProduct_qty() {
                return product_qty;
            }

            public void setProduct_qty(int product_qty) {
                this.product_qty = product_qty;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public Object getAttributes() {
                return attributes;
            }

            public void setAttributes(Object attributes) {
                this.attributes = attributes;
            }

            public String getDelivery_type() {
                return delivery_type;
            }

            public void setDelivery_type(String delivery_type) {
                this.delivery_type = delivery_type;
            }

            public int getModifier_id() {
                return modifier_id;
            }

            public void setModifier_id(int modifier_id) {
                this.modifier_id = modifier_id;
            }

            public String getNet_amount() {
                return net_amount;
            }

            public void setNet_amount(String net_amount) {
                this.net_amount = net_amount;
            }

            public Object getItem_status() {
                return item_status;
            }

            public void setItem_status(Object item_status) {
                this.item_status = item_status;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getReturn_day() {
                return return_day;
            }

            public void setReturn_day(int return_day) {
                this.return_day = return_day;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_image() {
                return product_image;
            }

            public void setProduct_image(String product_image) {
                this.product_image = product_image;
            }

            public Object getModifier_images() {
                return modifier_images;
            }

            public void setModifier_images(Object modifier_images) {
                this.modifier_images = modifier_images;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public List<?> getAcknowledgement() {
                return acknowledgement;
            }

            public void setAcknowledgement(List<?> acknowledgement) {
                this.acknowledgement = acknowledgement;
            }

            public String getUnique_code() {
                return unique_code;
            }

            public void setUnique_code(String unique_code) {
                this.unique_code = unique_code;
            }

            public String getVenue_id() {
                return venue_id;
            }

            public void setVenue_id(String venue_id) {
                this.venue_id = venue_id;
            }

            public String getVenue_name() {
                return venue_name;
            }

            public void setVenue_name(String venue_name) {
                this.venue_name = venue_name;
            }

            public int getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(int merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getDelivery_date() {
                return delivery_date;
            }

            public void setDelivery_date(String delivery_date) {
                this.delivery_date = delivery_date;
            }

            public String getRattings() {
                return rattings;
            }

            public void setRattings(String rattings) {
                this.rattings = rattings;
            }

            public String getReview() {
                return review;
            }

            public void setReview(String review) {
                this.review = review;
            }

//            public double getNet_amountX() {
//                return net_amountX;
//            }

//            public void setNet_amountX(double net_amountX) {
//                this.net_amountX = net_amountX;
//            }
        }
    }
}
