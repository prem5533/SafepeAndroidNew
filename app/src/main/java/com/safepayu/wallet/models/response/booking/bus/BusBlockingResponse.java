package com.safepayu.wallet.models.response.booking.bus;

public class BusBlockingResponse {

    /**
     * status : true
     * message : SUCCESS
     * data : {"BlockingReferenceNo":"1008303898","BookingReferenceNo":"100301004988","Message":"SUCCESS","IsPriceChange":false,"previousFare":null,"updatedFare":null,"BookingStatus":1,"ResponseStatus":200,"bookingFee":0,"FareBreakUps":null,"TollFee":0,"ReservationFee":0}
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
         * BlockingReferenceNo : 1008303898
         * BookingReferenceNo : 100301004988
         * Message : SUCCESS
         * IsPriceChange : false
         * previousFare : null
         * updatedFare : null
         * BookingStatus : 1
         * ResponseStatus : 200
         * bookingFee : 0
         * FareBreakUps : null
         * TollFee : 0
         * ReservationFee : 0
         */

        private String BlockingReferenceNo;
        private String BookingReferenceNo;
        private String Message;
        private boolean IsPriceChange;
        private Object previousFare;
        private Object updatedFare;
        private int BookingStatus;
        private int ResponseStatus;
        private int bookingFee;
        private Object FareBreakUps;
        private int TollFee;
        private int ReservationFee;

        public String getBlockingReferenceNo() {
            return BlockingReferenceNo;
        }

        public void setBlockingReferenceNo(String BlockingReferenceNo) {
            this.BlockingReferenceNo = BlockingReferenceNo;
        }

        public String getBookingReferenceNo() {
            return BookingReferenceNo;
        }

        public void setBookingReferenceNo(String BookingReferenceNo) {
            this.BookingReferenceNo = BookingReferenceNo;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public boolean isIsPriceChange() {
            return IsPriceChange;
        }

        public void setIsPriceChange(boolean IsPriceChange) {
            this.IsPriceChange = IsPriceChange;
        }

        public Object getPreviousFare() {
            return previousFare;
        }

        public void setPreviousFare(Object previousFare) {
            this.previousFare = previousFare;
        }

        public Object getUpdatedFare() {
            return updatedFare;
        }

        public void setUpdatedFare(Object updatedFare) {
            this.updatedFare = updatedFare;
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

        public int getBookingFee() {
            return bookingFee;
        }

        public void setBookingFee(int bookingFee) {
            this.bookingFee = bookingFee;
        }

        public Object getFareBreakUps() {
            return FareBreakUps;
        }

        public void setFareBreakUps(Object FareBreakUps) {
            this.FareBreakUps = FareBreakUps;
        }

        public int getTollFee() {
            return TollFee;
        }

        public void setTollFee(int TollFee) {
            this.TollFee = TollFee;
        }

        public int getReservationFee() {
            return ReservationFee;
        }

        public void setReservationFee(int ReservationFee) {
            this.ReservationFee = ReservationFee;
        }
    }
}
