package com.safepayu.wallet.models.response;

public class RechargeResponse {

    /**
     * status : true
     * statusCode : 1
     * message : Recharge Success
     * Response : {"orderId":1705150249,"status":"SUCCESS","mobile":"8375968388","amount":10,"operatorId":"1912021355280153","error_code":"200","service":"Vodafone","bal":"578.0499","creditUsed":"9.9850","resText":"Recharge Success","billAmount":"","billName":""}
     */

    private boolean status;
    private int statusCode;
    private String message;
    private ResponseBean Response;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseBean getResponse() {
        return Response;
    }

    public void setResponse(ResponseBean Response) {
        this.Response = Response;
    }

    public static class ResponseBean {
        /**
         * orderId : 1705150249
         * status : SUCCESS
         * mobile : 8375968388
         * amount : 10
         * operatorId : 1912021355280153
         * error_code : 200
         * service : Vodafone
         * bal : 578.0499
         * creditUsed : 9.9850
         * resText : Recharge Success
         * billAmount :
         * billName :
         */

        private int orderId;
        private String status;
        private String mobile;
        private int amount;
        private String operatorId;
        private String error_code;
        private String service;
        private String bal;
        private String creditUsed;
        private String resText;
        private String billAmount;
        private String billName;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(String operatorId) {
            this.operatorId = operatorId;
        }

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getBal() {
            return bal;
        }

        public void setBal(String bal) {
            this.bal = bal;
        }

        public String getCreditUsed() {
            return creditUsed;
        }

        public void setCreditUsed(String creditUsed) {
            this.creditUsed = creditUsed;
        }

        public String getResText() {
            return resText;
        }

        public void setResText(String resText) {
            this.resText = resText;
        }

        public String getBillAmount() {
            return billAmount;
        }

        public void setBillAmount(String billAmount) {
            this.billAmount = billAmount;
        }

        public String getBillName() {
            return billName;
        }

        public void setBillName(String billName) {
            this.billName = billName;
        }
    }
}
