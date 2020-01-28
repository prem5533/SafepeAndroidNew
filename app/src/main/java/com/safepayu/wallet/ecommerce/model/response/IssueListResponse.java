package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class IssueListResponse {


    /**
     * status : true
     * message : Message retrieved successfully
     * data : [{"id":456,"From":"venue","To":"cust","message":"Order has been placed successfully.","order_id":"479","venue_id":"201911011148462","merchant_id":2,"user_id":3,"cust_type":"app","is_read":0,"ecom_is_read":1,"staff_id":"","staff_name":"","prevmsg":0,"nextmsg":0,"created_at":"2019-12-19 07:29:56","updated_at":"2019-12-19 10:54:25","venueid":"201911011148462","venue_name":"LillyWhites","venue_images":"1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg","phone_number":"5468496484","unique_code":"201912190729553"}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 456
         * From : venue
         * To : cust
         * message : Order has been placed successfully.
         * order_id : 479
         * venue_id : 201911011148462
         * merchant_id : 2
         * user_id : 3
         * cust_type : app
         * is_read : 0
         * ecom_is_read : 1
         * staff_id :
         * staff_name :
         * prevmsg : 0
         * nextmsg : 0
         * created_at : 2019-12-19 07:29:56
         * updated_at : 2019-12-19 10:54:25
         * venueid : 201911011148462
         * venue_name : LillyWhites
         * venue_images : 1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg
         * phone_number : 5468496484
         * unique_code : 201912190729553
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
        private int is_read;
        private int ecom_is_read;
        private String staff_id;
        private String staff_name;
        private int prevmsg;
        private int nextmsg;
        private String created_at;
        private String updated_at;
        private String venueid;
        private String venue_name;
        private String venue_images;
        private String phone_number;
        private String unique_code;

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

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public int getEcom_is_read() {
            return ecom_is_read;
        }

        public void setEcom_is_read(int ecom_is_read) {
            this.ecom_is_read = ecom_is_read;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public int getPrevmsg() {
            return prevmsg;
        }

        public void setPrevmsg(int prevmsg) {
            this.prevmsg = prevmsg;
        }

        public int getNextmsg() {
            return nextmsg;
        }

        public void setNextmsg(int nextmsg) {
            this.nextmsg = nextmsg;
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

        public String getVenueid() {
            return venueid;
        }

        public void setVenueid(String venueid) {
            this.venueid = venueid;
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

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getUnique_code() {
            return unique_code;
        }

        public void setUnique_code(String unique_code) {
            this.unique_code = unique_code;
        }
    }
}
