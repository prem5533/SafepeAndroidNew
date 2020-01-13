package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class ProductsBySearchResponse {

    /**
     * status : true
     * message : List Of product Details By Search
     * category : [{"id":32,"cat_name":"Ayurveda","parent_cat_id":7,"breadcrumb":"7","color":"64e6a9","menu_level":2}]
     * products : [{"id":11,"product_name":"Fur Jaden Anti Theft Zipper","product_description":"<ul class=\"a-unordered-list a-vertical a-spacing-none\" style=\"margin-right: 0px; margin-bottom: 0px; margin-left: 18px; color: rgb(17, 17, 17); padding: 0px; font-family: &quot;Amazon Ember&quot;, Arial, sans-serif; font-size: 13px;\"><li style=\"list-style: disc; overflow-wrap: break-word; margin: 0px;\"><span class=\"a-list-item\">Style - Brown colour backpack for men made of PU leather for stylish office use as well as for trendy college students. Huge compartment size that can fit upto a 15 inch laptop with ease. Multiple pockets for other accessories and water bottle. Please note Zips are not Water Resistant.<\/span><\/li><li style=\"list-style: disc; overflow-wrap: break-word; margin: 0px;\"><span class=\"a-list-item\">USB Charging - Now charge your phone on the go while travelling without the hassle of holding the bulky power bank in your pocket or in your hand. Simple Connect your Power bank to the wire inside the Bag and leave it in the bag. When you want to charge your phone, just connect your phone charger to the bag and your phone will start charging.<\/span><\/li><li style=\"list-style: disc; overflow-wrap: break-word; margin: 0px;\"><span class=\"a-list-item\">Capacity: 30 liters; Weight: 550 grams; Dimensions: 33 cms x 22 cms x 44 cms (LxWxH)<\/span><\/li><\/ul>","merchant_id":2,"category_id":"11","brand_id":3,"images":"uploaded/products/9847715726135010.jpeg","modifier_id":11,"selling_price":"50.00","buy_price":"30.00","avl_quantity":100,"modifier_images":null,"venue_id":"201911011148462","venue_name":"LillyWhites","venue_images":"1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg,20191121064555.lillywhites.jpg,20191121064555.lillywhites-sports-shopdepartment-store-londons-west-end-london-engtand-DA53PJ.jpg,,,","country":"United Kingdom","address_1":"Oaklands Road, Wolverhampton WV3 0DS, England, UK","address_2":null,"city_name":"Wolverhampton","post_code":"WV3 0DS","delivery":1,"collection":1,"latitude":"52.5756465","longitude":"-2.1385980000000018","stars":0,"distance":"3,634.56"}]
     * venues : [{"venue_id":"201911011148462","venue_name":"LillyWhites","merchant_id":2,"venue_images":"1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg","address_1":"Oaklands Road, Wolverhampton WV3 0DS, England, UK","address_2":null,"city_name":"Wolverhampton","country":"United Kingdom","post_code":"WV3 0DS","delivery":1,"collection":1,"opening_time":"06:15","closing_time":"20:30","distance":"3,634.56","stars":"2.7","total_offers":0},{"venue_id":"2019121606413511","venue_name":"Wolverhamton Morrions","merchant_id":11,"venue_images":"","address_1":"Bilston WV14 0DZ, UK","address_2":null,"city_name":"Wolverhampton","country":"United Kingdom","post_code":"WV14 0DZ","delivery":1,"collection":1,"latitude":"52.56342480000001","longitude":"-2.074979100000064","opening_time":"09:00","closing_time":"15:00","distance":"3,633.59","stars":"2.3","total_offers":0}]
     * products_offer : [{"offer_id":10,"offer_type":"discper","disc_per":"10.00","disc_amt":"0.00","offer_title":"10% off on vegetable","start_date":"2019-12-16","end_date":"2020-01-31","offer_time_start":"00:00:00","offer_time_end":"23:55:00","type":1,"discount_type":1,"discount_amount":"10.00","product_id":94,"merchant_id":11,"venue_id":"2019121606413511","product_name":"Asparagus Tips","product_description":"An 80g (1 medium banana) serving counts as 1 portion 5 a day fruit and vegetables.\nAim to eat at least 5 portions of different fruit and vegetables each day.\nRemember that fresh, frozen, dried, canned and juice all count!\n\n80g 1 portion 5 a day, Rainforest Alliance certified, A healthy and filling family fruit bowl favourite","product_images":"uploaded/products/9938715764972390.jpeg","modifier_id":162,"buy_price":"0.17","selling_price":"0.24","avl_quantity":10012,"modifier_images":"0","venue_name":"Wolverhamton Morrions","distance":"3,633.59","stars":0}]
     */

    private boolean status;
    private String message;
    private List<CategoryBean> category;
    private List<ProductsBean> products;
    private List<VenuesBean> venues;
    private List<ProductsOfferBean> products_offer;

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

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public List<VenuesBean> getVenues() {
        return venues;
    }

    public void setVenues(List<VenuesBean> venues) {
        this.venues = venues;
    }

    public List<ProductsOfferBean> getProducts_offer() {
        return products_offer;
    }

    public void setProducts_offer(List<ProductsOfferBean> products_offer) {
        this.products_offer = products_offer;
    }

    public static class CategoryBean {
        /**
         * id : 32
         * cat_name : Ayurveda
         * parent_cat_id : 7
         * breadcrumb : 7
         * color : 64e6a9
         * menu_level : 2
         */

        private int id;
        private String cat_name;
        private int parent_cat_id;
        private String breadcrumb;
        private String color;
        private int menu_level;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public int getParent_cat_id() {
            return parent_cat_id;
        }

        public void setParent_cat_id(int parent_cat_id) {
            this.parent_cat_id = parent_cat_id;
        }

        public String getBreadcrumb() {
            return breadcrumb;
        }

        public void setBreadcrumb(String breadcrumb) {
            this.breadcrumb = breadcrumb;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getMenu_level() {
            return menu_level;
        }

        public void setMenu_level(int menu_level) {
            this.menu_level = menu_level;
        }
    }

    public static class ProductsBean {
        /**
         * id : 11
         * product_name : Fur Jaden Anti Theft Zipper
         * product_description : <ul class="a-unordered-list a-vertical a-spacing-none" style="margin-right: 0px; margin-bottom: 0px; margin-left: 18px; color: rgb(17, 17, 17); padding: 0px; font-family: &quot;Amazon Ember&quot;, Arial, sans-serif; font-size: 13px;"><li style="list-style: disc; overflow-wrap: break-word; margin: 0px;"><span class="a-list-item">Style - Brown colour backpack for men made of PU leather for stylish office use as well as for trendy college students. Huge compartment size that can fit upto a 15 inch laptop with ease. Multiple pockets for other accessories and water bottle. Please note Zips are not Water Resistant.</span></li><li style="list-style: disc; overflow-wrap: break-word; margin: 0px;"><span class="a-list-item">USB Charging - Now charge your phone on the go while travelling without the hassle of holding the bulky power bank in your pocket or in your hand. Simple Connect your Power bank to the wire inside the Bag and leave it in the bag. When you want to charge your phone, just connect your phone charger to the bag and your phone will start charging.</span></li><li style="list-style: disc; overflow-wrap: break-word; margin: 0px;"><span class="a-list-item">Capacity: 30 liters; Weight: 550 grams; Dimensions: 33 cms x 22 cms x 44 cms (LxWxH)</span></li></ul>
         * merchant_id : 2
         * category_id : 11
         * brand_id : 3
         * images : uploaded/products/9847715726135010.jpeg
         * modifier_id : 11
         * selling_price : 50.00
         * buy_price : 30.00
         * avl_quantity : 100
         * modifier_images : null
         * venue_id : 201911011148462
         * venue_name : LillyWhites
         * venue_images : 1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg,20191121064555.lillywhites.jpg,20191121064555.lillywhites-sports-shopdepartment-store-londons-west-end-london-engtand-DA53PJ.jpg,,,
         * country : United Kingdom
         * address_1 : Oaklands Road, Wolverhampton WV3 0DS, England, UK
         * address_2 : null
         * city_name : Wolverhampton
         * post_code : WV3 0DS
         * delivery : 1
         * collection : 1
         * latitude : 52.5756465
         * longitude : -2.1385980000000018
         * stars : 0
         * distance : 3,634.56
         */

        private int id;
        private String product_name;
        private String product_description;
        private int merchant_id;
        private String category_id;
        private int brand_id;
        private String images;
        private int modifier_id;
        private String selling_price;
        private String buy_price;
        private int avl_quantity;
        private Object modifier_images;
        private String venue_id;
        private String venue_name;
        private String venue_images;
        private String country;
        private String address_1;
        private Object address_2;
        private String city_name;
        private String post_code;
        private int delivery;
        private int collection;
        private String latitude;
        private String longitude;
        private int stars;
        private String distance;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_description() {
            return product_description;
        }

        public void setProduct_description(String product_description) {
            this.product_description = product_description;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public int getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(int modifier_id) {
            this.modifier_id = modifier_id;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public String getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(String buy_price) {
            this.buy_price = buy_price;
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

        public String getVenue_images() {
            return venue_images;
        }

        public void setVenue_images(String venue_images) {
            this.venue_images = venue_images;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public Object getAddress_2() {
            return address_2;
        }

        public void setAddress_2(Object address_2) {
            this.address_2 = address_2;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
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

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }

    public static class VenuesBean {
        /**
         * venue_id : 201911011148462
         * venue_name : LillyWhites
         * merchant_id : 2
         * venue_images : 1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg
         * address_1 : Oaklands Road, Wolverhampton WV3 0DS, England, UK
         * address_2 : null
         * city_name : Wolverhampton
         * country : United Kingdom
         * post_code : WV3 0DS
         * delivery : 1
         * collection : 1
         * opening_time : 06:15
         * closing_time : 20:30
         * distance : 3,634.56
         * stars : 2.7
         * total_offers : 0
         * latitude : 52.56342480000001
         * longitude : -2.074979100000064
         */

        private String venue_id;
        private String venue_name;
        private int merchant_id;
        private String venue_images;
        private String address_1;
        private Object address_2;
        private String city_name;
        private String country;
        private String post_code;
        private int delivery;
        private int collection;
        private String opening_time;
        private String closing_time;
        private String distance;
        private String stars;
        private int total_offers;
        private String latitude;
        private String longitude;

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

        public String getVenue_images() {
            return venue_images;
        }

        public void setVenue_images(String venue_images) {
            this.venue_images = venue_images;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public Object getAddress_2() {
            return address_2;
        }

        public void setAddress_2(Object address_2) {
            this.address_2 = address_2;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
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

        public String getOpening_time() {
            return opening_time;
        }

        public void setOpening_time(String opening_time) {
            this.opening_time = opening_time;
        }

        public String getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(String closing_time) {
            this.closing_time = closing_time;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getTotal_offers() {
            return total_offers;
        }

        public void setTotal_offers(int total_offers) {
            this.total_offers = total_offers;
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
    }

    public static class ProductsOfferBean {
        /**
         * offer_id : 10
         * offer_type : discper
         * disc_per : 10.00
         * disc_amt : 0.00
         * offer_title : 10% off on vegetable
         * start_date : 2019-12-16
         * end_date : 2020-01-31
         * offer_time_start : 00:00:00
         * offer_time_end : 23:55:00
         * type : 1
         * discount_type : 1
         * discount_amount : 10.00
         * product_id : 94
         * merchant_id : 11
         * venue_id : 2019121606413511
         * product_name : Asparagus Tips
         * product_description : An 80g (1 medium banana) serving counts as 1 portion 5 a day fruit and vegetables.
         Aim to eat at least 5 portions of different fruit and vegetables each day.
         Remember that fresh, frozen, dried, canned and juice all count!

         80g 1 portion 5 a day, Rainforest Alliance certified, A healthy and filling family fruit bowl favourite
         * product_images : uploaded/products/9938715764972390.jpeg
         * modifier_id : 162
         * buy_price : 0.17
         * selling_price : 0.24
         * avl_quantity : 10012
         * modifier_images : 0
         * venue_name : Wolverhamton Morrions
         * distance : 3,633.59
         * stars : 0
         */

        private int offer_id;
        private String offer_type;
        private String disc_per;
        private String disc_amt;
        private String offer_title;
        private String start_date;
        private String end_date;
        private String offer_time_start;
        private String offer_time_end;
        private int type;
        private int discount_type;
        private String discount_amount;
        private int product_id;
        private int merchant_id;
        private String venue_id;
        private String product_name;
        private String product_description;
        private String product_images;
        private int modifier_id;
        private String buy_price;
        private String selling_price;
        private int avl_quantity;
        private String modifier_images;
        private String venue_name;
        private String distance;
        private int stars;

        public int getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(int offer_id) {
            this.offer_id = offer_id;
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

        public String getOffer_title() {
            return offer_title;
        }

        public void setOffer_title(String offer_title) {
            this.offer_title = offer_title;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getOffer_time_start() {
            return offer_time_start;
        }

        public void setOffer_time_start(String offer_time_start) {
            this.offer_time_start = offer_time_start;
        }

        public String getOffer_time_end() {
            return offer_time_end;
        }

        public void setOffer_time_end(String offer_time_end) {
            this.offer_time_end = offer_time_end;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDiscount_type() {
            return discount_type;
        }

        public void setDiscount_type(int discount_type) {
            this.discount_type = discount_type;
        }

        public String getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
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

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_description() {
            return product_description;
        }

        public void setProduct_description(String product_description) {
            this.product_description = product_description;
        }

        public String getProduct_images() {
            return product_images;
        }

        public void setProduct_images(String product_images) {
            this.product_images = product_images;
        }

        public int getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(int modifier_id) {
            this.modifier_id = modifier_id;
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

        public String getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(String modifier_images) {
            this.modifier_images = modifier_images;
        }

        public String getVenue_name() {
            return venue_name;
        }

        public void setVenue_name(String venue_name) {
            this.venue_name = venue_name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }
    }
}
