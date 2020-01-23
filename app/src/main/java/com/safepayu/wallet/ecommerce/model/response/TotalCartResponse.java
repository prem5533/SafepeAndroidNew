package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class TotalCartResponse  {


    /**
     * status : true
     * message : Total number of Carts.
     * carts : [{"id":80,"guest":null,"customer_id":4,"type":1,"product_id":20,"modifier_id":20,"offer_id":12,"quantities":10,"cost_per_product":0,"coupon_code":null,"combo_no":null,"combo_group":null,"ip_address":null,"venue_id":"201911011148462","merchant_id":2,"created_at":"2020-01-22 09:58:05","updated_at":"2020-01-22 09:58:05","product_name":"OnePlus 138.8 cm (55 inches)","product_image":"uploaded/products/3750215730270990.jpeg","modifier_name":null,"buy_price":"120.00","selling_price":"150.00","avl_quantity":199,"modifier_images":null,"venue_name":"LillyWhites","venue_images":null,"distance":"0.00","offer_title":"10% Off","offer_type":"discper","disc_per":"10.00","disc_amt":"0.00","brand_name":null,"tax_name":null,"tax_amount":0,"loyalty_point":0,"loyalty_point_value":0}]
     * total_carts : 1
     * cart_venue : 201911011148462
     * venue_opening_time : 06:15
     * venue_closing_time : 20:30
     */

    private boolean status;
    private String message;
    private int total_carts;
    private String cart_venue;
    private String venue_opening_time;
    private String venue_closing_time;
    private List<CartsBean> carts;

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

    public int getTotal_carts() {
        return total_carts;
    }

    public void setTotal_carts(int total_carts) {
        this.total_carts = total_carts;
    }

    public String getCart_venue() {
        return cart_venue;
    }

    public void setCart_venue(String cart_venue) {
        this.cart_venue = cart_venue;
    }

    public String getVenue_opening_time() {
        return venue_opening_time;
    }

    public void setVenue_opening_time(String venue_opening_time) {
        this.venue_opening_time = venue_opening_time;
    }

    public String getVenue_closing_time() {
        return venue_closing_time;
    }

    public void setVenue_closing_time(String venue_closing_time) {
        this.venue_closing_time = venue_closing_time;
    }

    public List<CartsBean> getCarts() {
        return carts;
    }

    public void setCarts(List<CartsBean> carts) {
        this.carts = carts;
    }

    public static class CartsBean {
        /**
         * id : 80
         * guest : null
         * customer_id : 4
         * type : 1
         * product_id : 20
         * modifier_id : 20
         * offer_id : 12
         * quantities : 10
         * cost_per_product : 0
         * coupon_code : null
         * combo_no : null
         * combo_group : null
         * ip_address : null
         * venue_id : 201911011148462
         * merchant_id : 2
         * created_at : 2020-01-22 09:58:05
         * updated_at : 2020-01-22 09:58:05
         * product_name : OnePlus 138.8 cm (55 inches)
         * product_image : uploaded/products/3750215730270990.jpeg
         * modifier_name : null
         * buy_price : 120.00
         * selling_price : 150.00
         * avl_quantity : 199
         * modifier_images : null
         * venue_name : LillyWhites
         * venue_images : null
         * distance : 0.00
         * offer_title : 10% Off
         * offer_type : discper
         * disc_per : 10.00
         * disc_amt : 0.00
         * brand_name : null
         * tax_name : null
         * tax_amount : 0
         * loyalty_point : 0
         * loyalty_point_value : 0
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
        private String product_name;
        private String product_image;
        private Object modifier_name;
        private String buy_price;
        private String selling_price;
        private int avl_quantity;
        private Object modifier_images;
        private String venue_name;
        private Object venue_images;
        private String distance;
        private String offer_title;
        private String offer_type;
        private String disc_per;
        private String disc_amt;
        private Object brand_name;
        private Object tax_name;
        private int tax_amount;
        private int loyalty_point;
        private int loyalty_point_value;

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

        public Object getModifier_name() {
            return modifier_name;
        }

        public void setModifier_name(Object modifier_name) {
            this.modifier_name = modifier_name;
        }

        public String getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(String buy_price) {
            this.buy_price = buy_price;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public int getAvl_quantity() {
            return avl_quantity;
        }

        public void setAvl_quantity(int avl_quantity) {
            this.avl_quantity = avl_quantity;
        }

        public Object getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(Object modifier_images) {
            this.modifier_images = modifier_images;
        }

        public String getVenue_name() {
            return venue_name;
        }

        public void setVenue_name(String venue_name) {
            this.venue_name = venue_name;
        }

        public Object getVenue_images() {
            return venue_images;
        }

        public void setVenue_images(Object venue_images) {
            this.venue_images = venue_images;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getOffer_title() {
            return offer_title;
        }

        public void setOffer_title(String offer_title) {
            this.offer_title = offer_title;
        }

        public String getOffer_type() {
            return offer_type;
        }

        public void setOffer_type(String offer_type) {
            this.offer_type = offer_type;
        }

        public String getDisc_per() {
            return disc_per;
        }

        public void setDisc_per(String disc_per) {
            this.disc_per = disc_per;
        }

        public String getDisc_amt() {
            return disc_amt;
        }

        public void setDisc_amt(String disc_amt) {
            this.disc_amt = disc_amt;
        }

        public Object getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(Object brand_name) {
            this.brand_name = brand_name;
        }

        public Object getTax_name() {
            return tax_name;
        }

        public void setTax_name(Object tax_name) {
            this.tax_name = tax_name;
        }

        public int getTax_amount() {
            return tax_amount;
        }

        public void setTax_amount(int tax_amount) {
            this.tax_amount = tax_amount;
        }

        public int getLoyalty_point() {
            return loyalty_point;
        }

        public void setLoyalty_point(int loyalty_point) {
            this.loyalty_point = loyalty_point;
        }

        public int getLoyalty_point_value() {
            return loyalty_point_value;
        }

        public void setLoyalty_point_value(int loyalty_point_value) {
            this.loyalty_point_value = loyalty_point_value;
        }
    }
}
