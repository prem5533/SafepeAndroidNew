package com.safepayu.wallet.models.response.booking.bus;

import java.util.List;

public class BusListResponse {

    /**
     * status : true
     * message : Available Buses
     * data : {"AvailableTrips":[],"ProvidersCount":1,"ResponseStatus":200,"Message":"Sucess"}
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
         * AvailableTrips : []
         * ProvidersCount : 1
         * ResponseStatus : 200
         * Message : Sucess
         */

        private int ProvidersCount;
        private int ResponseStatus;
        private String Message;
        private List<AvailableTripsBean> AvailableTrips;


        public int getProvidersCount() {
            return ProvidersCount;
        }

        public void setProvidersCount(int ProvidersCount) {
            this.ProvidersCount = ProvidersCount;
        }

        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public List<AvailableTripsBean> getAvailableTrips() {
            return AvailableTrips;
        }

        public void setAvailableTrips(List<AvailableTripsBean> AvailableTrips) {
            this.AvailableTrips = AvailableTrips;
        }

        public static class AvailableTripsBean {
            /**
             * DisplayName : TRSMOP
             * AvailableSeats : 40
             * IsRtc : false
             * IdproofRequried : false
             * isOpTicketTemplateRequired : false
             * isOpLogoRequired : false
             * ArrivalTime : 05:50 AM
             * Amenities : []
             * BoardingTimes : [{"Address":null,"ContactNumbers":null,"ContactPersons":null,"PointId":"137","Landmark":"White Field","Location":"White Field","Name":null,"Time":"1250"}]
             * BusType : Seater AC Scania Semi Sleeper (2+2)
             * SeatLayoutType : null
             * CancellationPolicy : 10:-1:10:0
             * IdProofRequired : false
             * MaxSeatsPerTicket : null
             * DepartureTime : 08:00 PM
             * DestinationId : 103
             * Duration : 14:10 hrs
             * DroppingTimes : [{"Address":null,"ContactNumbers":null,"ContactPersons":null,"PointId":"709","Landmark":"Vandalur","Location":"Vandalur","Name":null,"Time":"300"}]
             * Fares : 2000
             * ServiceTax : 0.00
             * OperatorServiceCharge : 0.00
             * ConvenienceFee : 0
             * ConvenienceFeeType : 1
             * AffiliateId : null
             * PartnerFareDatails : {"NetFares":"2000","Commission":"0","CommissionType":1}
             * NetFares : null
             * PartnerFee : 0
             * OperatorId :
             * BpDpSeatLayout : null
             * Id : 313
             * Provider : +rfVawweNEABIDWJVZMKFA==
             * PartialCancellationAllowed : true
             * SeatType : 1
             * SourceId : 109
             * Travels : SANGAMITRA
             * Mticket : True
             * InventoryType : null
             * Journeydate : 2019-11-18
             */

            private String DisplayName;
            private String AvailableSeats;
            private boolean IsRtc;
            private boolean IdproofRequried;
            private boolean isOpTicketTemplateRequired;
            private boolean isOpLogoRequired;
            private String ArrivalTime;
            private String BusType;
            private Object SeatLayoutType;
            private String CancellationPolicy;
            private boolean IdProofRequired;
            private Object MaxSeatsPerTicket;
            private String DepartureTime;
            private String DestinationId;
            private String Duration;
            private String Fares;
            private String ServiceTax;
            private String OperatorServiceCharge;
            private int ConvenienceFee;
            private int ConvenienceFeeType;
            private Object AffiliateId;
            private PartnerFareDatailsBean PartnerFareDatails;
            private Object NetFares;
            private int PartnerFee;
            private String OperatorId;
            private Object BpDpSeatLayout;
            private String Id;
            private String Provider;
            private String PartialCancellationAllowed;
            private int SeatType;
            private String SourceId;
            private String Travels;
            private String Mticket;
            private Object InventoryType;
            private String Journeydate;
            private List<?> Amenities;
            private List<BoardingTimesBean> BoardingTimes;
            private List<DroppingTimesBean> DroppingTimes;

            public String getDisplayName() {
                return DisplayName;
            }

            public void setDisplayName(String DisplayName) {
                this.DisplayName = DisplayName;
            }

            public String getAvailableSeats() {
                return AvailableSeats;
            }

            public void setAvailableSeats(String AvailableSeats) {
                this.AvailableSeats = AvailableSeats;
            }

            public boolean isIsRtc() {
                return IsRtc;
            }

            public void setIsRtc(boolean IsRtc) {
                this.IsRtc = IsRtc;
            }

            public boolean isIdproofRequried() {
                return IdproofRequried;
            }

            public void setIdproofRequried(boolean IdproofRequried) {
                this.IdproofRequried = IdproofRequried;
            }

            public boolean isIsOpTicketTemplateRequired() {
                return isOpTicketTemplateRequired;
            }

            public void setIsOpTicketTemplateRequired(boolean isOpTicketTemplateRequired) {
                this.isOpTicketTemplateRequired = isOpTicketTemplateRequired;
            }

            public boolean isIsOpLogoRequired() {
                return isOpLogoRequired;
            }

            public void setIsOpLogoRequired(boolean isOpLogoRequired) {
                this.isOpLogoRequired = isOpLogoRequired;
            }

            public String getArrivalTime() {
                return ArrivalTime;
            }

            public void setArrivalTime(String ArrivalTime) {
                this.ArrivalTime = ArrivalTime;
            }

            public String getBusType() {
                return BusType;
            }

            public void setBusType(String BusType) {
                this.BusType = BusType;
            }

            public Object getSeatLayoutType() {
                return SeatLayoutType;
            }

            public void setSeatLayoutType(Object SeatLayoutType) {
                this.SeatLayoutType = SeatLayoutType;
            }

            public String getCancellationPolicy() {
                return CancellationPolicy;
            }

            public void setCancellationPolicy(String CancellationPolicy) {
                this.CancellationPolicy = CancellationPolicy;
            }

            public boolean isIdProofRequired() {
                return IdProofRequired;
            }

            public void setIdProofRequired(boolean IdProofRequired) {
                this.IdProofRequired = IdProofRequired;
            }

            public Object getMaxSeatsPerTicket() {
                return MaxSeatsPerTicket;
            }

            public void setMaxSeatsPerTicket(Object MaxSeatsPerTicket) {
                this.MaxSeatsPerTicket = MaxSeatsPerTicket;
            }

            public String getDepartureTime() {
                return DepartureTime;
            }

            public void setDepartureTime(String DepartureTime) {
                this.DepartureTime = DepartureTime;
            }

            public String getDestinationId() {
                return DestinationId;
            }

            public void setDestinationId(String DestinationId) {
                this.DestinationId = DestinationId;
            }

            public String getDuration() {
                return Duration;
            }

            public void setDuration(String Duration) {
                this.Duration = Duration;
            }

            public String getFares() {
                return Fares;
            }

            public void setFares(String Fares) {
                this.Fares = Fares;
            }

            public String getServiceTax() {
                return ServiceTax;
            }

            public void setServiceTax(String ServiceTax) {
                this.ServiceTax = ServiceTax;
            }

            public String getOperatorServiceCharge() {
                return OperatorServiceCharge;
            }

            public void setOperatorServiceCharge(String OperatorServiceCharge) {
                this.OperatorServiceCharge = OperatorServiceCharge;
            }

            public int getConvenienceFee() {
                return ConvenienceFee;
            }

            public void setConvenienceFee(int ConvenienceFee) {
                this.ConvenienceFee = ConvenienceFee;
            }

            public int getConvenienceFeeType() {
                return ConvenienceFeeType;
            }

            public void setConvenienceFeeType(int ConvenienceFeeType) {
                this.ConvenienceFeeType = ConvenienceFeeType;
            }

            public Object getAffiliateId() {
                return AffiliateId;
            }

            public void setAffiliateId(Object AffiliateId) {
                this.AffiliateId = AffiliateId;
            }

            public PartnerFareDatailsBean getPartnerFareDatails() {
                return PartnerFareDatails;
            }

            public void setPartnerFareDatails(PartnerFareDatailsBean PartnerFareDatails) {
                this.PartnerFareDatails = PartnerFareDatails;
            }

            public Object getNetFares() {
                return NetFares;
            }

            public void setNetFares(Object NetFares) {
                this.NetFares = NetFares;
            }

            public int getPartnerFee() {
                return PartnerFee;
            }

            public void setPartnerFee(int PartnerFee) {
                this.PartnerFee = PartnerFee;
            }

            public String getOperatorId() {
                return OperatorId;
            }

            public void setOperatorId(String OperatorId) {
                this.OperatorId = OperatorId;
            }

            public Object getBpDpSeatLayout() {
                return BpDpSeatLayout;
            }

            public void setBpDpSeatLayout(Object BpDpSeatLayout) {
                this.BpDpSeatLayout = BpDpSeatLayout;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getProvider() {
                return Provider;
            }

            public void setProvider(String Provider) {
                this.Provider = Provider;
            }

            public String getPartialCancellationAllowed() {
                return PartialCancellationAllowed;
            }

            public void setPartialCancellationAllowed(String PartialCancellationAllowed) {
                this.PartialCancellationAllowed = PartialCancellationAllowed;
            }

            public int getSeatType() {
                return SeatType;
            }

            public void setSeatType(int SeatType) {
                this.SeatType = SeatType;
            }

            public String getSourceId() {
                return SourceId;
            }

            public void setSourceId(String SourceId) {
                this.SourceId = SourceId;
            }

            public String getTravels() {
                return Travels;
            }

            public void setTravels(String Travels) {
                this.Travels = Travels;
            }

            public String getMticket() {
                return Mticket;
            }

            public void setMticket(String Mticket) {
                this.Mticket = Mticket;
            }

            public Object getInventoryType() {
                return InventoryType;
            }

            public void setInventoryType(Object InventoryType) {
                this.InventoryType = InventoryType;
            }

            public String getJourneydate() {
                return Journeydate;
            }

            public void setJourneydate(String Journeydate) {
                this.Journeydate = Journeydate;
            }

            public List<?> getAmenities() {
                return Amenities;
            }

            public void setAmenities(List<?> Amenities) {
                this.Amenities = Amenities;
            }

            public List<BoardingTimesBean> getBoardingTimes() {
                return BoardingTimes;
            }

            public void setBoardingTimes(List<BoardingTimesBean> BoardingTimes) {
                this.BoardingTimes = BoardingTimes;
            }

            public List<DroppingTimesBean> getDroppingTimes() {
                return DroppingTimes;
            }

            public void setDroppingTimes(List<DroppingTimesBean> DroppingTimes) {
                this.DroppingTimes = DroppingTimes;
            }

            public static class PartnerFareDatailsBean {
                /**
                 * NetFares : 2000
                 * Commission : 0
                 * CommissionType : 1
                 */

                private String NetFares;
                private String Commission;
                private int CommissionType;

                public String getNetFares() {
                    return NetFares;
                }

                public void setNetFares(String NetFares) {
                    this.NetFares = NetFares;
                }

                public String getCommission() {
                    return Commission;
                }

                public void setCommission(String Commission) {
                    this.Commission = Commission;
                }

                public int getCommissionType() {
                    return CommissionType;
                }

                public void setCommissionType(int CommissionType) {
                    this.CommissionType = CommissionType;
                }
            }

            public static class BoardingTimesBean {
                /**
                 * Address : null
                 * ContactNumbers : null
                 * ContactPersons : null
                 * PointId : 137
                 * Landmark : White Field
                 * Location : White Field
                 * Name : null
                 * Time : 1250
                 */

                private Object Address;
                private Object ContactNumbers;
                private Object ContactPersons;
                private String PointId;
                private String Landmark;
                private String Location;
                private Object Name;
                private String Time;

                public Object getAddress() {
                    return Address;
                }

                public void setAddress(Object Address) {
                    this.Address = Address;
                }

                public Object getContactNumbers() {
                    return ContactNumbers;
                }

                public void setContactNumbers(Object ContactNumbers) {
                    this.ContactNumbers = ContactNumbers;
                }

                public Object getContactPersons() {
                    return ContactPersons;
                }

                public void setContactPersons(Object ContactPersons) {
                    this.ContactPersons = ContactPersons;
                }

                public String getPointId() {
                    return PointId;
                }

                public void setPointId(String PointId) {
                    this.PointId = PointId;
                }

                public String getLandmark() {
                    return Landmark;
                }

                public void setLandmark(String Landmark) {
                    this.Landmark = Landmark;
                }

                public String getLocation() {
                    return Location;
                }

                public void setLocation(String Location) {
                    this.Location = Location;
                }

                public Object getName() {
                    return Name;
                }

                public void setName(Object Name) {
                    this.Name = Name;
                }

                public String getTime() {
                    return Time;
                }

                public void setTime(String Time) {
                    this.Time = Time;
                }
            }

            public static class DroppingTimesBean {
                /**
                 * Address : null
                 * ContactNumbers : null
                 * ContactPersons : null
                 * PointId : 709
                 * Landmark : Vandalur
                 * Location : Vandalur
                 * Name : null
                 * Time : 300
                 */

                private Object Address;
                private Object ContactNumbers;
                private Object ContactPersons;
                private String PointId;
                private String Landmark;
                private String Location;
                private Object Name;
                private String Time;

                public Object getAddress() {
                    return Address;
                }

                public void setAddress(Object Address) {
                    this.Address = Address;
                }

                public Object getContactNumbers() {
                    return ContactNumbers;
                }

                public void setContactNumbers(Object ContactNumbers) {
                    this.ContactNumbers = ContactNumbers;
                }

                public Object getContactPersons() {
                    return ContactPersons;
                }

                public void setContactPersons(Object ContactPersons) {
                    this.ContactPersons = ContactPersons;
                }

                public String getPointId() {
                    return PointId;
                }

                public void setPointId(String PointId) {
                    this.PointId = PointId;
                }

                public String getLandmark() {
                    return Landmark;
                }

                public void setLandmark(String Landmark) {
                    this.Landmark = Landmark;
                }

                public String getLocation() {
                    return Location;
                }

                public void setLocation(String Location) {
                    this.Location = Location;
                }

                public Object getName() {
                    return Name;
                }

                public void setName(Object Name) {
                    this.Name = Name;
                }

                public String getTime() {
                    return Time;
                }

                public void setTime(String Time) {
                    this.Time = Time;
                }
            }
        }
    }
}
