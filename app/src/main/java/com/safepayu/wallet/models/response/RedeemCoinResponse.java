package com.safepayu.wallet.models.response;

public class RedeemCoinResponse {

    /**
     * status : true
     * message : Data retrieved successfully
     * data : {"id":1,"userid":"u8375890846","amount":17,"created_at":"2019-12-18 09:39:44","updated_at":"2019-12-19 15:16:52"}
     */

    private boolean status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * userid : u8375890846
         * amount : 17
         * created_at : 2019-12-18 09:39:44
         * updated_at : 2019-12-19 15:16:52
         */

        private int id;
        private String userid;
        private int amount;
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

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
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
