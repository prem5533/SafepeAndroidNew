package com.safepayu.wallet.models.response.booking.flight;

import java.util.List;

public class FlightBlockTicketResponse {


    /**
     * status : true
     * message : Ticket booked successully
     * data : {"ReferenceNo":"300620016446","APIReferenceNo":"7W1VJZEG","Message":"Ticket booked successully","ResponseStatus":200,"RefundResponse":null,"BookingStatus":3,"GDFPNRNo":"I1PXMYVA","EticketNo":"TL7L6WG2|4C1RXLEF|VWQGKB84","Flightuid":null,"Passworduid":"","TransactionId":"I1PXMYVA","LastTicketDate":null,"TransactionDetails":null,"Tickets":[{"ChangeRequestId":null,"EticketNo":"TL7L6WG2","TicketId":"315358","FlightuId":"","PassuId":"224616","GivenName":"adult","SurName":"test","NameReference":"Ms. adult test","TripType":1,"Status":0,"title":"Ms."},{"ChangeRequestId":null,"EticketNo":"4C1RXLEF","TicketId":"755246","FlightuId":"","PassuId":"687222","GivenName":"child","SurName":"test","NameReference":"Mstr. child test","TripType":1,"Status":0,"title":"chld"},{"ChangeRequestId":null,"EticketNo":"VWQGKB84","TicketId":"455466","FlightuId":"","PassuId":"518458","GivenName":"infant","SurName":"test","NameReference":"Mstr. infant test","TripType":1,"Status":0,"title":"inft"}]}
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
         * ReferenceNo : 300620016446
         * APIReferenceNo : 7W1VJZEG
         * Message : Ticket booked successully
         * ResponseStatus : 200
         * RefundResponse : null
         * BookingStatus : 3
         * GDFPNRNo : I1PXMYVA
         * EticketNo : TL7L6WG2|4C1RXLEF|VWQGKB84
         * Flightuid : null
         * Passworduid :
         * TransactionId : I1PXMYVA
         * LastTicketDate : null
         * TransactionDetails : null
         * Tickets : [{"ChangeRequestId":null,"EticketNo":"TL7L6WG2","TicketId":"315358","FlightuId":"","PassuId":"224616","GivenName":"adult","SurName":"test","NameReference":"Ms. adult test","TripType":1,"Status":0,"title":"Ms."},{"ChangeRequestId":null,"EticketNo":"4C1RXLEF","TicketId":"755246","FlightuId":"","PassuId":"687222","GivenName":"child","SurName":"test","NameReference":"Mstr. child test","TripType":1,"Status":0,"title":"chld"},{"ChangeRequestId":null,"EticketNo":"VWQGKB84","TicketId":"455466","FlightuId":"","PassuId":"518458","GivenName":"infant","SurName":"test","NameReference":"Mstr. infant test","TripType":1,"Status":0,"title":"inft"}]
         */

        private String ReferenceNo;
        private String APIReferenceNo;
        private String Message;
        private int ResponseStatus;
        private Object RefundResponse;
        private int BookingStatus;
        private String GDFPNRNo;
        private String EticketNo;
        private Object Flightuid;
        private String Passworduid;
        private String TransactionId;
        private Object LastTicketDate;
        private Object TransactionDetails;
        private List<TicketsBean> Tickets;

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

        public int getBookingStatus() {
            return BookingStatus;
        }

        public void setBookingStatus(int BookingStatus) {
            this.BookingStatus = BookingStatus;
        }

        public String getGDFPNRNo() {
            return GDFPNRNo;
        }

        public void setGDFPNRNo(String GDFPNRNo) {
            this.GDFPNRNo = GDFPNRNo;
        }

        public String getEticketNo() {
            return EticketNo;
        }

        public void setEticketNo(String EticketNo) {
            this.EticketNo = EticketNo;
        }

        public Object getFlightuid() {
            return Flightuid;
        }

        public void setFlightuid(Object Flightuid) {
            this.Flightuid = Flightuid;
        }

        public String getPassworduid() {
            return Passworduid;
        }

        public void setPassworduid(String Passworduid) {
            this.Passworduid = Passworduid;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String TransactionId) {
            this.TransactionId = TransactionId;
        }

        public Object getLastTicketDate() {
            return LastTicketDate;
        }

        public void setLastTicketDate(Object LastTicketDate) {
            this.LastTicketDate = LastTicketDate;
        }

        public Object getTransactionDetails() {
            return TransactionDetails;
        }

        public void setTransactionDetails(Object TransactionDetails) {
            this.TransactionDetails = TransactionDetails;
        }

        public List<TicketsBean> getTickets() {
            return Tickets;
        }

        public void setTickets(List<TicketsBean> Tickets) {
            this.Tickets = Tickets;
        }

        public static class TicketsBean {
            /**
             * ChangeRequestId : null
             * EticketNo : TL7L6WG2
             * TicketId : 315358
             * FlightuId :
             * PassuId : 224616
             * GivenName : adult
             * SurName : test
             * NameReference : Ms. adult test
             * TripType : 1
             * Status : 0
             * title : Ms.
             */

            private Object ChangeRequestId;
            private String EticketNo;
            private String TicketId;
            private String FlightuId;
            private String PassuId;
            private String GivenName;
            private String SurName;
            private String NameReference;
            private int TripType;
            private int Status;
            private String title;

            public Object getChangeRequestId() {
                return ChangeRequestId;
            }

            public void setChangeRequestId(Object ChangeRequestId) {
                this.ChangeRequestId = ChangeRequestId;
            }

            public String getEticketNo() {
                return EticketNo;
            }

            public void setEticketNo(String EticketNo) {
                this.EticketNo = EticketNo;
            }

            public String getTicketId() {
                return TicketId;
            }

            public void setTicketId(String TicketId) {
                this.TicketId = TicketId;
            }

            public String getFlightuId() {
                return FlightuId;
            }

            public void setFlightuId(String FlightuId) {
                this.FlightuId = FlightuId;
            }

            public String getPassuId() {
                return PassuId;
            }

            public void setPassuId(String PassuId) {
                this.PassuId = PassuId;
            }

            public String getGivenName() {
                return GivenName;
            }

            public void setGivenName(String GivenName) {
                this.GivenName = GivenName;
            }

            public String getSurName() {
                return SurName;
            }

            public void setSurName(String SurName) {
                this.SurName = SurName;
            }

            public String getNameReference() {
                return NameReference;
            }

            public void setNameReference(String NameReference) {
                this.NameReference = NameReference;
            }

            public int getTripType() {
                return TripType;
            }

            public void setTripType(int TripType) {
                this.TripType = TripType;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}