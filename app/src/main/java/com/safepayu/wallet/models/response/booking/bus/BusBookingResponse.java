package com.safepayu.wallet.models.response.booking.bus;

public class BusBookingResponse {

    /**
     * status : true
     * message : Bus ticket(s) booked Successfully
     * data : {"OperatorPNR":null,"ReferenceNo":"100301004988","APIReferenceNo":"00114950","Message":"SUCCESS","BookingStatus":3,"ResponseStatus":200,"RefundResponse":null,"Provider":null}
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
         * OperatorPNR : null
         * ReferenceNo : 100301004988
         * APIReferenceNo : 00114950
         * Message : SUCCESS
         * BookingStatus : 3
         * ResponseStatus : 200
         * RefundResponse : null
         * Provider : null
         */

        private Object OperatorPNR;
        private String ReferenceNo;
        private String APIReferenceNo;
        private String Message;
        private int BookingStatus;
        private int ResponseStatus;
        private Object RefundResponse;
        private Object Provider;

        public Object getOperatorPNR() {
            return OperatorPNR;
        }

        public void setOperatorPNR(Object OperatorPNR) {
            this.OperatorPNR = OperatorPNR;
        }

        public String getReferenceNo() {
            return ReferenceNo;
        }

        public void setReferenceNo(String ReferenceNo) {
            this.ReferenceNo = ReferenceNo;
        }

        public String getAPIReferenceNo() {
            return APIReferenceNo;
        }

        public void setAPIReferenceNo(String APIReferenceNo) {
            this.APIReferenceNo = APIReferenceNo;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public int getBookingStatus() {
            return BookingStatus;
        }

        public void setBookingStatus(int BookingStatus) {
            this.BookingStatus = BookingStatus;
        }

        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }

        public Object getRefundResponse() {
            return RefundResponse;
        }

        public void setRefundResponse(Object RefundResponse) {
            this.RefundResponse = RefundResponse;
        }

        public Object getProvider() {
            return Provider;
        }

        public void setProvider(Object Provider) {
            this.Provider = Provider;
        }
    }
}
