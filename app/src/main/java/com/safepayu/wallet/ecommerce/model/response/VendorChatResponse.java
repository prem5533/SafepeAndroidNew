package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class VendorChatResponse {


    /**
     * status : true
     * message :
     * data : [{"id":2,"From":"venue","To":"cust","message":"Your order has been seen by LillyWhites staff and is now being prepared for you. We will message you when its ready for collection or on its way.\nRelax :) LillyWhites","order_id":"539","venue_id":"201911011148462","merchant_id":2,"user_id":8,"cust_type":"app","is_read":0,"ecom_is_read":1,"staff_id":3,"staff_name":"TommyHilson","prevmsg":506,"nextmsg":0,"created_at":"2019-11-01 11:48:46","updated_at":"2019-12-31 07:16:36","VenueId":"201911011148462","stripe_account_id":"acct_1Fix5dDQuqS7d1S7","stripe_docs_id":"","kyc_status":"pending","fire_base_id":"deqQMNlQtYw:APA91bEMKRI3RsZLZ4ddCPxOoEc77293MDWar_96VFFNfePnT08kWxqXxNQGav7NNIg79tW0h88ZMN_svH9x3eav3o4k1_LE794mf5SpaAHG4VvzucqaJdCGPoMikJt3GgvSQZ9rI8Q8","venue_name":"LillyWhites","address_1":"Oaklands Road, Wolverhampton WV3 0DS, England, UK","address_2":null,"post_code":"WV3 0DS","city_name":"Wolverhampton","country":"United Kingdom","phone_number":"5468496484","contact_person":"Shane","registration_no":"987978787","vat_no":"97858989","subscription_charges":"10.00","status":1,"venue_email":"whiteslilly@dmailpro.net","venue_pwd":"$2y$10$j9r9iH2VZgxlmq68heVHBOubT4qy.Vma4Z4CQNrnxll00T0oNX8Eq","venue_website":"https://www.lillywhites.com/","venue_category":"lilly","service_charge":null,"table_service":null,"home_delivery_mov":"0.30","home_delivery":1,"start_days":1,"end_days":2,"collection_time":20,"preparation_time":30,"delivery":1,"collection":1,"free_delivery":"0.30","delivery_charge":"0.30","terms_conditions":"TNC","stripe_docs":null,"room_service":null,"tab_service":null,"item_booking":null,"custom_discounts":null,"kitchen_receipt_change":null,"tips_enable":null,"cold_enable":null,"takeaway_enable":null,"marketman_service":null,"venue_images":"1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg,20191121064555.lillywhites.jpg,20191121064555.lillywhites-sports-shopdepartment-store-londons-west-end-london-engtand-DA53PJ.jpg,,,","sameforall":0,"mon_opened":null,"tue_opened":null,"wed_opened":null,"thu_opened":null,"fri_opened":null,"sat_opened":null,"sun_opened":null,"merchant_fee_swoope":null,"user_fee_swoope":null,"total_fee_swoope":null,"merchant_fee_ecom":null,"user_fee_ecom":null,"total_fee_ecom":null,"latitude":"52.5756465","longitude":"-2.1385980000000018","delivery_distance":0,"mob_commerce":0,"web_commerce":0,"exact":null,"payment_gatway":"stripe"}]
     */

    private boolean status;
    private List<DataBean> data;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return message;
    }

    public void setMessages(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * id : 2
         * From : venue
         * To : cust
         * message : Your order has been seen by LillyWhites staff and is now being prepared for you. We will message you when its ready for collection or on its way.
         Relax :) LillyWhites
         * order_id : 539
         * venue_id : 201911011148462
         * merchant_id : 2
         * user_id : 8
         * cust_type : app
         * is_read : 0
         * ecom_is_read : 1
         * staff_id : 3
         * staff_name : TommyHilson
         * prevmsg : 506
         * nextmsg : 0
         * created_at : 2019-11-01 11:48:46
         * updated_at : 2019-12-31 07:16:36
         * VenueId : 201911011148462
         * stripe_account_id : acct_1Fix5dDQuqS7d1S7
         * stripe_docs_id :
         * kyc_status : pending
         * fire_base_id : deqQMNlQtYw:APA91bEMKRI3RsZLZ4ddCPxOoEc77293MDWar_96VFFNfePnT08kWxqXxNQGav7NNIg79tW0h88ZMN_svH9x3eav3o4k1_LE794mf5SpaAHG4VvzucqaJdCGPoMikJt3GgvSQZ9rI8Q8
         * venue_name : LillyWhites
         * address_1 : Oaklands Road, Wolverhampton WV3 0DS, England, UK
         * address_2 : null
         * post_code : WV3 0DS
         * city_name : Wolverhampton
         * country : United Kingdom
         * phone_number : 5468496484
         * contact_person : Shane
         * registration_no : 987978787
         * vat_no : 97858989
         * subscription_charges : 10.00
         * status : 1
         * venue_email : whiteslilly@dmailpro.net
         * venue_pwd : $2y$10$j9r9iH2VZgxlmq68heVHBOubT4qy.Vma4Z4CQNrnxll00T0oNX8Eq
         * venue_website : https://www.lillywhites.com/
         * venue_category : lilly
         * service_charge : null
         * table_service : null
         * home_delivery_mov : 0.30
         * home_delivery : 1
         * start_days : 1
         * end_days : 2
         * collection_time : 20
         * preparation_time : 30
         * delivery : 1
         * collection : 1
         * free_delivery : 0.30
         * delivery_charge : 0.30
         * terms_conditions : TNC
         * stripe_docs : null
         * room_service : null
         * tab_service : null
         * item_booking : null
         * custom_discounts : null
         * kitchen_receipt_change : null
         * tips_enable : null
         * cold_enable : null
         * takeaway_enable : null
         * marketman_service : null
         * venue_images : 1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg,20191121064555.lillywhites.jpg,20191121064555.lillywhites-sports-shopdepartment-store-londons-west-end-london-engtand-DA53PJ.jpg,,,
         * sameforall : 0
         * mon_opened : null
         * tue_opened : null
         * wed_opened : null
         * thu_opened : null
         * fri_opened : null
         * sat_opened : null
         * sun_opened : null
         * merchant_fee_swoope : null
         * user_fee_swoope : null
         * total_fee_swoope : null
         * merchant_fee_ecom : null
         * user_fee_ecom : null
         * total_fee_ecom : null
         * latitude : 52.5756465
         * longitude : -2.1385980000000018
         * delivery_distance : 0
         * mob_commerce : 0
         * web_commerce : 0
         * exact : null
         * payment_gatway : stripe
         */

        private int id;
        private String From;
        private String To;
        private String message;
        private String order_id;
        private String venue_id;
        private int merchant_id;
        private int user_id;
        private String cust_type;
        private String created_at;
        private String updated_at;
        private String VenueId;
        private String stripe_account_id;
        private String kyc_status;
        private String venue_name;
        private String address_1;
        private String post_code;
        private String city_name;
        private String country;
        private String phone_number;
        private String contact_person;
        private String registration_no;
        private String vat_no;
        private int status;
        private String venue_email;
        private String venue_images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFrom() {
            return From;
        }

        public void setFrom(String From) {
            this.From = From;
        }

        public String getTo() {
            return To;
        }

        public void setTo(String To) {
            this.To = To;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCust_type() {
            return cust_type;
        }

        public void setCust_type(String cust_type) {
            this.cust_type = cust_type;
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

        public String getVenueId() {
            return VenueId;
        }

        public void setVenueId(String VenueId) {
            this.VenueId = VenueId;
        }

        public String getStripe_account_id() {
            return stripe_account_id;
        }

        public void setStripe_account_id(String stripe_account_id) {
            this.stripe_account_id = stripe_account_id;
        }

        public String getKyc_status() {
            return kyc_status;
        }

        public void setKyc_status(String kyc_status) {
            this.kyc_status = kyc_status;
        }

        public String getVenue_name() {
            return venue_name;
        }

        public void setVenue_name(String venue_name) {
            this.venue_name = venue_name;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
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

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getContact_person() {
            return contact_person;
        }

        public void setContact_person(String contact_person) {
            this.contact_person = contact_person;
        }

        public String getRegistration_no() {
            return registration_no;
        }

        public void setRegistration_no(String registration_no) {
            this.registration_no = registration_no;
        }

        public String getVat_no() {
            return vat_no;
        }

        public void setVat_no(String vat_no) {
            this.vat_no = vat_no;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getVenue_email() {
            return venue_email;
        }

        public void setVenue_email(String venue_email) {
            this.venue_email = venue_email;
        }

        public String getVenue_images() {
            return venue_images;
        }

        public void setVenue_images(String venue_images) {
            this.venue_images = venue_images;
        }
    }
}
