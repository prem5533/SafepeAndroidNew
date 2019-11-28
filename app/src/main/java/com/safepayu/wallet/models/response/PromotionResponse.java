package com.safepayu.wallet.models.response;

import java.util.List;

public class PromotionResponse {


    /**
     * status : true
     * message : Images found
     * data : [{"id":1,"image":"uploaded/operator/airtel.jpg","type":1,"status":"0","created_at":null,"updated_at":null},{"id":2,"image":"uploaded/promotional/1574918703banner2.png","type":1,"status":"0","created_at":"2019-11-28 10:55:03","updated_at":"2019-11-28 10:55:03"}]
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
         * id : 1
         * image : uploaded/operator/airtel.jpg
         * type : 1
         * status : 0
         * created_at : null
         * updated_at : null
         */

        private int id;
        private String image;
        private int type;
        private String status;
        private Object created_at;
        private Object updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

