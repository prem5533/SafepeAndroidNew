package com.safepayu.wallet.models.response;

import java.util.List;

public class NotificationResponse {

    /**
     * status : true
     * message : All Notification Data.
     * data : [{"id":3,"userid":"u8376097766","title":"new","description":"Hello team live test message","link":null,"image":null,"created_at":"2019-10-17 13:24:04","updated_at":"2019-10-17 13:24:04"},{"id":6,"userid":"u8376097766","title":"new","description":"Hello team live test message","link":null,"image":null,"created_at":"2019-10-17 13:25:31","updated_at":"2019-10-17 13:25:31"},{"id":9,"userid":"u8376097766","title":"new","description":"Hello team live test message","link":null,"image":null,"created_at":"2019-10-17 13:26:17","updated_at":"2019-10-17 13:26:17"},{"id":12,"userid":"u8376097766","title":"new","description":"Hello team live test message","link":null,"image":null,"created_at":"2019-10-17 13:27:13","updated_at":"2019-10-17 13:27:13"},{"id":15,"userid":"u8376097766","title":"new","description":"Hello team live test message","link":null,"image":null,"created_at":"2019-10-17 13:27:36","updated_at":"2019-10-17 13:27:36"},{"id":18,"userid":"u8376097766","title":"new","description":"Hello team live test message","link":null,"image":null,"created_at":"2019-10-17 13:27:44","updated_at":"2019-10-17 13:27:44"}]
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
         * id : 3
         * userid : u8376097766
         * title : new
         * description : Hello team live test message
         * link : null
         * image : null
         * created_at : 2019-10-17 13:24:04
         * updated_at : 2019-10-17 13:24:04
         */

        private int id;
        private String userid;
        private String title;
        private String description;
        private Object link;
        private Object image;
        private String created_at;
        private String updated_at;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getLink() {
            return link;
        }

        public void setLink(Object link) {
            this.link = link;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
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
    }
}
