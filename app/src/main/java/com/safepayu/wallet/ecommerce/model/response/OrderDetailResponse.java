package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class OrderDetailResponse {

    /**
     * status : true
     * orderList : {"id":536,"unique_code":"201912261358249","merchant_id":11,"venue_id":"2019121606413511","source_type":"ecom","payment_mode":"Card","order_date":"2019-12-26 13:58:24","order_status":"Approved","total_items":2,"net_amount":0.42,"loyelty_used_amount":"0.00","delivery_charge":"0.00","total_tax":"0.00","total_discount":"0.05","status":2,"delivery_date":"","venue_name":"Wolverhamton Morrions","end_days":3,"start_days":2,"name":"","email":"","mobile":"","delivery_address":"1 Oaklands Road, Wolverhampton, England- WV3 0DS","mrp":"0.47","products":[{"order_details_id":947,"product_qty":1,"order_id":536,"product_id":"94","attributes":null,"delivery_type":"Home Delivery","modifier_id":162,"net_amount":"0.22","item_status":"Pre Rejected","status":8,"return_day":1,"product_name":"Asparagus Tips","product_image":"uploaded/products/9938715764972390.jpeg","modifier_images":null,"brand_name":"Starx","rattings":"2","review":"Awe Awe","acknowledgement":[{"id":478,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:18:07"},{"id":479,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:28:51"},{"id":480,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:31:01"},{"id":481,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:32:32"},{"id":482,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:36:12"},{"id":483,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:37:05"},{"id":484,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:38:31"}]},{"order_details_id":948,"product_qty":1,"order_id":536,"product_id":"95","attributes":null,"delivery_type":"Home Delivery","modifier_id":163,"net_amount":"0.21","item_status":null,"status":0,"return_day":1,"product_name":"Trimmed Mangetout","product_image":"uploaded/products/2560815764973990.jpeg","modifier_images":null,"brand_name":"Starx","rattings":"0.0","review":null,"acknowledgement":[]}]}
     */

    private boolean status;
    private OrderListBean orderList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public OrderListBean getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderListBean orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * id : 536
         * unique_code : 201912261358249
         * merchant_id : 11
         * venue_id : 2019121606413511
         * source_type : ecom
         * payment_mode : Card
         * order_date : 2019-12-26 13:58:24
         * order_status : Approved
         * total_items : 2
         * net_amount : 0.42
         * loyelty_used_amount : 0.00
         * delivery_charge : 0.00
         * total_tax : 0.00
         * total_discount : 0.05
         * status : 2
         * delivery_date :
         * venue_name : Wolverhamton Morrions
         * end_days : 3
         * start_days : 2
         * name :
         * email :
         * mobile :
         * delivery_address : 1 Oaklands Road, Wolverhampton, England- WV3 0DS
         * mrp : 0.47
         * products : [{"order_details_id":947,"product_qty":1,"order_id":536,"product_id":"94","attributes":null,"delivery_type":"Home Delivery","modifier_id":162,"net_amount":"0.22","item_status":"Pre Rejected","status":8,"return_day":1,"product_name":"Asparagus Tips","product_image":"uploaded/products/9938715764972390.jpeg","modifier_images":null,"brand_name":"Starx","rattings":"2","review":"Awe Awe","acknowledgement":[{"id":478,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:18:07"},{"id":479,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:28:51"},{"id":480,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:31:01"},{"id":481,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:32:32"},{"id":482,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:36:12"},{"id":483,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:37:05"},{"id":484,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:38:31"}]},{"order_details_id":948,"product_qty":1,"order_id":536,"product_id":"95","attributes":null,"delivery_type":"Home Delivery","modifier_id":163,"net_amount":"0.21","item_status":null,"status":0,"return_day":1,"product_name":"Trimmed Mangetout","product_image":"uploaded/products/2560815764973990.jpeg","modifier_images":null,"brand_name":"Starx","rattings":"0.0","review":null,"acknowledgement":[]}]
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
        private String delivery_charge;
        private String total_tax;
        private String total_discount;
        private int status;
        private String delivery_date;
        private String venue_name;
        private int end_days;
        private int start_days;
        private String name;
        private String email;
        private String mobile;
        private String delivery_address;
        private String mrp;
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

        public String getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(String delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

        public String getTotal_tax() {
            return total_tax;
        }

        public void setTotal_tax(String total_tax) {
            this.total_tax = total_tax;
        }

        public String getTotal_discount() {
            return total_discount;
        }

        public void setTotal_discount(String total_discount) {
            this.total_discount = total_discount;
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

        public String getDelivery_address() {
            return delivery_address;
        }

        public void setDelivery_address(String delivery_address) {
            this.delivery_address = delivery_address;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * order_details_id : 947
             * product_qty : 1
             * order_id : 536
             * product_id : 94
             * attributes : null
             * delivery_type : Home Delivery
             * modifier_id : 162
             * net_amount : 0.22
             * item_status : Pre Rejected
             * status : 8
             * return_day : 1
             * product_name : Asparagus Tips
             * product_image : uploaded/products/9938715764972390.jpeg
             * modifier_images : null
             * brand_name : Starx
             * rattings : 2
             * review : Awe Awe
             * acknowledgement : [{"id":478,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:18:07"},{"id":479,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:28:51"},{"id":480,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:31:01"},{"id":481,"type":"1","return_qty":1,"discount":0.02,"tax":0,"status":8,"created_at":"2020-01-22 11:32:32"},{"id":482,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:36:12"},{"id":483,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:37:05"},{"id":484,"type":"2","return_qty":1,"discount":0.02,"tax":0,"status":9,"created_at":"2020-01-22 11:38:31"}]
             */

            private int order_details_id;
            private int product_qty;
            private int order_id;
            private String product_id;
            private Object attributes;
            private String delivery_type;
            private int modifier_id;
            private String net_amount;
            private String item_status;
            private int status;
            private int return_day;
            private String product_name;
            private String product_image;
            private Object modifier_images;
            private String brand_name;
            private String rattings;
            private String review;
            private List<AcknowledgementBean> acknowledgement;

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

            public String getItem_status() {
                return item_status;
            }

            public void setItem_status(String item_status) {
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

            public List<AcknowledgementBean> getAcknowledgement() {
                return acknowledgement;
            }

            public void setAcknowledgement(List<AcknowledgementBean> acknowledgement) {
                this.acknowledgement = acknowledgement;
            }

            public static class AcknowledgementBean {
                /**
                 * id : 478
                 * type : 1
                 * return_qty : 1
                 * discount : 0.02
                 * tax : 0
                 * status : 8
                 * created_at : 2020-01-22 11:18:07
                 */

                private int id;
                private String type;
                private int return_qty;
                private double discount;
                private double tax;
                private int status;
                private String created_at;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getReturn_qty() {
                    return return_qty;
                }

                public void setReturn_qty(int return_qty) {
                    this.return_qty = return_qty;
                }

                public double getDiscount() {
                    return discount;
                }

                public void setDiscount(double discount) {
                    this.discount = discount;
                }

                public double getTax() {
                    return tax;
                }

                public void setTax(double tax) {
                    this.tax = tax;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }
            }
        }
    }
}
