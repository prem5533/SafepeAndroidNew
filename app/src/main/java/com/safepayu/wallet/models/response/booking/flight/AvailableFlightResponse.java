package com.safepayu.wallet.models.response.booking.flight;

import java.io.Serializable;
import java.util.List;

public class AvailableFlightResponse {




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

        private int ResponseStatus;
        private String Message;
        private String SearchID;
        private List<DomesticOnwardFlightsBean> DomesticOnwardFlights;
        private List<?> DomesticReturnFlights;

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

        public String getSearchID() {
            return SearchID;
        }

        public void setSearchID(String SearchID) {
            this.SearchID = SearchID;
        }

        public List<DomesticOnwardFlightsBean> getDomesticOnwardFlights() {
            return DomesticOnwardFlights;
        }

        public void setDomesticOnwardFlights(List<DomesticOnwardFlightsBean> DomesticOnwardFlights) {
            this.DomesticOnwardFlights = DomesticOnwardFlights;
        }

        public List<?> getDomesticReturnFlights() {
            return DomesticReturnFlights;
        }

        public void setDomesticReturnFlights(List<?> DomesticReturnFlights) {
            this.DomesticReturnFlights = DomesticReturnFlights;
        }

        public static class DomesticOnwardFlightsBean   {


            private FareDetailsBean FareDetails;
            private OriginDestinationoptionIdBean OriginDestinationoptionId;
            private Object IntOnward;
            private Object IntReturn;
            private Object IntMulti;
            private RequestDetailsBean RequestDetails;
            private String Provider;
            private Object PartnerId;
            private Object AffiliateId;
            private String FlightUId;
            private boolean IsGSTMandatory;
            private String AirlineRemark;
            private boolean IsLCC;
            private boolean IsHoldAllowed;
            private List<FlightSegmentsBean> FlightSegments;

            public FareDetailsBean getFareDetails() {
                return FareDetails;
            }

            public void setFareDetails(FareDetailsBean FareDetails) {
                this.FareDetails = FareDetails;
            }

            public OriginDestinationoptionIdBean getOriginDestinationoptionId() {
                return OriginDestinationoptionId;
            }

            public void setOriginDestinationoptionId(OriginDestinationoptionIdBean OriginDestinationoptionId) {
                this.OriginDestinationoptionId = OriginDestinationoptionId;
            }

            public Object getIntOnward() {
                return IntOnward;
            }

            public void setIntOnward(Object IntOnward) {
                this.IntOnward = IntOnward;
            }

            public Object getIntReturn() {
                return IntReturn;
            }

            public void setIntReturn(Object IntReturn) {
                this.IntReturn = IntReturn;
            }

            public Object getIntMulti() {
                return IntMulti;
            }

            public void setIntMulti(Object IntMulti) {
                this.IntMulti = IntMulti;
            }

            public RequestDetailsBean getRequestDetails() {
                return RequestDetails;
            }

            public void setRequestDetails(RequestDetailsBean RequestDetails) {
                this.RequestDetails = RequestDetails;
            }

            public String getProvider() {
                return Provider;
            }

            public void setProvider(String Provider) {
                this.Provider = Provider;
            }

            public Object getPartnerId() {
                return PartnerId;
            }

            public void setPartnerId(Object PartnerId) {
                this.PartnerId = PartnerId;
            }

            public Object getAffiliateId() {
                return AffiliateId;
            }

            public void setAffiliateId(Object AffiliateId) {
                this.AffiliateId = AffiliateId;
            }

            public String getFlightUId() {
                return FlightUId;
            }

            public void setFlightUId(String FlightUId) {
                this.FlightUId = FlightUId;
            }

            public boolean isIsGSTMandatory() {
                return IsGSTMandatory;
            }

            public void setIsGSTMandatory(boolean IsGSTMandatory) {
                this.IsGSTMandatory = IsGSTMandatory;
            }

            public String getAirlineRemark() {
                return AirlineRemark;
            }

            public void setAirlineRemark(String AirlineRemark) {
                this.AirlineRemark = AirlineRemark;
            }

            public boolean isIsLCC() {
                return IsLCC;
            }

            public void setIsLCC(boolean IsLCC) {
                this.IsLCC = IsLCC;
            }

            public boolean isIsHoldAllowed() {
                return IsHoldAllowed;
            }

            public void setIsHoldAllowed(boolean IsHoldAllowed) {
                this.IsHoldAllowed = IsHoldAllowed;
            }

            public List<FlightSegmentsBean> getFlightSegments() {
                return FlightSegments;
            }

            public void setFlightSegments(List<FlightSegmentsBean> FlightSegments) {
                this.FlightSegments = FlightSegments;
            }

            public static class FareDetailsBean {

                private ChargeableFaresBean ChargeableFares;
                private NonchargeableFaresBean NonchargeableFares;
                private FareBreakUpBean FareBreakUp;
                private int OCTax;
                private int PartnerFee;
                private int PLBEarned;
                private int TdsOnPLB;
                private int Bonus;
                private int TotalFare;
                private int ResponseStatus;
                private int Status;
                private boolean IsGSTMandatory;
                private Object Message;
                private Object RequiredFields;

                public ChargeableFaresBean getChargeableFares() {
                    return ChargeableFares;
                }

                public void setChargeableFares(ChargeableFaresBean ChargeableFares) {
                    this.ChargeableFares = ChargeableFares;
                }

                public NonchargeableFaresBean getNonchargeableFares() {
                    return NonchargeableFares;
                }

                public void setNonchargeableFares(NonchargeableFaresBean NonchargeableFares) {
                    this.NonchargeableFares = NonchargeableFares;
                }

                public FareBreakUpBean getFareBreakUp() {
                    return FareBreakUp;
                }

                public void setFareBreakUp(FareBreakUpBean FareBreakUp) {
                    this.FareBreakUp = FareBreakUp;
                }

                public int getOCTax() {
                    return OCTax;
                }

                public void setOCTax(int OCTax) {
                    this.OCTax = OCTax;
                }

                public int getPartnerFee() {
                    return PartnerFee;
                }

                public void setPartnerFee(int PartnerFee) {
                    this.PartnerFee = PartnerFee;
                }

                public int getPLBEarned() {
                    return PLBEarned;
                }

                public void setPLBEarned(int PLBEarned) {
                    this.PLBEarned = PLBEarned;
                }

                public int getTdsOnPLB() {
                    return TdsOnPLB;
                }

                public void setTdsOnPLB(int TdsOnPLB) {
                    this.TdsOnPLB = TdsOnPLB;
                }

                public int getBonus() {
                    return Bonus;
                }

                public void setBonus(int Bonus) {
                    this.Bonus = Bonus;
                }

                public int getTotalFare() {
                    return TotalFare;
                }

                public void setTotalFare(int TotalFare) {
                    this.TotalFare = TotalFare;
                }

                public int getResponseStatus() {
                    return ResponseStatus;
                }

                public void setResponseStatus(int ResponseStatus) {
                    this.ResponseStatus = ResponseStatus;
                }

                public int getStatus() {
                    return Status;
                }

                public void setStatus(int Status) {
                    this.Status = Status;
                }

                public boolean isIsGSTMandatory() {
                    return IsGSTMandatory;
                }

                public void setIsGSTMandatory(boolean IsGSTMandatory) {
                    this.IsGSTMandatory = IsGSTMandatory;
                }

                public Object getMessage() {
                    return Message;
                }

                public void setMessage(Object Message) {
                    this.Message = Message;
                }

                public Object getRequiredFields() {
                    return RequiredFields;
                }

                public void setRequiredFields(Object RequiredFields) {
                    this.RequiredFields = RequiredFields;
                }

                public static class ChargeableFaresBean {

                    private int ActualBaseFare;
                    private int Tax;
                    private int STax;
                    private int SCharge;
                    private int TDiscount;
                    private int TPartnerCommission;
                    private int NetFare;
                    private int Conveniencefee;
                    private int ConveniencefeeType;
                    private PartnerFareDatailsBean PartnerFareDatails;

                    public int getActualBaseFare() {
                        return ActualBaseFare;
                    }

                    public void setActualBaseFare(int ActualBaseFare) {
                        this.ActualBaseFare = ActualBaseFare;
                    }

                    public int getTax() {
                        return Tax;
                    }

                    public void setTax(int Tax) {
                        this.Tax = Tax;
                    }

                    public int getSTax() {
                        return STax;
                    }

                    public void setSTax(int STax) {
                        this.STax = STax;
                    }

                    public int getSCharge() {
                        return SCharge;
                    }

                    public void setSCharge(int SCharge) {
                        this.SCharge = SCharge;
                    }

                    public int getTDiscount() {
                        return TDiscount;
                    }

                    public void setTDiscount(int TDiscount) {
                        this.TDiscount = TDiscount;
                    }

                    public int getTPartnerCommission() {
                        return TPartnerCommission;
                    }

                    public void setTPartnerCommission(int TPartnerCommission) {
                        this.TPartnerCommission = TPartnerCommission;
                    }

                    public int getNetFare() {
                        return NetFare;
                    }

                    public void setNetFare(int NetFare) {
                        this.NetFare = NetFare;
                    }

                    public int getConveniencefee() {
                        return Conveniencefee;
                    }

                    public void setConveniencefee(int Conveniencefee) {
                        this.Conveniencefee = Conveniencefee;
                    }

                    public int getConveniencefeeType() {
                        return ConveniencefeeType;
                    }

                    public void setConveniencefeeType(int ConveniencefeeType) {
                        this.ConveniencefeeType = ConveniencefeeType;
                    }

                    public PartnerFareDatailsBean getPartnerFareDatails() {
                        return PartnerFareDatails;
                    }

                    public void setPartnerFareDatails(PartnerFareDatailsBean PartnerFareDatails) {
                        this.PartnerFareDatails = PartnerFareDatails;
                    }

                    public static class PartnerFareDatailsBean {
                        /**
                         * NetFares : 1472
                         * Commission : 0
                         * CommissionType : 0
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
                }

                public static class NonchargeableFaresBean {
                    /**
                     * TCharge : 0
                     * TMarkup : 0
                     * TSdiscount : 0
                     */

                    private int TCharge;
                    private int TMarkup;
                    private int TSdiscount;

                    public int getTCharge() {
                        return TCharge;
                    }

                    public void setTCharge(int TCharge) {
                        this.TCharge = TCharge;
                    }

                    public int getTMarkup() {
                        return TMarkup;
                    }

                    public void setTMarkup(int TMarkup) {
                        this.TMarkup = TMarkup;
                    }

                    public int getTSdiscount() {
                        return TSdiscount;
                    }

                    public void setTSdiscount(int TSdiscount) {
                        this.TSdiscount = TSdiscount;
                    }
                }

                public static class FareBreakUpBean {
                    private List<FareAryBean> FareAry;

                    public List<FareAryBean> getFareAry() {
                        return FareAry;
                    }

                    public void setFareAry(List<FareAryBean> FareAry) {
                        this.FareAry = FareAry;
                    }

                    public static class FareAryBean {
                        /**
                         * IntPassengerType : 1
                         * IntBaseFare : 1472
                         * IntPassengerCount : 1
                         * IntTax : 931
                         * IntYQTax : 200
                         * IntTaxDataArray : [{"IntCountry":"India","IntAmount":2603}]
                         * OtherCharges : [{"Amount":0,"ChargeCode":null,"ChargeType":"0"}]
                         */

                        private String IntPassengerType;
                        private int IntBaseFare;
                        private int IntPassengerCount;
                        private int IntTax;
                        private int IntYQTax;
                        private List<IntTaxDataArrayBean> IntTaxDataArray;
                        private List<OtherChargesBean> OtherCharges;

                        public String getIntPassengerType() {
                            return IntPassengerType;
                        }

                        public void setIntPassengerType(String IntPassengerType) {
                            this.IntPassengerType = IntPassengerType;
                        }

                        public int getIntBaseFare() {
                            return IntBaseFare;
                        }

                        public void setIntBaseFare(int IntBaseFare) {
                            this.IntBaseFare = IntBaseFare;
                        }

                        public int getIntPassengerCount() {
                            return IntPassengerCount;
                        }

                        public void setIntPassengerCount(int IntPassengerCount) {
                            this.IntPassengerCount = IntPassengerCount;
                        }

                        public int getIntTax() {
                            return IntTax;
                        }

                        public void setIntTax(int IntTax) {
                            this.IntTax = IntTax;
                        }

                        public int getIntYQTax() {
                            return IntYQTax;
                        }

                        public void setIntYQTax(int IntYQTax) {
                            this.IntYQTax = IntYQTax;
                        }

                        public List<IntTaxDataArrayBean> getIntTaxDataArray() {
                            return IntTaxDataArray;
                        }

                        public void setIntTaxDataArray(List<IntTaxDataArrayBean> IntTaxDataArray) {
                            this.IntTaxDataArray = IntTaxDataArray;
                        }

                        public List<OtherChargesBean> getOtherCharges() {
                            return OtherCharges;
                        }

                        public void setOtherCharges(List<OtherChargesBean> OtherCharges) {
                            this.OtherCharges = OtherCharges;
                        }

                        public static class IntTaxDataArrayBean {
                            /**
                             * IntCountry : India
                             * IntAmount : 2603
                             */

                            private String IntCountry;
                            private int IntAmount;

                            public String getIntCountry() {
                                return IntCountry;
                            }

                            public void setIntCountry(String IntCountry) {
                                this.IntCountry = IntCountry;
                            }

                            public int getIntAmount() {
                                return IntAmount;
                            }

                            public void setIntAmount(int IntAmount) {
                                this.IntAmount = IntAmount;
                            }
                        }

                        public static class OtherChargesBean {
                            /**
                             * Amount : 0
                             * ChargeCode : null
                             * ChargeType : 0
                             */

                            private int Amount;
                            private Object ChargeCode;
                            private String ChargeType;

                            public int getAmount() {
                                return Amount;
                            }

                            public void setAmount(int Amount) {
                                this.Amount = Amount;
                            }

                            public Object getChargeCode() {
                                return ChargeCode;
                            }

                            public void setChargeCode(Object ChargeCode) {
                                this.ChargeCode = ChargeCode;
                            }

                            public String getChargeType() {
                                return ChargeType;
                            }

                            public void setChargeType(String ChargeType) {
                                this.ChargeType = ChargeType;
                            }
                        }
                    }
                }
            }

            public static class OriginDestinationoptionIdBean {
                /**
                 * Id :
                 * Key : 70ba73c4-f1cf-4fad-8777-669bef5d3292~OB1~636892207383990597182
                 */

                private String Id;
                private String Key;

                public String getId() {
                    return Id;
                }

                public void setId(String Id) {
                    this.Id = Id;
                }

                public String getKey() {
                    return Key;
                }

                public void setKey(String Key) {
                    this.Key = Key;
                }
            }

            public static class RequestDetailsBean {
                /**
                 * TravelClass : null
                 * Mode : DOMESTIC
                 * Source : HYD
                 * Destination : BLR
                 * Adults : 1
                 * Children : 0
                 * Infants : 0
                 * DepartDate : 2020-01-01T00:00:00
                 * ReturnDate : 2020-01-01T00:00:00
                 * MultiCityearch : null
                 */

                private Object TravelClass;
                private String Mode;
                private String Source;
                private String Destination;
                private String Adults;
                private String Children;
                private String Infants;
                private String DepartDate;
                private String ReturnDate;
                private Object MultiCityearch;

                public Object getTravelClass() {
                    return TravelClass;
                }

                public void setTravelClass(Object TravelClass) {
                    this.TravelClass = TravelClass;
                }

                public String getMode() {
                    return Mode;
                }

                public void setMode(String Mode) {
                    this.Mode = Mode;
                }

                public String getSource() {
                    return Source;
                }

                public void setSource(String Source) {
                    this.Source = Source;
                }

                public String getDestination() {
                    return Destination;
                }

                public void setDestination(String Destination) {
                    this.Destination = Destination;
                }

                public String getAdults() {
                    return Adults;
                }

                public void setAdults(String Adults) {
                    this.Adults = Adults;
                }

                public String getChildren() {
                    return Children;
                }

                public void setChildren(String Children) {
                    this.Children = Children;
                }

                public String getInfants() {
                    return Infants;
                }

                public void setInfants(String Infants) {
                    this.Infants = Infants;
                }

                public String getDepartDate() {
                    return DepartDate;
                }

                public void setDepartDate(String DepartDate) {
                    this.DepartDate = DepartDate;
                }

                public String getReturnDate() {
                    return ReturnDate;
                }

                public void setReturnDate(String ReturnDate) {
                    this.ReturnDate = ReturnDate;
                }

                public Object getMultiCityearch() {
                    return MultiCityearch;
                }

                public void setMultiCityearch(Object MultiCityearch) {
                    this.MultiCityearch = MultiCityearch;
                }
            }

            public static class FlightSegmentsBean {

                private String AirEquipType;
                private String ArrivalAirportCode;
                private String ArrivalDateTime;
                private String ArrivalDateTimeZone;
                private String DepartureAirportCode;
                private String DepartureDateTime;
                private String DepartureDateTimeZone;
                private String Duration;
                private String FlightNumber;
                private String OperatingAirlineCode;
                private String OperatingAirlineFlightNumber;
                private String RPH;
                private String StopQuantity;
                private String AirLineName;
                private Object AirportTax;
                private String ImageFileName;
                private String ImagePath;
                private String ViaFlight;
                private String Discount;
                private String AirportTaxChild;
                private String AirportTaxInfant;
                private String AdultTaxBreakup;
                private String ChildTaxBreakup;
                private String InfantTaxBreakup;
                private String OcTax;
                private BookingClassBean BookingClass;
                private BookingClassFareBean BookingClassFare;
                private Object IntNumStops;
                private Object IntOperatingAirlineName;
                private String IntArrivalAirportName;
                private String IntDepartureAirportName;
                private String IntMarketingAirlineCode;
                private Object IntLinkSellAgrmnt;
                private Object IntConx;
                private Object IntAirpChg;
                private Object IntInsideAvailOption;
                private Object IntGenTrafRestriction;
                private Object IntDaysOperates;
                private Object IntJourneyTime;
                private Object IntEndDate;
                private String IntStartTerminal;
                private String IntEndTerminal;
                private Object IntFltTm;
                private Object IntLSAInd;
                private String IntMile;
                private Object Cabin;
                private String itineraryNumber;
                private BaggageAllowedBean BaggageAllowed;
                private Object PaxWiseBaggageAllowed;
                private String AccumulatedDuration;
                private String GroundTime;

                public String getAirEquipType() {
                    return AirEquipType;
                }

                public void setAirEquipType(String AirEquipType) {
                    this.AirEquipType = AirEquipType;
                }

                public String getArrivalAirportCode() {
                    return ArrivalAirportCode;
                }

                public void setArrivalAirportCode(String ArrivalAirportCode) {
                    this.ArrivalAirportCode = ArrivalAirportCode;
                }

                public String getArrivalDateTime() {
                    return ArrivalDateTime;
                }

                public void setArrivalDateTime(String ArrivalDateTime) {
                    this.ArrivalDateTime = ArrivalDateTime;
                }

                public String getArrivalDateTimeZone() {
                    return ArrivalDateTimeZone;
                }

                public void setArrivalDateTimeZone(String ArrivalDateTimeZone) {
                    this.ArrivalDateTimeZone = ArrivalDateTimeZone;
                }

                public String getDepartureAirportCode() {
                    return DepartureAirportCode;
                }

                public void setDepartureAirportCode(String DepartureAirportCode) {
                    this.DepartureAirportCode = DepartureAirportCode;
                }

                public String getDepartureDateTime() {
                    return DepartureDateTime;
                }

                public void setDepartureDateTime(String DepartureDateTime) {
                    this.DepartureDateTime = DepartureDateTime;
                }

                public String getDepartureDateTimeZone() {
                    return DepartureDateTimeZone;
                }

                public void setDepartureDateTimeZone(String DepartureDateTimeZone) {
                    this.DepartureDateTimeZone = DepartureDateTimeZone;
                }

                public String getDuration() {
                    return Duration;
                }

                public void setDuration(String Duration) {
                    this.Duration = Duration;
                }

                public String getFlightNumber() {
                    return FlightNumber;
                }

                public void setFlightNumber(String FlightNumber) {
                    this.FlightNumber = FlightNumber;
                }

                public String getOperatingAirlineCode() {
                    return OperatingAirlineCode;
                }

                public void setOperatingAirlineCode(String OperatingAirlineCode) {
                    this.OperatingAirlineCode = OperatingAirlineCode;
                }

                public String getOperatingAirlineFlightNumber() {
                    return OperatingAirlineFlightNumber;
                }

                public void setOperatingAirlineFlightNumber(String OperatingAirlineFlightNumber) {
                    this.OperatingAirlineFlightNumber = OperatingAirlineFlightNumber;
                }

                public String getRPH() {
                    return RPH;
                }

                public void setRPH(String RPH) {
                    this.RPH = RPH;
                }

                public String getStopQuantity() {
                    return StopQuantity;
                }

                public void setStopQuantity(String StopQuantity) {
                    this.StopQuantity = StopQuantity;
                }

                public String getAirLineName() {
                    return AirLineName;
                }

                public void setAirLineName(String AirLineName) {
                    this.AirLineName = AirLineName;
                }

                public Object getAirportTax() {
                    return AirportTax;
                }

                public void setAirportTax(Object AirportTax) {
                    this.AirportTax = AirportTax;
                }

                public String getImageFileName() {
                    return ImageFileName;
                }

                public void setImageFileName(String ImageFileName) {
                    this.ImageFileName = ImageFileName;
                }

                public String getImagePath() {
                    return ImagePath;
                }

                public void setImagePath(String ImagePath) {
                    this.ImagePath = ImagePath;
                }

                public String getViaFlight() {
                    return ViaFlight;
                }

                public void setViaFlight(String ViaFlight) {
                    this.ViaFlight = ViaFlight;
                }

                public String getDiscount() {
                    return Discount;
                }

                public void setDiscount(String Discount) {
                    this.Discount = Discount;
                }

                public String getAirportTaxChild() {
                    return AirportTaxChild;
                }

                public void setAirportTaxChild(String AirportTaxChild) {
                    this.AirportTaxChild = AirportTaxChild;
                }

                public String getAirportTaxInfant() {
                    return AirportTaxInfant;
                }

                public void setAirportTaxInfant(String AirportTaxInfant) {
                    this.AirportTaxInfant = AirportTaxInfant;
                }

                public String getAdultTaxBreakup() {
                    return AdultTaxBreakup;
                }

                public void setAdultTaxBreakup(String AdultTaxBreakup) {
                    this.AdultTaxBreakup = AdultTaxBreakup;
                }

                public String getChildTaxBreakup() {
                    return ChildTaxBreakup;
                }

                public void setChildTaxBreakup(String ChildTaxBreakup) {
                    this.ChildTaxBreakup = ChildTaxBreakup;
                }

                public String getInfantTaxBreakup() {
                    return InfantTaxBreakup;
                }

                public void setInfantTaxBreakup(String InfantTaxBreakup) {
                    this.InfantTaxBreakup = InfantTaxBreakup;
                }

                public String getOcTax() {
                    return OcTax;
                }

                public void setOcTax(String OcTax) {
                    this.OcTax = OcTax;
                }

                public BookingClassBean getBookingClass() {
                    return BookingClass;
                }

                public void setBookingClass(BookingClassBean BookingClass) {
                    this.BookingClass = BookingClass;
                }

                public BookingClassFareBean getBookingClassFare() {
                    return BookingClassFare;
                }

                public void setBookingClassFare(BookingClassFareBean BookingClassFare) {
                    this.BookingClassFare = BookingClassFare;
                }

                public Object getIntNumStops() {
                    return IntNumStops;
                }

                public void setIntNumStops(Object IntNumStops) {
                    this.IntNumStops = IntNumStops;
                }

                public Object getIntOperatingAirlineName() {
                    return IntOperatingAirlineName;
                }

                public void setIntOperatingAirlineName(Object IntOperatingAirlineName) {
                    this.IntOperatingAirlineName = IntOperatingAirlineName;
                }

                public String getIntArrivalAirportName() {
                    return IntArrivalAirportName;
                }

                public void setIntArrivalAirportName(String IntArrivalAirportName) {
                    this.IntArrivalAirportName = IntArrivalAirportName;
                }

                public String getIntDepartureAirportName() {
                    return IntDepartureAirportName;
                }

                public void setIntDepartureAirportName(String IntDepartureAirportName) {
                    this.IntDepartureAirportName = IntDepartureAirportName;
                }

                public String getIntMarketingAirlineCode() {
                    return IntMarketingAirlineCode;
                }

                public void setIntMarketingAirlineCode(String IntMarketingAirlineCode) {
                    this.IntMarketingAirlineCode = IntMarketingAirlineCode;
                }

                public Object getIntLinkSellAgrmnt() {
                    return IntLinkSellAgrmnt;
                }

                public void setIntLinkSellAgrmnt(Object IntLinkSellAgrmnt) {
                    this.IntLinkSellAgrmnt = IntLinkSellAgrmnt;
                }

                public Object getIntConx() {
                    return IntConx;
                }

                public void setIntConx(Object IntConx) {
                    this.IntConx = IntConx;
                }

                public Object getIntAirpChg() {
                    return IntAirpChg;
                }

                public void setIntAirpChg(Object IntAirpChg) {
                    this.IntAirpChg = IntAirpChg;
                }

                public Object getIntInsideAvailOption() {
                    return IntInsideAvailOption;
                }

                public void setIntInsideAvailOption(Object IntInsideAvailOption) {
                    this.IntInsideAvailOption = IntInsideAvailOption;
                }

                public Object getIntGenTrafRestriction() {
                    return IntGenTrafRestriction;
                }

                public void setIntGenTrafRestriction(Object IntGenTrafRestriction) {
                    this.IntGenTrafRestriction = IntGenTrafRestriction;
                }

                public Object getIntDaysOperates() {
                    return IntDaysOperates;
                }

                public void setIntDaysOperates(Object IntDaysOperates) {
                    this.IntDaysOperates = IntDaysOperates;
                }

                public Object getIntJourneyTime() {
                    return IntJourneyTime;
                }

                public void setIntJourneyTime(Object IntJourneyTime) {
                    this.IntJourneyTime = IntJourneyTime;
                }

                public Object getIntEndDate() {
                    return IntEndDate;
                }

                public void setIntEndDate(Object IntEndDate) {
                    this.IntEndDate = IntEndDate;
                }

                public String getIntStartTerminal() {
                    return IntStartTerminal;
                }

                public void setIntStartTerminal(String IntStartTerminal) {
                    this.IntStartTerminal = IntStartTerminal;
                }

                public String getIntEndTerminal() {
                    return IntEndTerminal;
                }

                public void setIntEndTerminal(String IntEndTerminal) {
                    this.IntEndTerminal = IntEndTerminal;
                }

                public Object getIntFltTm() {
                    return IntFltTm;
                }

                public void setIntFltTm(Object IntFltTm) {
                    this.IntFltTm = IntFltTm;
                }

                public Object getIntLSAInd() {
                    return IntLSAInd;
                }

                public void setIntLSAInd(Object IntLSAInd) {
                    this.IntLSAInd = IntLSAInd;
                }

                public String getIntMile() {
                    return IntMile;
                }

                public void setIntMile(String IntMile) {
                    this.IntMile = IntMile;
                }

                public Object getCabin() {
                    return Cabin;
                }

                public void setCabin(Object Cabin) {
                    this.Cabin = Cabin;
                }

                public String getItineraryNumber() {
                    return itineraryNumber;
                }

                public void setItineraryNumber(String itineraryNumber) {
                    this.itineraryNumber = itineraryNumber;
                }

                public BaggageAllowedBean getBaggageAllowed() {
                    return BaggageAllowed;
                }

                public void setBaggageAllowed(BaggageAllowedBean BaggageAllowed) {
                    this.BaggageAllowed = BaggageAllowed;
                }

                public Object getPaxWiseBaggageAllowed() {
                    return PaxWiseBaggageAllowed;
                }

                public void setPaxWiseBaggageAllowed(Object PaxWiseBaggageAllowed) {
                    this.PaxWiseBaggageAllowed = PaxWiseBaggageAllowed;
                }

                public String getAccumulatedDuration() {
                    return AccumulatedDuration;
                }

                public void setAccumulatedDuration(String AccumulatedDuration) {
                    this.AccumulatedDuration = AccumulatedDuration;
                }

                public String getGroundTime() {
                    return GroundTime;
                }

                public void setGroundTime(String GroundTime) {
                    this.GroundTime = GroundTime;
                }

                public static class BookingClassBean {
                    /**
                     * Availability : 9
                     * ResBookDesigCode : L
                     * IntBIC :
                     */

                    private String Availability;
                    private String ResBookDesigCode;
                    private String IntBIC;

                    public String getAvailability() {
                        return Availability;
                    }

                    public void setAvailability(String Availability) {
                        this.Availability = Availability;
                    }

                    public String getResBookDesigCode() {
                        return ResBookDesigCode;
                    }

                    public void setResBookDesigCode(String ResBookDesigCode) {
                        this.ResBookDesigCode = ResBookDesigCode;
                    }

                    public String getIntBIC() {
                        return IntBIC;
                    }

                    public void setIntBIC(String IntBIC) {
                        this.IntBIC = IntBIC;
                    }
                }

                public static class BookingClassFareBean {
                    /**
                     * AdultFare :
                     * Bookingclass :
                     * ClassType : L
                     * Farebasiscode : L0IP
                     * Rule : Refundable
                     * AdultCommission : null
                     * ChildCommission : null
                     * CommissionOnTCharge : null
                     * ChildFare : null
                     * InfantFare : null
                     */

                    private String AdultFare;
                    private String Bookingclass;
                    private String ClassType;
                    private String Farebasiscode;
                    private String Rule;
                    private Object AdultCommission;
                    private Object ChildCommission;
                    private Object CommissionOnTCharge;
                    private Object ChildFare;
                    private Object InfantFare;

                    public String getAdultFare() {
                        return AdultFare;
                    }

                    public void setAdultFare(String AdultFare) {
                        this.AdultFare = AdultFare;
                    }

                    public String getBookingclass() {
                        return Bookingclass;
                    }

                    public void setBookingclass(String Bookingclass) {
                        this.Bookingclass = Bookingclass;
                    }

                    public String getClassType() {
                        return ClassType;
                    }

                    public void setClassType(String ClassType) {
                        this.ClassType = ClassType;
                    }

                    public String getFarebasiscode() {
                        return Farebasiscode;
                    }

                    public void setFarebasiscode(String Farebasiscode) {
                        this.Farebasiscode = Farebasiscode;
                    }

                    public String getRule() {
                        return Rule;
                    }

                    public void setRule(String Rule) {
                        this.Rule = Rule;
                    }

                    public Object getAdultCommission() {
                        return AdultCommission;
                    }

                    public void setAdultCommission(Object AdultCommission) {
                        this.AdultCommission = AdultCommission;
                    }

                    public Object getChildCommission() {
                        return ChildCommission;
                    }

                    public void setChildCommission(Object ChildCommission) {
                        this.ChildCommission = ChildCommission;
                    }

                    public Object getCommissionOnTCharge() {
                        return CommissionOnTCharge;
                    }

                    public void setCommissionOnTCharge(Object CommissionOnTCharge) {
                        this.CommissionOnTCharge = CommissionOnTCharge;
                    }

                    public Object getChildFare() {
                        return ChildFare;
                    }

                    public void setChildFare(Object ChildFare) {
                        this.ChildFare = ChildFare;
                    }

                    public Object getInfantFare() {
                        return InfantFare;
                    }

                    public void setInfantFare(Object InfantFare) {
                        this.InfantFare = InfantFare;
                    }
                }

                public static class BaggageAllowedBean {
                    /**
                     * CheckInBaggage : 15 KG
                     * HandBaggage : 7 KG
                     */

                    private String CheckInBaggage;
                    private String HandBaggage;

                    public String getCheckInBaggage() {
                        return CheckInBaggage;
                    }

                    public void setCheckInBaggage(String CheckInBaggage) {
                        this.CheckInBaggage = CheckInBaggage;
                    }

                    public String getHandBaggage() {
                        return HandBaggage;
                    }

                    public void setHandBaggage(String HandBaggage) {
                        this.HandBaggage = HandBaggage;
                    }
                }
            }
        }
    }
}