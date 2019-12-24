package com.safepayu.wallet.models.response.booking.hotel;

public class HotelCancelResponse {

    /**
     * status : true
     * mesage : Data retrieved successfully
     * data : {"CancellationId":"I2SOEDCRF","TotalRefundAmount":1610,"CancellationAmount":0,"Error":null,"BookingStatus":5,"Message":"Hotel Room(s) Cancelled Successfully","RefundResponse":{"TransactionId":null,"PaymentId":null,"Message":null,"Amount":0,"Mode":null,"PgRefNo":null,"RefundStatus":2,"RequestDatatime":"0001-01-01T00:00:00"}}
     */

    private boolean status;
    private String mesage;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * CancellationId : I2SOEDCRF
         * TotalRefundAmount : 1610
         * CancellationAmount : 0
         * Error : null
         * BookingStatus : 5
         * Message : Hotel Room(s) Cancelled Successfully
         * RefundResponse : {"TransactionId":null,"PaymentId":null,"Message":null,"Amount":0,"Mode":null,"PgRefNo":null,"RefundStatus":2,"RequestDatatime":"0001-01-01T00:00:00"}
         */

        private String CancellationId;
        private int TotalRefundAmount;
        private int CancellationAmount;
        private Object Error;
        private int BookingStatus;
        private String Message;
        private RefundResponseBean RefundResponse;

        public String getCancellationId() {
            return CancellationId;
        }

        public void setCancellationId(String CancellationId) {
            this.CancellationId = CancellationId;
        }

        public int getTotalRefundAmount() {
            return TotalRefundAmount;
        }

        public void setTotalRefundAmount(int TotalRefundAmount) {
            this.TotalRefundAmount = TotalRefundAmount;
        }

        public int getCancellationAmount() {
            return CancellationAmount;
        }

        public void setCancellationAmount(int CancellationAmount) {
            this.CancellationAmount = CancellationAmount;
        }

        public Object getError() {
            return Error;
        }

        public void setError(Object Error) {
            this.Error = Error;
        }

        public int getBookingStatus() {
            return BookingStatus;
        }

        public void setBookingStatus(int BookingStatus) {
            this.BookingStatus = BookingStatus;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public RefundResponseBean getRefundResponse() {
            return RefundResponse;
        }

        public void setRefundResponse(RefundResponseBean RefundResponse) {
            this.RefundResponse = RefundResponse;
        }

        public static class RefundResponseBean {
            /**
             * TransactionId : null
             * PaymentId : null
             * Message : null
             * Amount : 0
             * Mode : null
             * PgRefNo : null
             * RefundStatus : 2
             * RequestDatatime : 0001-01-01T00:00:00
             */

            private Object TransactionId;
            private Object PaymentId;
            private Object Message;
            private int Amount;
            private Object Mode;
            private Object PgRefNo;
            private int RefundStatus;
            private String RequestDatatime;

            public Object getTransactionId() {
                return TransactionId;
            }

            public void setTransactionId(Object TransactionId) {
                this.TransactionId = TransactionId;
            }

            public Object getPaymentId() {
                return PaymentId;
            }

            public void setPaymentId(Object PaymentId) {
                this.PaymentId = PaymentId;
            }

            public Object getMessage() {
                return Message;
            }

            public void setMessage(Object Message) {
                this.Message = Message;
            }

            public int getAmount() {
                return Amount;
            }

            public void setAmount(int Amount) {
                this.Amount = Amount;
            }

            public Object getMode() {
                return Mode;
            }

            public void setMode(Object Mode) {
                this.Mode = Mode;
            }

            public Object getPgRefNo() {
                return PgRefNo;
            }

            public void setPgRefNo(Object PgRefNo) {
                this.PgRefNo = PgRefNo;
            }

            public int getRefundStatus() {
                return RefundStatus;
            }

            public void setRefundStatus(int RefundStatus) {
                this.RefundStatus = RefundStatus;
            }

            public String getRequestDatatime() {
                return RequestDatatime;
            }

            public void setRequestDatatime(String RequestDatatime) {
                this.RequestDatatime = RequestDatatime;
            }
        }
    }
}
