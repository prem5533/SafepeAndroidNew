package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class ChatListResponse {

    /**
     * status : true
     * message : Customer Queries retrieved successfully
     * data : [{"id":1,"userid":"u9811871855","title":"Hello","message":"Whats up?? 1","reply":null,"status":0,"created_at":"2020-01-23 15:54:55","updated_at":"2020-01-23 15:54:55","from":"1","attachment":null},{"id":2,"userid":"u9811871855","title":"Hello","message":"Admin","reply":null,"status":0,"created_at":"2020-01-23 19:27:54","updated_at":"2020-01-23 19:27:54","from":"2","attachment":null},{"id":3,"userid":"u9811871855","title":"Hello","message":"Whats up?? 2","reply":null,"status":0,"created_at":"2020-01-23 19:28:47","updated_at":"2020-01-23 19:28:47","from":"1","attachment":null},{"id":4,"userid":"u9811871855","title":"Hello","message":"Admin","reply":null,"status":0,"created_at":"2020-01-23 19:28:48","updated_at":"2020-01-23 19:28:48","from":"2","attachment":null},{"id":5,"userid":"u9811871855","title":"Message","message":"Adarsh Kumar","reply":null,"status":0,"created_at":"2020-01-23 19:35:57","updated_at":"2020-01-23 19:35:57","from":"1","attachment":null},{"id":6,"userid":"u9811871855","title":"Message","message":"gabsjsjnsjsnsnz","reply":null,"status":0,"created_at":"2020-01-23 19:47:57","updated_at":"2020-01-23 19:47:57","from":"1","attachment":null},{"id":7,"userid":"u9811871855","title":"Message","message":"good morning\nhello","reply":null,"status":0,"created_at":"2020-01-24 10:52:49","updated_at":"2020-01-24 10:52:49","from":"1","attachment":null},{"id":8,"userid":"u9811871855","title":"Message","message":"kya haal","reply":null,"status":0,"created_at":"2020-01-24 10:53:11","updated_at":"2020-01-24 10:53:11","from":"1","attachment":null},{"id":9,"userid":"u9811871855","title":"Message","message":"hehehhe","reply":null,"status":0,"created_at":"2020-01-24 11:19:28","updated_at":"2020-01-24 11:19:28","from":"1","attachment":null},{"id":10,"userid":"u9811871855","title":"Message","message":"gagahsbbs","reply":null,"status":0,"created_at":"2020-01-24 11:19:40","updated_at":"2020-01-24 11:19:40","from":"1","attachment":null},{"id":12,"userid":"u9811871855","title":"Message","message":"gagaggsbs","reply":null,"status":0,"created_at":"2020-01-24 11:38:44","updated_at":"2020-01-24 11:38:44","from":"1","attachment":null},{"id":13,"userid":"u9811871855","title":"Message","message":"😁😁😁","reply":null,"status":0,"created_at":"2020-01-24 11:38:56","updated_at":"2020-01-24 11:38:56","from":"1","attachment":null},{"id":14,"userid":"u9811871855","title":"Message","message":"adarshmandal515@gmail.com","reply":null,"status":0,"created_at":"2020-01-24 11:39:13","updated_at":"2020-01-24 11:39:13","from":"1","attachment":null},{"id":15,"userid":"u9811871855","title":"Message","message":"adarshmandal515@gmail.com","reply":null,"status":1,"created_at":"2020-01-24 11:40:50","updated_at":"2020-01-24 12:12:38","from":"1","attachment":null},{"id":17,"userid":"u9811871855","title":"Message","message":"😍😍😍","reply":null,"status":0,"created_at":"2020-01-24 11:55:32","updated_at":"2020-01-24 11:55:32","from":"1","attachment":null},{"id":18,"userid":"u9811871855","title":"Message","message":"affaga","reply":null,"status":0,"created_at":"2020-01-24 11:55:40","updated_at":"2020-01-24 11:55:40","from":"1","attachment":null},{"id":19,"userid":"u9811871855","title":"Message","message":"tesvhjk","reply":null,"status":0,"created_at":"2020-01-24 12:04:58","updated_at":"2020-01-24 12:04:58","from":"1","attachment":null},{"id":20,"userid":"u9811871855","title":"Message","message":"tgbju","reply":null,"status":0,"created_at":"2020-01-24 12:08:08","updated_at":"2020-01-24 12:08:08","from":"1","attachment":null},{"id":23,"userid":"u9811871855","title":"Message","message":"Hello","reply":null,"status":1,"created_at":"2020-01-24 12:12:38","updated_at":"2020-01-24 12:12:38","from":"1","attachment":null}]
     * title : [{"id":1,"title":"Recharge","created_at":null,"updated_at":null},{"id":2,"title":"Package","created_at":null,"updated_at":null},{"id":3,"title":"Direct Income","created_at":null,"updated_at":null},{"id":4,"title":"Binary Incme","created_at":null,"updated_at":null},{"id":5,"title":"Flight Booking","created_at":null,"updated_at":null},{"id":6,"title":"Bus Booking","created_at":null,"updated_at":null}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;
    private List<TitleBean> title;

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

    public List<TitleBean> getTitle() {
        return title;
    }

    public void setTitle(List<TitleBean> title) {
        this.title = title;
    }

    public static class DataBean {
        /**
         * id : 1
         * userid : u9811871855
         * title : Hello
         * message : Whats up?? 1
         * reply : null
         * status : 0
         * created_at : 2020-01-23 15:54:55
         * updated_at : 2020-01-23 15:54:55
         * from : 1
         * attachment : null
         */

        private int id;
        private String userid;
        private String title;
        private String message;
        private Object reply;
        private int status;
        private String created_at;
        private String updated_at;
        private String from;
        private Object attachment;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getReply() {
            return reply;
        }

        public void setReply(Object reply) {
            this.reply = reply;
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

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public Object getAttachment() {
            return attachment;
        }

        public void setAttachment(Object attachment) {
            this.attachment = attachment;
        }
    }

    public static class TitleBean {
        /**
         * id : 1
         * title : Recharge
         * created_at : null
         * updated_at : null
         */

        private int id;
        private String title;
        private Object created_at;
        private Object updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Object created_at) {
            this.created_at = created_at;
        }

        public Object getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Object updated_at) {
            this.updated_at = updated_at;
        }
    }
}
