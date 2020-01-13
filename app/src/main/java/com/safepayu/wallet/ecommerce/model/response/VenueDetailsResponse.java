package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class VenueDetailsResponse {


    /**
     * status : true
     * message : Venue Details page Information!
     * venueDetails : {"id":1,"merchant_id":1,"venue_id":"201910311251401","venue_name":"Nando wolverhampton","venue_images":"20191111135857.Nando\u2019s-Job-Application.jpg","address_1":"Oaklands Road, Wolverhampton WV3 0DS, UK","address_2":null,"city_name":"Wolverhampton","country":"United Kingdom","post_code":"110014","phone_number":"123456765","venue_email":"richy@vmailcloud.com","venue_website":null,"home_delivery_mov":"100.00","start_days":1,"end_days":10,"collection_time":120,"preparation_time":70,"free_delivery":"180.00","delivery_charge":"20.00","delivery":1,"collection":1,"latitude":"52.5756465","longitude":"-2.1385980000000018","today_opening_time":"05:00","today_closing_time":"20:00","distance":3634.56089347916,"venueRatingCount":0,"venueRating":0}
     * CategoryList : [{"id":2,"cat_name":"Fashion","parent_cat_id":0},{"id":3,"cat_name":"Electronics","parent_cat_id":0,"sub_cat":[{"id":5,"cat_name":"Laptop","parent_cat_id":3},{"id":4,"cat_name":"Mobile","parent_cat_id":3},{"id":6,"cat_name":"Tablet","parent_cat_id":3}]},{"id":1,"cat_name":"Sports","parent_cat_id":0},{"id":7,"cat_name":"Beauty","parent_cat_id":0},{"id":10,"cat_name":"Grocery","parent_cat_id":0},{"id":8,"cat_name":"Furniture","parent_cat_id":0},{"id":12,"cat_name":"Home & Kitchen","parent_cat_id":0},{"id":9,"cat_name":"Jewellery","parent_cat_id":0},{"id":11,"cat_name":"Luggage & Bags","parent_cat_id":0},{"id":13,"cat_name":"Mobile Accessories","parent_cat_id":0},{"id":14,"cat_name":"iPads","parent_cat_id":0},{"id":15,"cat_name":"Men Grooming","parent_cat_id":0},{"id":16,"cat_name":"Musical Instrument","parent_cat_id":0},{"id":17,"cat_name":"Video Games","parent_cat_id":0},{"id":18,"cat_name":"Laptop Accessories","parent_cat_id":0},{"id":19,"cat_name":"DSLR","parent_cat_id":0}]
     * productList : []
     * brands : []
     * closing_day : ["20:00","20:00","20:00","20:00","20:00","20:00","20:00"]
     * opening_day : ["05:00","05:00","05:00","05:00","05:00","05:00","05:00"]
     */

    private boolean status;
    private String message;
    private VenueDetailsBean venueDetails;
    private List<CategoryListBean> CategoryList;
    private List<?> productList;
    private List<?> brands;
    private List<String> closing_day;
    private List<String> opening_day;

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

    public VenueDetailsBean getVenueDetails() {
        return venueDetails;
    }

    public void setVenueDetails(VenueDetailsBean venueDetails) {
        this.venueDetails = venueDetails;
    }

    public List<CategoryListBean> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<CategoryListBean> CategoryList) {
        this.CategoryList = CategoryList;
    }

    public List<?> getProductList() {
        return productList;
    }

    public void setProductList(List<?> productList) {
        this.productList = productList;
    }

    public List<?> getBrands() {
        return brands;
    }

    public void setBrands(List<?> brands) {
        this.brands = brands;
    }

    public List<String> getClosing_day() {
        return closing_day;
    }

    public void setClosing_day(List<String> closing_day) {
        this.closing_day = closing_day;
    }

    public List<String> getOpening_day() {
        return opening_day;
    }

    public void setOpening_day(List<String> opening_day) {
        this.opening_day = opening_day;
    }

    public static class VenueDetailsBean {
        /**
         * id : 1
         * merchant_id : 1
         * venue_id : 201910311251401
         * venue_name : Nando wolverhampton
         * venue_images : 20191111135857.Nando’s-Job-Application.jpg
         * address_1 : Oaklands Road, Wolverhampton WV3 0DS, UK
         * address_2 : null
         * city_name : Wolverhampton
         * country : United Kingdom
         * post_code : 110014
         * phone_number : 123456765
         * venue_email : richy@vmailcloud.com
         * venue_website : null
         * home_delivery_mov : 100.00
         * start_days : 1
         * end_days : 10
         * collection_time : 120
         * preparation_time : 70
         * free_delivery : 180.00
         * delivery_charge : 20.00
         * delivery : 1
         * collection : 1
         * latitude : 52.5756465
         * longitude : -2.1385980000000018
         * today_opening_time : 05:00
         * today_closing_time : 20:00
         * distance : 3634.56089347916
         * venueRatingCount : 0
         * venueRating : 0
         */

        private int id;
        private int merchant_id;
        private String venue_id;
        private String venue_name;
        private String venue_images;
        private String address_1;
        private Object address_2;
        private String city_name;
        private String country;
        private String post_code;
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
        private String latitude;
        private String longitude;
        private String today_opening_time;
        private String today_closing_time;
        private double distance;
        private int venueRatingCount;
        private int venueRating;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getToday_opening_time() {
            return today_opening_time;
        }

        public void setToday_opening_time(String today_opening_time) {
            this.today_opening_time = today_opening_time;
        }

        public String getToday_closing_time() {
            return today_closing_time;
        }

        public void setToday_closing_time(String today_closing_time) {
            this.today_closing_time = today_closing_time;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getVenueRatingCount() {
            return venueRatingCount;
        }

        public void setVenueRatingCount(int venueRatingCount) {
            this.venueRatingCount = venueRatingCount;
        }

        public int getVenueRating() {
            return venueRating;
        }

        public void setVenueRating(int venueRating) {
            this.venueRating = venueRating;
        }
    }

    public static class CategoryListBean {
        /**
         * id : 2
         * cat_name : Fashion
         * parent_cat_id : 0
         * sub_cat : [{"id":5,"cat_name":"Laptop","parent_cat_id":3},{"id":4,"cat_name":"Mobile","parent_cat_id":3},{"id":6,"cat_name":"Tablet","parent_cat_id":3}]
         */

        private int id;
        private String cat_name;
        private int parent_cat_id;
        private List<SubCatBean> sub_cat;

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

        public List<SubCatBean> getSub_cat() {
            return sub_cat;
        }

        public void setSub_cat(List<SubCatBean> sub_cat) {
            this.sub_cat = sub_cat;
        }

        public static class SubCatBean {
            /**
             * id : 5
             * cat_name : Laptop
             * parent_cat_id : 3
             */

            private int id;
            private String cat_name;
            private int parent_cat_id;

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
        }
    }
}
