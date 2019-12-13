package com.safepayu.wallet.models.response.booking.flight;

import java.util.List;

public class CancelBookTicketResponse {


    /**
     * status : true
     * message : Ticket cancelled successfully!
     * data : {"ResponseStatus":200,"APIReferenceNo":"UBJ01QHX","Message":"Ticket cancelled successfully!","FinalRefundAmount":0,"BookingStatus":5,"Cancellations":[{"CancellationCharges":0,"RefundStatus":2,"CancellationId":"","Remarks":null,"CancelStatus":5,"Etickets":[{"ChangeRequestId":null,"EticketNo":"0PJTTCTX","TicketId":null,"FlightuId":"","PassuId":null,"GivenName":"Arpan","SurName":null,"NameReference":"Mr. Arpan Singh","TripType":1,"Status":0}]}],"CancelTime":"13-12-2019 12:09:48"}
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
         * ResponseStatus : 200
         * APIReferenceNo : UBJ01QHX
         * Message : Ticket cancelled successfully!
         * FinalRefundAmount : 0
         * BookingStatus : 5
         * Cancellations : [{"CancellationCharges":0,"RefundStatus":2,"CancellationId":"","Remarks":null,"CancelStatus":5,"Etickets":[{"ChangeRequestId":null,"EticketNo":"0PJTTCTX","TicketId":null,"FlightuId":"","PassuId":null,"GivenName":"Arpan","SurName":null,"NameReference":"Mr. Arpan Singh","TripType":1,"Status":0}]}]
         * CancelTime : 13-12-2019 12:09:48
         */

        private int ResponseStatus;
        private String APIReferenceNo;
        private String Message;
        private int FinalRefundAmount;
        private int BookingStatus;
        private String CancelTime;
        private List<CancellationsBean> Cancellations;

        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
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

        public int getFinalRefundAmount() {
            return FinalRefundAmount;
        }

        public void setFinalRefundAmount(int FinalRefundAmount) {
            this.FinalRefundAmount = FinalRefundAmount;
        }

        public int getBookingStatus() {
            return BookingStatus;
        }

        public void setBookingStatus(int BookingStatus) {
            this.BookingStatus = BookingStatus;
        }

        public String getCancelTime() {
            return CancelTime;
        }

        public void setCancelTime(String CancelTime) {
            this.CancelTime = CancelTime;
        }

        public List<CancellationsBean> getCancellations() {
            return Cancellations;
        }

        public void setCancellations(List<CancellationsBean> Cancellations) {
            this.Cancellations = Cancellations;
        }

        public static class CancellationsBean {
            /**
             * CancellationCharges : 0
             * RefundStatus : 2
             * CancellationId :
             * Remarks : null
             * CancelStatus : 5
             * Etickets : [{"ChangeRequestId":null,"EticketNo":"0PJTTCTX","TicketId":null,"FlightuId":"","PassuId":null,"GivenName":"Arpan","SurName":null,"NameReference":"Mr. Arpan Singh","TripType":1,"Status":0}]
             */

            private int CancellationCharges;
            private int RefundStatus;
            private String CancellationId;
            private Object Remarks;
            private int CancelStatus;
            private List<EticketsBean> Etickets;

            public int getCancellationCharges() {
                return CancellationCharges;
            }

            public void setCancellationCharges(int CancellationCharges) {
                this.CancellationCharges = CancellationCharges;
            }

            public int getRefundStatus() {
                return RefundStatus;
            }

            public void setRefundStatus(int RefundStatus) {
                this.RefundStatus = RefundStatus;
            }

            public String getCancellationId() {
                return CancellationId;
            }

            public void setCancellationId(String CancellationId) {
                this.CancellationId = CancellationId;
            }

            public Object getRemarks() {
                return Remarks;
            }

            public void setRemarks(Object Remarks) {
                this.Remarks = Remarks;
            }

            public int getCancelStatus() {
                return CancelStatus;
            }

            public void setCancelStatus(int CancelStatus) {
                this.CancelStatus = CancelStatus;
            }

            public List<EticketsBean> getEtickets() {
                return Etickets;
            }

            public void setEtickets(List<EticketsBean> Etickets) {
                this.Etickets = Etickets;
            }

            public static class EticketsBean {
                /**
                 * ChangeRequestId : null
                 * EticketNo : 0PJTTCTX
                 * TicketId : null
                 * FlightuId :
                 * PassuId : null
                 * GivenName : Arpan
                 * SurName : null
                 * NameReference : Mr. Arpan Singh
                 * TripType : 1
                 * Status : 0
                 */

                private Object ChangeRequestId;
                private String EticketNo;
                private Object TicketId;
                private String FlightuId;
                private Object PassuId;
                private String GivenName;
                private Object SurName;
                private String NameReference;
                private int TripType;
                private int Status;

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

                public Object getTicketId() {
                    return TicketId;
                }

                public void setTicketId(Object TicketId) {
                    this.TicketId = TicketId;
                }

                public String getFlightuId() {
                    return FlightuId;
                }

                public void setFlightuId(String FlightuId) {
                    this.FlightuId = FlightuId;
                }

                public Object getPassuId() {
                    return PassuId;
                }

                public void setPassuId(Object PassuId) {
                    this.PassuId = PassuId;
                }

                public String getGivenName() {
                    return GivenName;
                }

                public void setGivenName(String GivenName) {
                    this.GivenName = GivenName;
                }

                public Object getSurName() {
                    return SurName;
                }

                public void setSurName(Object SurName) {
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
            }
        }
    }
}
