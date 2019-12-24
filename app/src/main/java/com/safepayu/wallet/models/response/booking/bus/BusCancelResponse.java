package com.safepayu.wallet.models.response.booking.bus;

public class BusCancelResponse {

    /**
     * status : true
     * message : Seat cancellation details
     * data : {"BookingStatus":5,"TicketNumber":"00115169","TotalTicketFare":2310,"TotalRefundAmount":2000,"Message":"Ticket Cancelled Successfully for seats 41","CancellationCharges":0,"RefundType":"Wallet","isSeatCancellable":false,"PartialCancellationAllowed":false,"RefundResponse":{"TransactionId":null,"PaymentId":null,"Message":null,"Amount":0,"Mode":null,"PgRefNo":null,"RefundStatus":1,"RequestDatatime":"0001-01-01T00:00:00"},"CancellationId":null,"CancellationAmount":0}
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
         * BookingStatus : 5
         * TicketNumber : 00115169
         * TotalTicketFare : 2310
         * TotalRefundAmount : 2000
         * Message : Ticket Cancelled Successfully for seats 41
         * CancellationCharges : 0
         * RefundType : Wallet
         * isSeatCancellable : false
         * PartialCancellationAllowed : false
         * RefundResponse : {"TransactionId":null,"PaymentId":null,"Message":null,"Amount":0,"Mode":null,"PgRefNo":null,"RefundStatus":1,"RequestDatatime":"0001-01-01T00:00:00"}
         * CancellationId : null
         * CancellationAmount : 0
         */

        private int BookingStatus;
        private String TicketNumber;
        private int TotalTicketFare;
        private int TotalRefundAmount;
        private String Message;
        private int CancellationCharges;
        private String RefundType;
        private boolean isSeatCancellable;
        private boolean PartialCancellationAllowed;
        private RefundResponseBean RefundResponse;
        private Object CancellationId;
        private int CancellationAmount;

        public int getBookingStatus() {
            return BookingStatus;
        }

        public void setBookingStatus(int BookingStatus) {
            this.BookingStatus = BookingStatus;
        }

        public String getTicketNumber() {
            return TicketNumber;
        }

        public void setTicketNumber(String TicketNumber) {
            this.TicketNumber = TicketNumber;
        }

        public int getTotalTicketFare() {
            return TotalTicketFare;
        }

        public void setTotalTicketFare(int TotalTicketFare) {
            this.TotalTicketFare = TotalTicketFare;
        }

        public int getTotalRefundAmount() {
            return TotalRefundAmount;
        }

        public void setTotalRefundAmount(int TotalRefundAmount) {
            this.TotalRefundAmount = TotalRefundAmount;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public int getCancellationCharges() {
            return CancellationCharges;
        }

        public void setCancellationCharges(int CancellationCharges) {
            this.CancellationCharges = CancellationCharges;
        }

        public String getRefundType() {
            return RefundType;
        }

        public void setRefundType(String RefundType) {
            this.RefundType = RefundType;
        }

        public boolean isIsSeatCancellable() {
            return isSeatCancellable;
        }

        public void setIsSeatCancellable(boolean isSeatCancellable) {
            this.isSeatCancellable = isSeatCancellable;
        }

        public boolean isPartialCancellationAllowed() {
            return PartialCancellationAllowed;
        }

        public void setPartialCancellationAllowed(boolean PartialCancellationAllowed) {
            this.PartialCancellationAllowed = PartialCancellationAllowed;
        }

        public RefundResponseBean getRefundResponse() {
            return RefundResponse;
        }

        public void setRefundResponse(RefundResponseBean RefundResponse) {
            this.RefundResponse = RefundResponse;
        }

        public Object getCancellationId() {
            return CancellationId;
        }

        public void setCancellationId(Object CancellationId) {
            this.CancellationId = CancellationId;
        }

        public int getCancellationAmount() {
            return CancellationAmount;
        }

        public void setCancellationAmount(int CancellationAmount) {
            this.CancellationAmount = CancellationAmount;
        }

        public static class RefundResponseBean {
            /**
             * TransactionId : null
             * PaymentId : null
             * Message : null
             * Amount : 0
             * Mode : null
             * PgRefNo : null
             * RefundStatus : 1
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
