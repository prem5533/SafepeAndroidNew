package com.safepayu.wallet.ecommerce.model.response;

public class SendChatToVendorResponse {

    /**
     * status : true
     * success : Message Send successfully
     * data : {"From":"cust","To":"venue","message":"dhshwvsvav","order_id":"456","venue_id":"201911011148462","user_id":3,"cust_type":"app","prevmsg":522,"nextmsg":0,"is_read":0,"staff_id":"","staff_name":"","updated_at":"2020-01-27 13:59:01","created_at":"2020-01-27 13:59:01","id":523}
     * message : {"From":"cust","To":"venue","message":"dhshwvsvav","order_id":"456","venue_id":"201911011148462","user_id":3,"cust_type":"app","prevmsg":522,"nextmsg":0,"is_read":0,"staff_id":"","staff_name":"","updated_at":"2020-01-27 13:59:01","created_at":"2020-01-27 13:59:01","id":523}
     */

    private boolean status;
    private String success;
    private DataBean data;
    private MessageBean message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * From : cust
         * To : venue
         * message : dhshwvsvav
         * order_id : 456
         * venue_id : 201911011148462
         * user_id : 3
         * cust_type : app
         * prevmsg : 522
         * nextmsg : 0
         * is_read : 0
         * staff_id :
         * staff_name :
         * updated_at : 2020-01-27 13:59:01
         * created_at : 2020-01-27 13:59:01
         * id : 523
         */

        private String From;
        private String To;
        private String message;
        private String order_id;
        private String venue_id;
        private int user_id;
        private String cust_type;
        private int prevmsg;
        private int nextmsg;
        private int is_read;
        private String staff_id;
        private String staff_name;
        private String updated_at;
        private String created_at;
        private int id;

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

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
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

    public static class MessageBean {
        /**
         * From : cust
         * To : venue
         * message : dhshwvsvav
         * order_id : 456
         * venue_id : 201911011148462
         * user_id : 3
         * cust_type : app
         * prevmsg : 522
         * nextmsg : 0
         * is_read : 0
         * staff_id :
         * staff_name :
         * updated_at : 2020-01-27 13:59:01
         * created_at : 2020-01-27 13:59:01
         * id : 523
         */

        private String From;
        private String To;
        private String message;
        private String order_id;
        private String venue_id;
        private int user_id;
        private String cust_type;
        private int prevmsg;
        private int nextmsg;
        private int is_read;
        private String staff_id;
        private String staff_name;
        private String updated_at;
        private String created_at;
        private int id;

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

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
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
