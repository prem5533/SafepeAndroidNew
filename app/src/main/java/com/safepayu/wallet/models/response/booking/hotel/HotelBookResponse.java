package com.safepayu.wallet.models.response.booking.hotel;

public class HotelBookResponse {

    /**
     * status : false
     * message : Room blocked for booking
     * data : {"Allocavail":null,"Allocid":null,"ErrorResult":null,"BookingTransactionId":null,"AdditionalInfo":null,"IsPriceChanged":false,"PreviousFare":0,"UpdatedFare":0,"FareBreakUps":null,"TempField":"","ReferenceNo":"500660000818","APIReferenceNo":"I2SPSIT5X","Message":"Hotel Room Blocked Successfully","BookingStatus":1,"ResponseStatus":200,"RefundResponse":null,"Provider":null}
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
         * Allocavail : null
         * Allocid : null
         * ErrorResult : null
         * BookingTransactionId : null
         * AdditionalInfo : null
         * IsPriceChanged : false
         * PreviousFare : 0
         * UpdatedFare : 0
         * FareBreakUps : null
         * TempField :
         * ReferenceNo : 500660000818
         * APIReferenceNo : I2SPSIT5X
         * Message : Hotel Room Blocked Successfully
         * BookingStatus : 1
         * ResponseStatus : 200
         * RefundResponse : null
         * Provider : null
         */

        private Object Allocavail;
        private Object Allocid;
        private Object ErrorResult;
        private Object BookingTransactionId;
        private Object AdditionalInfo;
        private boolean IsPriceChanged;
        private int PreviousFare;
        private int UpdatedFare;
        private Object FareBreakUps;
        private String TempField;
        private String ReferenceNo;
        private String APIReferenceNo;
        private String Message;
        private int BookingStatus;
        private int ResponseStatus;
        private Object RefundResponse;
        private Object Provider;

        public Object getAllocavail() {
            return Allocavail;
        }

        public void setAllocavail(Object Allocavail) {
            this.Allocavail = Allocavail;
        }

        public Object getAllocid() {
            return Allocid;
        }

        public void setAllocid(Object Allocid) {
            this.Allocid = Allocid;
        }

        public Object getErrorResult() {
            return ErrorResult;
        }

        public void setErrorResult(Object ErrorResult) {
            this.ErrorResult = ErrorResult;
        }

        public Object getBookingTransactionId() {
            return BookingTransactionId;
        }

        public void setBookingTransactionId(Object BookingTransactionId) {
            this.BookingTransactionId = BookingTransactionId;
        }

        public Object getAdditionalInfo() {
            return AdditionalInfo;
        }

        public void setAdditionalInfo(Object AdditionalInfo) {
            this.AdditionalInfo = AdditionalInfo;
        }

        public boolean isIsPriceChanged() {
            return IsPriceChanged;
        }

        public void setIsPriceChanged(boolean IsPriceChanged) {
            this.IsPriceChanged = IsPriceChanged;
        }

        public int getPreviousFare() {
            return PreviousFare;
        }

        public void setPreviousFare(int PreviousFare) {
            this.PreviousFare = PreviousFare;
        }

        public int getUpdatedFare() {
            return UpdatedFare;
        }

        public void setUpdatedFare(int UpdatedFare) {
            this.UpdatedFare = UpdatedFare;
        }

        public Object getFareBreakUps() {
            return FareBreakUps;
        }

        public void setFareBreakUps(Object FareBreakUps) {
            this.FareBreakUps = FareBreakUps;
        }

        public String getTempField() {
            return TempField;
        }

        public void setTempField(String TempField) {
            this.TempField = TempField;
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
