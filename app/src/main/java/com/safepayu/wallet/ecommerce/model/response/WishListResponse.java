package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class WishListResponse {

    /**
     * status : true
     * message : List of liked products.
     * likes : [{"id":19,"guest":null,"customer_id":4,"product_id":27,"modifier_id":30,"offer_id":2,"ip_address":null,"venue_id":"201911011148462","merchant_id":2,"created_at":"2019-11-25 07:46:29","updated_at":"2019-11-25 07:46:29","product_name":"ROSSO BRUNELLO Men's Tan Lace Ups Formal Leather Shoes","product_image":"uploaded/products/5853415730351820.jpeg","brand_id":11,"modifier_name":null,"buy_price":"80.00","selling_price":"100.00","avl_quantity":98,"modifier_images":null,"venue_name":"LillyWhites","offer_title":"10% Off","offer_type":"discper","disc_per":"10.00","disc_amt":"0.00","brand_name":"Pacifia"}]
     */

    private boolean status;
    private String message;
    private List<LikesBean> likes;

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

    public List<LikesBean> getLikes() {
        return likes;
    }

    public void setLikes(List<LikesBean> likes) {
        this.likes = likes;
    }

    public static class LikesBean {
        /**
         * id : 19
         * guest : null
         * customer_id : 4
         * product_id : 27
         * modifier_id : 30
         * offer_id : 2
         * ip_address : null
         * venue_id : 201911011148462
         * merchant_id : 2
         * created_at : 2019-11-25 07:46:29
         * updated_at : 2019-11-25 07:46:29
         * product_name : ROSSO BRUNELLO Men's Tan Lace Ups Formal Leather Shoes
         * product_image : uploaded/products/5853415730351820.jpeg
         * brand_id : 11
         * modifier_name : null
         * buy_price : 80.00
         * selling_price : 100.00
         * avl_quantity : 98
         * modifier_images : null
         * venue_name : LillyWhites
         * offer_title : 10% Off
         * offer_type : discper
         * disc_per : 10.00
         * disc_amt : 0.00
         * brand_name : Pacifia
         */

        private int id;
        private Object guest;
        private int customer_id;
        private int product_id;
        private int modifier_id;
        private int offer_id;
        private Object ip_address;
        private String venue_id;
        private int merchant_id;
        private String created_at;
        private String updated_at;
        private String product_name;
        private String product_image;
        private int brand_id;
        private Object modifier_name;
        private String buy_price;
        private String selling_price;
        private int avl_quantity;
        private Object modifier_images;
        private String venue_name;
        private String offer_title;
        private String offer_type;
        private String disc_per;
        private String disc_amt;
        private String brand_name;

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

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
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

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }
    }
}
