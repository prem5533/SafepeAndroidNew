package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class CartsQuantityResponse {

    /**
     * status : true
     * message : List of carts.
     * carts : [{"id":134,"guest":null,"customer_id":4,"type":1,"product_id":52,"modifier_id":79,"offer_id":null,"quantities":1,"cost_per_product":0,"coupon_code":null,"combo_no":null,"combo_group":null,"ip_address":null,"venue_id":"2019120307071210","merchant_id":10,"created_at":"2020-01-24 11:58:02","updated_at":"2020-01-24 12:48:03","product_name":"Tiny Red Roses Bunch","product_image":"uploaded/products/6179615755348320.jpeg","brand_id":8,"tax_id":"0","return_day":0,"modifier_list":null,"modifier_name":null,"modifier_value":null,"buy_price":"0.15","selling_price":"0.32","avl_quantity":382,"modifier_images":null,"venue_name":"Petalon Flowers London","phone_number":"44121212121","venue_email":"pfl@imailt.com","venue_website":null,"home_delivery_mov":"0.20","start_days":1,"end_days":3,"collection_time":100,"preparation_time":70,"free_delivery":"0.30","delivery_charge":"1.00","delivery":1,"collection":1,"venue_images":"1575356832.Petalon-98.jpg","latitude":"51.5341388","longitude":"-0.08055539999998018","address_1":"263 Hoxton St, Whitmore Estate, London, UK","loyalty_point":0,"loyalty_point_value":"0.30","distance":"3,560.50","offer_title":null,"offer_type":null,"disc_per":"0","disc_amt":"0","brand_name":"Wow","tax_name":null,"tax_amount":"0"},{"id":135,"guest":null,"customer_id":4,"type":1,"product_id":51,"modifier_id":78,"offer_id":0,"quantities":1,"cost_per_product":0,"coupon_code":null,"combo_no":null,"combo_group":null,"ip_address":null,"venue_id":"2019120307071210","merchant_id":10,"created_at":"2020-01-24 11:58:34","updated_at":"2020-01-24 12:47:59","product_name":"Missed You So Much","product_image":"uploaded/products/7980115755348420.jpeg","brand_id":8,"tax_id":"0","return_day":0,"modifier_list":null,"modifier_name":null,"modifier_value":null,"buy_price":"0.15","selling_price":"0.33","avl_quantity":241,"modifier_images":null,"venue_name":"Petalon Flowers London","phone_number":"44121212121","venue_email":"pfl@imailt.com","venue_website":null,"home_delivery_mov":"0.20","start_days":1,"end_days":3,"collection_time":100,"preparation_time":70,"free_delivery":"0.30","delivery_charge":"1.00","delivery":1,"collection":1,"venue_images":"1575356832.Petalon-98.jpg","latitude":"51.5341388","longitude":"-0.08055539999998018","address_1":"263 Hoxton St, Whitmore Estate, London, UK","loyalty_point":0,"loyalty_point_value":"0.30","distance":"3,560.50","offer_title":"","offer_type":"","disc_per":"0","disc_amt":"0","brand_name":"Wow","tax_name":null,"tax_amount":"0"}]
     */

    private boolean status;
    private String message;
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

    public List<CartsBean> getCarts() {
        return carts;
    }

    public void setCarts(List<CartsBean> carts) {
        this.carts = carts;
    }

    public static class CartsBean {
        /**
         * id : 134
         * guest : null
         * customer_id : 4
         * type : 1
         * product_id : 52
         * modifier_id : 79
         * offer_id : null
         * quantities : 1
         * cost_per_product : 0
         * coupon_code : null
         * combo_no : null
         * combo_group : null
         * ip_address : null
         * venue_id : 2019120307071210
         * merchant_id : 10
         * created_at : 2020-01-24 11:58:02
         * updated_at : 2020-01-24 12:48:03
         * product_name : Tiny Red Roses Bunch
         * product_image : uploaded/products/6179615755348320.jpeg
         * brand_id : 8
         * tax_id : 0
         * return_day : 0
         * modifier_list : null
         * modifier_name : null
         * modifier_value : null
         * buy_price : 0.15
         * selling_price : 0.32
         * avl_quantity : 382
         * modifier_images : null
         * venue_name : Petalon Flowers London
         * phone_number : 44121212121
         * venue_email : pfl@imailt.com
         * venue_website : null
         * home_delivery_mov : 0.20
         * start_days : 1
         * end_days : 3
         * collection_time : 100
         * preparation_time : 70
         * free_delivery : 0.30
         * delivery_charge : 1.00
         * delivery : 1
         * collection : 1
         * venue_images : 1575356832.Petalon-98.jpg
         * latitude : 51.5341388
         * longitude : -0.08055539999998018
         * address_1 : 263 Hoxton St, Whitmore Estate, London, UK
         * loyalty_point : 0
         * loyalty_point_value : 0.30
         * distance : 3,560.50
         * offer_title : null
         * offer_type : null
         * disc_per : 0
         * disc_amt : 0
         * brand_name : Wow
         * tax_name : null
         * tax_amount : 0
         */

        private int id;
        private Object guest;
        private int customer_id;
        private int type;
        private int product_id;
        private int modifier_id;

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
        private int brand_id;
        private String tax_id;
        private int return_day;
        private Object modifier_list;
        private Object modifier_name;
        private Object modifier_value;
        private String buy_price;
        private String selling_price;
        private int avl_quantity;
        private Object modifier_images;
        private String venue_name;
        private String phone_number;
        private String venue_email;
        private Object venue_website;
        private String home_delivery_mov;
        private int start_days;
        private int end_days;
        private int collection_time;
        private int preparation_time;
        private String free_delivery;
        private String delivery_charge;
        private int delivery;
        private int collection;
        private String venue_images;
        private String latitude;
        private String longitude;
        private String address_1;
        private double loyalty_point;
        private String loyalty_point_value;
        private String distance;
        private Object offer_title;
        private Object offer_type;
        private String disc_per;
        private String disc_amt;
        private String brand_name;
        private Object tax_name;
        private String tax_amount;
        /**
         * offer_id : 12
         */

        private int offer_id;


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

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public String getTax_id() {
            return tax_id;
        }

        public void setTax_id(String tax_id) {
            this.tax_id = tax_id;
        }

        public int getReturn_day() {
            return return_day;
        }

        public void setReturn_day(int return_day) {
            this.return_day = return_day;
        }

        public Object getModifier_list() {
            return modifier_list;
        }

        public void setModifier_list(Object modifier_list) {
            this.modifier_list = modifier_list;
        }

        public Object getModifier_name() {
            return modifier_name;
        }

        public void setModifier_name(Object modifier_name) {
            this.modifier_name = modifier_name;
        }

        public Object getModifier_value() {
            return modifier_value;
        }

        public void setModifier_value(Object modifier_value) {
            this.modifier_value = modifier_value;
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

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getVenue_email() {
            return venue_email;
        }

        public void setVenue_email(String venue_email) {
            this.venue_email = venue_email;
        }

        public Object getVenue_website() {
            return venue_website;
        }

        public void setVenue_website(Object venue_website) {
            this.venue_website = venue_website;
        }

        public String getHome_delivery_mov() {
            return home_delivery_mov;
        }

        public void setHome_delivery_mov(String home_delivery_mov) {
            this.home_delivery_mov = home_delivery_mov;
        }

        public int getStart_days() {
            return start_days;
        }

        public void setStart_days(int start_days) {
            this.start_days = start_days;
        }

        public int getEnd_days() {
            return end_days;
        }

        public void setEnd_days(int end_days) {
            this.end_days = end_days;
        }

        public int getCollection_time() {
            return collection_time;
        }

        public void setCollection_time(int collection_time) {
            this.collection_time = collection_time;
        }

        public int getPreparation_time() {
            return preparation_time;
        }

        public void setPreparation_time(int preparation_time) {
            this.preparation_time = preparation_time;
        }

        public String getFree_delivery() {
            return free_delivery;
        }

        public void setFree_delivery(String free_delivery) {
            this.free_delivery = free_delivery;
        }

        public String getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(String delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

        public int getDelivery() {
            return delivery;
        }

        public void setDelivery(int delivery) {
            this.delivery = delivery;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public String getVenue_images() {
            return venue_images;
        }

        public void setVenue_images(String venue_images) {
            this.venue_images = venue_images;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public double getLoyalty_point() {
            return loyalty_point;
        }

        public void setLoyalty_point(double loyalty_point) {
            this.loyalty_point = loyalty_point;
        }

        public String getLoyalty_point_value() {
            return loyalty_point_value;
        }

        public void setLoyalty_point_value(String loyalty_point_value) {
            this.loyalty_point_value = loyalty_point_value;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public Object getOffer_title() {
            return offer_title;
        }

        public void setOffer_title(Object offer_title) {
            this.offer_title = offer_title;
        }

        public Object getOffer_type() {
            return offer_type;
        }

        public void setOffer_type(Object offer_type) {
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

        public Object getTax_name() {
            return tax_name;
        }

        public void setTax_name(Object tax_name) {
            this.tax_name = tax_name;
        }

        public String getTax_amount() {
            return tax_amount;
        }

        public void setTax_amount(String tax_amount) {
            this.tax_amount = tax_amount;
        }

        public int getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(int offer_id) {
            this.offer_id = offer_id;
        }
    }
}
