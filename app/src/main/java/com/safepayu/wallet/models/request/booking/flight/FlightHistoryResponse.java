package com.safepayu.wallet.models.request.booking.flight;

import java.util.List;

public class FlightHistoryResponse {


    /**
     * status : true
     * message : Flight History
     * data : [{"DeviceModel":null,"DeviceOS":null,"DeviceOSVersion":null,"DeviceToken":null,"ApplicationType":0,"TicketRefNo":"","LastTicketDate":"","FrequentFlyerDetails":"","IsBlockTicket":false,"IsAgentPaymentGateway":false,"BookingResponseXML":"<FlightTicketBookingResponse><ReferenceNo>300344016590<\/ReferenceNo><APIReferenceNo /><Message>Ticket booked successully<\/Message><ResponseStatus>OK<\/ResponseStatus><RefundResponse /><BookingStatus>SuccessTkd<\/BookingStatus><GDFPNRNo>NQ1FP4P7|VX6WC7UY<\/GDFPNRNo><EticketNo>IC0QFD7O|SBV8F8TH|TN381TRH|T0B6NM0M<\/EticketNo><Flightuid /><Passworduid /><TransactionId>NQ1FP4P7|VX6WC7UY<\/TransactionId><LastTicketDate /><TransactionDetails /><Tickets><Eticket><ChangeRequestId /><EticketNo>IC0QFD7O<\/EticketNo><TicketId>996583<\/TicketId><FlightuId /><PassuId>451944<\/PassuId><GivenName>Guru<\/GivenName><SurName>Bahrath<\/SurName><NameReference>Mr. Guru Bahrath<\/NameReference><TripType>Single<\/TripType><Status>0<\/Status><\/Eticket><Eticket><ChangeRequestId /><EticketNo>SBV8F8TH<\/EticketNo><TicketId>174483<\/TicketId><FlightuId /><PassuId>441721<\/PassuId><GivenName>Guru<\/GivenName><SurName>Bahrath<\/SurName><NameReference>Mr. Guru Bahrath<\/NameReference><TripType>Single<\/TripType><Status>0<\/Status><\/Eticket><Eticket><ChangeRequestId /><EticketNo>TN381TRH<\/EticketNo><TicketId>225732<\/TicketId><FlightuId /><PassuId>799623<\/PassuId><GivenName>Guru<\/GivenName><SurName>Bahrath<\/SurName><NameReference>Mr. Guru Bahrath<\/NameReference><TripType>Round<\/TripType><Status>0<\/Status><\/Eticket><Eticket><ChangeRequestId /><EticketNo>T0B6NM0M<\/EticketNo><TicketId>723626<\/TicketId><FlightuId /><PassuId>325375<\/PassuId><GivenName>Guru<\/GivenName><SurName>Bahrath<\/SurName><NameReference>Mr. Guru Bahrath<\/NameReference><TripType>Round<\/TripType><Status>0<\/Status><\/Eticket><\/Tickets><\/FlightTicketBookingResponse>","IsOfflineBooking":false,"EcommerceSegment":[],"BookingRefNo":"300344016590","BookingStatus":3,"APIRefNo":"NQ1FP4P7|VX6WC7UY","Provider":"OpvFxVwo03md+gnyIb8Q+A==","PaymentId":null,"Names":"Mr.|Guru|Bahrath|adt~Mr.|Guru|Bahrath|adt","ages":"34~29","Genders":"M~M","PanNumber":"","telePhone":"","ISD":"","MobileNo":"9811871855","EmailId":"adarshmandal515@gmail.com","dob":"21-10-1985~25-5-1990","psgrtype":"","Address":"B129  B Block Sector 65","State":"Uttar Pradesh","City":"Noida","PostalCode":"201301","PassportDetails":"","SMSUsageCount":0,"ImagePath":null,"ImagePathRet":null,"Rule":"","keyRet":"49708da0-d821-46e0-a7f4-ccea4ee3831d~IB1~636892211974468103182","FlightId":"","FlightIdRet":"","OnwardFlightSegments":[{"AirEquipType":"320","ArrivalAirportCode":"BLR","ArrivalDateTime":"2020-01-01T09:50:00","ArrivalDateTimeZone":null,"DepartureAirportCode":"HYD","DepartureDateTime":"2020-01-01T08:25:00","DepartureDateTimeZone":null,"Duration":"01:25 hrs","FlightNumber":"151","OperatingAirlineCode":"6E","OperatingAirlineFlightNumber":"151","RPH":"","StopQuantity":"0","AirLineName":"Indigo","AirportTax":"","ImageFileName":"6E","ImagePath":null,"ViaFlight":"","Discount":"0","AirportTaxChild":"0","AirportTaxInfant":"0","AdultTaxBreakup":"0","ChildTaxBreakup":"0","InfantTaxBreakup":"0","OcTax":"0","BookingClass":{"Availability":"9","ResBookDesigCode":"L","IntBIC":""},"BookingClassFare":{"AdultFare":"","Bookingclass":"","ClassType":"L","Farebasiscode":"L0IP","Rule":"Refundable","AdultCommission":"","ChildCommission":"","CommissionOnTCharge":"","ChildFare":"","InfantFare":""},"IntNumStops":"","IntOperatingAirlineName":"","IntArrivalAirportName":"Bangalore","IntDepartureAirportName":"Hyderabad","IntMarketingAirlineCode":"151","IntLinkSellAgrmnt":"","IntConx":"","IntAirpChg":"","IntInsideAvailOption":"","IntGenTrafRestriction":"","IntDaysOperates":"","IntJourneyTime":"","IntEndDate":"","IntStartTerminal":"","IntEndTerminal":"1","IntFltTm":"","IntLSAInd":"","IntMile":"0","Cabin":null,"itineraryNumber":"637095282517603061-217-1499_ET0","BaggageAllowed":{"CheckInBaggage":"15 KG","HandBaggage":"7 KG"},"PaxWiseBaggageAllowed":[],"AccumulatedDuration":"0","GroundTime":"00:00 hrs"}],"ReturnFlightSegments":[{"AirEquipType":"320","ArrivalAirportCode":"HYD","ArrivalDateTime":"2020-01-03T20:00:00","ArrivalDateTimeZone":null,"DepartureAirportCode":"BLR","DepartureDateTime":"2020-01-03T18:45:00","DepartureDateTimeZone":null,"Duration":"01:15 hrs","FlightNumber":"855","OperatingAirlineCode":"6E","OperatingAirlineFlightNumber":"855","RPH":"","StopQuantity":"0","AirLineName":"Indigo","AirportTax":"","ImageFileName":"6E","ImagePath":null,"ViaFlight":"","Discount":"0","AirportTaxChild":"0","AirportTaxInfant":"0","AdultTaxBreakup":"0","ChildTaxBreakup":"0","InfantTaxBreakup":"0","OcTax":"0","BookingClass":{"Availability":"9","ResBookDesigCode":"L","IntBIC":""},"BookingClassFare":{"AdultFare":"","Bookingclass":"","ClassType":"L","Farebasiscode":"L0IP","Rule":"Refundable","AdultCommission":"","ChildCommission":"","CommissionOnTCharge":"","ChildFare":"","InfantFare":""},"IntNumStops":"","IntOperatingAirlineName":"","IntArrivalAirportName":"Hyderabad","IntDepartureAirportName":"Bangalore","IntMarketingAirlineCode":"855","IntLinkSellAgrmnt":"","IntConx":"","IntAirpChg":"","IntInsideAvailOption":"","IntGenTrafRestriction":"","IntDaysOperates":"","IntJourneyTime":"","IntEndDate":"","IntStartTerminal":"1","IntEndTerminal":"","IntFltTm":"","IntLSAInd":"","IntMile":"0","Cabin":null,"itineraryNumber":"637095285310831870-217-1499_ETR0","BaggageAllowed":{"CheckInBaggage":"15 KG","HandBaggage":"7 KG"},"PaxWiseBaggageAllowed":null,"AccumulatedDuration":"0","GroundTime":"00:00 hrs"}],"FareDetails":{"ChargeableFares":{"ActualBaseFare":0,"Tax":0,"STax":0,"SCharge":0,"TDiscount":0,"TPartnerCommission":0,"NetFare":0,"Conveniencefee":0,"ConveniencefeeType":0,"PartnerFareDatails":null},"NonchargeableFares":{"TCharge":0,"TMarkup":0,"TSdiscount":0},"FareBreakUp":{"FareAry":[]},"OCTax":0,"PartnerFee":0,"PLBEarned":0,"TdsOnPLB":0,"Bonus":0,"TotalFare":0,"ResponseStatus":0,"Status":0,"IsGSTMandatory":false,"Message":null,"RequiredFields":null},"BookingDate":"2019-12-16T20:09:00","PromoCode":null,"PromoCodeAmount":0,"PostMarkup":0,"IsLCC":"true","IsLCCRet":"true","BookedFrom":null,"CreatedById":3942,"IsWallet":false,"IsPartnerAgentDetails":{"AgentId":"","ServiceId":null,"ServiceName":null,"Amount":null,"UserCharges":null,"ResCode":null,"ResDesc":null,"Requestid":"","AgentFee":0},"OcTax":0,"CurrencyID":"INR","CurrencyValue":"1","PLBEarned":0,"TdsOnPLB":0,"ActualBaseFare":1472,"Tax":1131,"NetFare":0,"Commission":0,"Bonus":0,"STax":0,"SCharge":0,"TDiscount":0,"TPartnerCommission":0,"TCharge":0,"TMarkup":0,"TSdiscount":0,"PartnerFee":0,"TransactionId":"NQ1FP4P7|VX6WC7UY","Conveniencefee":0,"ConveniencefeeType":0,"ConvenienceFeeTotal":0,"EProductPrice":0,"PLBEarnedRet":0,"TdsOnPLBRet":0,"ActualBaseFareRet":4392,"NetFareRet":0,"BonusRet":0,"CommissionRet":0,"TaxRet":1318,"STaxRet":0,"SChargeRet":0,"TDiscountRet":0,"TSDiscountRet":0,"TPartnerCommissionRet":0,"EProductPriceRet":0,"TChargeRet":0,"TMarkupRet":0,"ConveniencefeeRet":0,"PartnerFeeRet":0,"ConveniencefeeTypeRet":0,"TermsAndConditions":"","OfferInformation":"","GSTDetails":{"GSTCompanyAddress":"","GSTCompanyContactNumber":"","GSTCompanyName":"","GSTNumber":"","GSTCompanyEmail":"","GSTFirstName":null,"GSTLastName":null},"SSRRequests":null,"Tickets":[{"ChangeRequestId":null,"EticketNo":"IC0QFD7O","TicketId":"996583","FlightuId":"","PassuId":"451944","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":1,"Status":0},{"ChangeRequestId":null,"EticketNo":"SBV8F8TH","TicketId":"174483","FlightuId":"","PassuId":"441721","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":1,"Status":0},{"ChangeRequestId":null,"EticketNo":"TN381TRH","TicketId":"225732","FlightuId":"","PassuId":"799623","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":2,"Status":0},{"ChangeRequestId":null,"EticketNo":"T0B6NM0M","TicketId":"723626","FlightuId":"","PassuId":"325375","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":2,"Status":0}],"IsHoldAllowedRet":false,"IsHoldAllowed":false,"IsGSTMandatoryRet":false,"IsGSTMandatory":false,"RequiredFields":null,"RequiredFieldsRet":null,"Source":"HYD","SourceName":"HYDERABAD, India- (HYD)-Rajiv Gandhi Airport","Destination":"BLR","DestinationName":"BANGALORE, India- (BLR)-Bangalore International Airport","JourneyDate":"01-01-2020","ReturnDate":"03-01-2020","TripType":2,"FlightType":1,"AdultPax":2,"ChildPax":0,"InfantPax":0,"TravelClass":"L","IsNonStopFlight":false,"FlightTimings":null,"AirlineName":null,"User":"","UserType":5,"IsGDS":null,"AffiliateId":null,"WebsiteUrl":null,"MultiFlightsSearch":null,"onward_fare":2603,"return_fare":"5710.00"}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * DeviceModel : null
         * DeviceOS : null
         * DeviceOSVersion : null
         * DeviceToken : null
         * ApplicationType : 0
         * TicketRefNo :
         * LastTicketDate :
         * FrequentFlyerDetails :
         * IsBlockTicket : false
         * IsAgentPaymentGateway : false
         * BookingResponseXML : <FlightTicketBookingResponse><ReferenceNo>300344016590</ReferenceNo><APIReferenceNo /><Message>Ticket booked successully</Message><ResponseStatus>OK</ResponseStatus><RefundResponse /><BookingStatus>SuccessTkd</BookingStatus><GDFPNRNo>NQ1FP4P7|VX6WC7UY</GDFPNRNo><EticketNo>IC0QFD7O|SBV8F8TH|TN381TRH|T0B6NM0M</EticketNo><Flightuid /><Passworduid /><TransactionId>NQ1FP4P7|VX6WC7UY</TransactionId><LastTicketDate /><TransactionDetails /><Tickets><Eticket><ChangeRequestId /><EticketNo>IC0QFD7O</EticketNo><TicketId>996583</TicketId><FlightuId /><PassuId>451944</PassuId><GivenName>Guru</GivenName><SurName>Bahrath</SurName><NameReference>Mr. Guru Bahrath</NameReference><TripType>Single</TripType><Status>0</Status></Eticket><Eticket><ChangeRequestId /><EticketNo>SBV8F8TH</EticketNo><TicketId>174483</TicketId><FlightuId /><PassuId>441721</PassuId><GivenName>Guru</GivenName><SurName>Bahrath</SurName><NameReference>Mr. Guru Bahrath</NameReference><TripType>Single</TripType><Status>0</Status></Eticket><Eticket><ChangeRequestId /><EticketNo>TN381TRH</EticketNo><TicketId>225732</TicketId><FlightuId /><PassuId>799623</PassuId><GivenName>Guru</GivenName><SurName>Bahrath</SurName><NameReference>Mr. Guru Bahrath</NameReference><TripType>Round</TripType><Status>0</Status></Eticket><Eticket><ChangeRequestId /><EticketNo>T0B6NM0M</EticketNo><TicketId>723626</TicketId><FlightuId /><PassuId>325375</PassuId><GivenName>Guru</GivenName><SurName>Bahrath</SurName><NameReference>Mr. Guru Bahrath</NameReference><TripType>Round</TripType><Status>0</Status></Eticket></Tickets></FlightTicketBookingResponse>
         * IsOfflineBooking : false
         * EcommerceSegment : []
         * BookingRefNo : 300344016590
         * BookingStatus : 3
         * APIRefNo : NQ1FP4P7|VX6WC7UY
         * Provider : OpvFxVwo03md+gnyIb8Q+A==
         * PaymentId : null
         * Names : Mr.|Guru|Bahrath|adt~Mr.|Guru|Bahrath|adt
         * ages : 34~29
         * Genders : M~M
         * PanNumber :
         * telePhone :
         * ISD :
         * MobileNo : 9811871855
         * EmailId : adarshmandal515@gmail.com
         * dob : 21-10-1985~25-5-1990
         * psgrtype :
         * Address : B129  B Block Sector 65
         * State : Uttar Pradesh
         * City : Noida
         * PostalCode : 201301
         * PassportDetails :
         * SMSUsageCount : 0
         * ImagePath : null
         * ImagePathRet : null
         * Rule :
         * keyRet : 49708da0-d821-46e0-a7f4-ccea4ee3831d~IB1~636892211974468103182
         * FlightId :
         * FlightIdRet :
         * OnwardFlightSegments : [{"AirEquipType":"320","ArrivalAirportCode":"BLR","ArrivalDateTime":"2020-01-01T09:50:00","ArrivalDateTimeZone":null,"DepartureAirportCode":"HYD","DepartureDateTime":"2020-01-01T08:25:00","DepartureDateTimeZone":null,"Duration":"01:25 hrs","FlightNumber":"151","OperatingAirlineCode":"6E","OperatingAirlineFlightNumber":"151","RPH":"","StopQuantity":"0","AirLineName":"Indigo","AirportTax":"","ImageFileName":"6E","ImagePath":null,"ViaFlight":"","Discount":"0","AirportTaxChild":"0","AirportTaxInfant":"0","AdultTaxBreakup":"0","ChildTaxBreakup":"0","InfantTaxBreakup":"0","OcTax":"0","BookingClass":{"Availability":"9","ResBookDesigCode":"L","IntBIC":""},"BookingClassFare":{"AdultFare":"","Bookingclass":"","ClassType":"L","Farebasiscode":"L0IP","Rule":"Refundable","AdultCommission":"","ChildCommission":"","CommissionOnTCharge":"","ChildFare":"","InfantFare":""},"IntNumStops":"","IntOperatingAirlineName":"","IntArrivalAirportName":"Bangalore","IntDepartureAirportName":"Hyderabad","IntMarketingAirlineCode":"151","IntLinkSellAgrmnt":"","IntConx":"","IntAirpChg":"","IntInsideAvailOption":"","IntGenTrafRestriction":"","IntDaysOperates":"","IntJourneyTime":"","IntEndDate":"","IntStartTerminal":"","IntEndTerminal":"1","IntFltTm":"","IntLSAInd":"","IntMile":"0","Cabin":null,"itineraryNumber":"637095282517603061-217-1499_ET0","BaggageAllowed":{"CheckInBaggage":"15 KG","HandBaggage":"7 KG"},"PaxWiseBaggageAllowed":[],"AccumulatedDuration":"0","GroundTime":"00:00 hrs"}]
         * ReturnFlightSegments : [{"AirEquipType":"320","ArrivalAirportCode":"HYD","ArrivalDateTime":"2020-01-03T20:00:00","ArrivalDateTimeZone":null,"DepartureAirportCode":"BLR","DepartureDateTime":"2020-01-03T18:45:00","DepartureDateTimeZone":null,"Duration":"01:15 hrs","FlightNumber":"855","OperatingAirlineCode":"6E","OperatingAirlineFlightNumber":"855","RPH":"","StopQuantity":"0","AirLineName":"Indigo","AirportTax":"","ImageFileName":"6E","ImagePath":null,"ViaFlight":"","Discount":"0","AirportTaxChild":"0","AirportTaxInfant":"0","AdultTaxBreakup":"0","ChildTaxBreakup":"0","InfantTaxBreakup":"0","OcTax":"0","BookingClass":{"Availability":"9","ResBookDesigCode":"L","IntBIC":""},"BookingClassFare":{"AdultFare":"","Bookingclass":"","ClassType":"L","Farebasiscode":"L0IP","Rule":"Refundable","AdultCommission":"","ChildCommission":"","CommissionOnTCharge":"","ChildFare":"","InfantFare":""},"IntNumStops":"","IntOperatingAirlineName":"","IntArrivalAirportName":"Hyderabad","IntDepartureAirportName":"Bangalore","IntMarketingAirlineCode":"855","IntLinkSellAgrmnt":"","IntConx":"","IntAirpChg":"","IntInsideAvailOption":"","IntGenTrafRestriction":"","IntDaysOperates":"","IntJourneyTime":"","IntEndDate":"","IntStartTerminal":"1","IntEndTerminal":"","IntFltTm":"","IntLSAInd":"","IntMile":"0","Cabin":null,"itineraryNumber":"637095285310831870-217-1499_ETR0","BaggageAllowed":{"CheckInBaggage":"15 KG","HandBaggage":"7 KG"},"PaxWiseBaggageAllowed":null,"AccumulatedDuration":"0","GroundTime":"00:00 hrs"}]
         * FareDetails : {"ChargeableFares":{"ActualBaseFare":0,"Tax":0,"STax":0,"SCharge":0,"TDiscount":0,"TPartnerCommission":0,"NetFare":0,"Conveniencefee":0,"ConveniencefeeType":0,"PartnerFareDatails":null},"NonchargeableFares":{"TCharge":0,"TMarkup":0,"TSdiscount":0},"FareBreakUp":{"FareAry":[]},"OCTax":0,"PartnerFee":0,"PLBEarned":0,"TdsOnPLB":0,"Bonus":0,"TotalFare":0,"ResponseStatus":0,"Status":0,"IsGSTMandatory":false,"Message":null,"RequiredFields":null}
         * BookingDate : 2019-12-16T20:09:00
         * PromoCode : null
         * PromoCodeAmount : 0
         * PostMarkup : 0
         * IsLCC : true
         * IsLCCRet : true
         * BookedFrom : null
         * CreatedById : 3942
         * IsWallet : false
         * IsPartnerAgentDetails : {"AgentId":"","ServiceId":null,"ServiceName":null,"Amount":null,"UserCharges":null,"ResCode":null,"ResDesc":null,"Requestid":"","AgentFee":0}
         * OcTax : 0
         * CurrencyID : INR
         * CurrencyValue : 1
         * PLBEarned : 0
         * TdsOnPLB : 0
         * ActualBaseFare : 1472
         * Tax : 1131
         * NetFare : 0
         * Commission : 0
         * Bonus : 0
         * STax : 0
         * SCharge : 0
         * TDiscount : 0
         * TPartnerCommission : 0
         * TCharge : 0
         * TMarkup : 0
         * TSdiscount : 0
         * PartnerFee : 0
         * TransactionId : NQ1FP4P7|VX6WC7UY
         * Conveniencefee : 0
         * ConveniencefeeType : 0
         * ConvenienceFeeTotal : 0
         * EProductPrice : 0
         * PLBEarnedRet : 0
         * TdsOnPLBRet : 0
         * ActualBaseFareRet : 4392
         * NetFareRet : 0
         * BonusRet : 0
         * CommissionRet : 0
         * TaxRet : 1318
         * STaxRet : 0
         * SChargeRet : 0
         * TDiscountRet : 0
         * TSDiscountRet : 0
         * TPartnerCommissionRet : 0
         * EProductPriceRet : 0
         * TChargeRet : 0
         * TMarkupRet : 0
         * ConveniencefeeRet : 0
         * PartnerFeeRet : 0
         * ConveniencefeeTypeRet : 0
         * TermsAndConditions :
         * OfferInformation :
         * GSTDetails : {"GSTCompanyAddress":"","GSTCompanyContactNumber":"","GSTCompanyName":"","GSTNumber":"","GSTCompanyEmail":"","GSTFirstName":null,"GSTLastName":null}
         * SSRRequests : null
         * Tickets : [{"ChangeRequestId":null,"EticketNo":"IC0QFD7O","TicketId":"996583","FlightuId":"","PassuId":"451944","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":1,"Status":0},{"ChangeRequestId":null,"EticketNo":"SBV8F8TH","TicketId":"174483","FlightuId":"","PassuId":"441721","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":1,"Status":0},{"ChangeRequestId":null,"EticketNo":"TN381TRH","TicketId":"225732","FlightuId":"","PassuId":"799623","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":2,"Status":0},{"ChangeRequestId":null,"EticketNo":"T0B6NM0M","TicketId":"723626","FlightuId":"","PassuId":"325375","GivenName":"Guru","SurName":null,"NameReference":"Mr. Guru Bahrath","TripType":2,"Status":0}]
         * IsHoldAllowedRet : false
         * IsHoldAllowed : false
         * IsGSTMandatoryRet : false
         * IsGSTMandatory : false
         * RequiredFields : null
         * RequiredFieldsRet : null
         * Source : HYD
         * SourceName : HYDERABAD, India- (HYD)-Rajiv Gandhi Airport
         * Destination : BLR
         * DestinationName : BANGALORE, India- (BLR)-Bangalore International Airport
         * JourneyDate : 01-01-2020
         * ReturnDate : 03-01-2020
         * TripType : 2
         * FlightType : 1
         * AdultPax : 2
         * ChildPax : 0
         * InfantPax : 0
         * TravelClass : L
         * IsNonStopFlight : false
         * FlightTimings : null
         * AirlineName : null
         * User :
         * UserType : 5
         * IsGDS : null
         * AffiliateId : null
         * WebsiteUrl : null
         * MultiFlightsSearch : null
         * onward_fare : 2603
         * return_fare : 5710.00
         */

        private Object DeviceModel;
        private Object DeviceOS;
        private Object DeviceOSVersion;
        private Object DeviceToken;
        private int ApplicationType;
        private String TicketRefNo;
        private String LastTicketDate;
        private String FrequentFlyerDetails;
        private boolean IsBlockTicket;
        private boolean IsAgentPaymentGateway;
        private String BookingResponseXML;
        private boolean IsOfflineBooking;
        private String BookingRefNo;
        private int BookingStatus;
        private String APIRefNo;
        private String Provider;
        private Object PaymentId;
        private String Names;
        private String ages;
        private String Genders;
        private String PanNumber;
        private String telePhone;
        private String ISD;
        private String MobileNo;
        private String EmailId;
        private String dob;
        private String psgrtype;
        private String Address;
        private String State;
        private String City;
        private String PostalCode;
        private String PassportDetails;
        private int SMSUsageCount;
        private Object ImagePath;
        private Object ImagePathRet;
        private String Rule;
        private String keyRet;
        private String FlightId;
        private String FlightIdRet;
        private FareDetailsBean FareDetails;
        private String BookingDate;
        private Object PromoCode;
        private int PromoCodeAmount;
        private int PostMarkup;
        private String IsLCC;
        private String IsLCCRet;
        private Object BookedFrom;
        private int CreatedById;
        private boolean IsWallet;
        private IsPartnerAgentDetailsBean IsPartnerAgentDetails;
        private int OcTax;
        private String CurrencyID;
        private String CurrencyValue;
        private int PLBEarned;
        private int TdsOnPLB;
        private int ActualBaseFare;
        private int Tax;
        private int NetFare;
        private int Commission;
        private int Bonus;
        private int STax;
        private int SCharge;
        private int TDiscount;
        private int TPartnerCommission;
        private int TCharge;
        private int TMarkup;
        private int TSdiscount;
        private int PartnerFee;
        private String TransactionId;
        private int Conveniencefee;
        private int ConveniencefeeType;
        private int ConvenienceFeeTotal;
        private int EProductPrice;
        private int PLBEarnedRet;
        private int TdsOnPLBRet;
        private int ActualBaseFareRet;
        private int NetFareRet;
        private int BonusRet;
        private int CommissionRet;
        private int TaxRet;
        private int STaxRet;
        private int SChargeRet;
        private int TDiscountRet;
        private int TSDiscountRet;
        private int TPartnerCommissionRet;
        private int EProductPriceRet;
        private int TChargeRet;
        private int TMarkupRet;
        private int ConveniencefeeRet;
        private int PartnerFeeRet;
        private int ConveniencefeeTypeRet;
        private String TermsAndConditions;
        private String OfferInformation;
        private GSTDetailsBean GSTDetails;
        private Object SSRRequests;
        private boolean IsHoldAllowedRet;
        private boolean IsHoldAllowed;
        private boolean IsGSTMandatoryRet;
        private boolean IsGSTMandatory;
        private Object RequiredFields;
        private Object RequiredFieldsRet;
        private String Source;
        private String SourceName;
        private String Destination;
        private String DestinationName;
        private String JourneyDate;
        private String ReturnDate;
        private int TripType;
        private int FlightType;
        private int AdultPax;
        private int ChildPax;
        private int InfantPax;
        private String TravelClass;
        private boolean IsNonStopFlight;
        private Object FlightTimings;
        private Object AirlineName;
        private String User;
        private int UserType;
        private Object IsGDS;
        private Object AffiliateId;
        private Object WebsiteUrl;
        private Object MultiFlightsSearch;
        private int onward_fare;
        private int return_fare;
        private List<?> EcommerceSegment;
        private List<OnwardFlightSegmentsBean> OnwardFlightSegments;
        private List<ReturnFlightSegmentsBean> ReturnFlightSegments;
        private List<TicketsBean> Tickets;

        public Object getDeviceModel() {
            return DeviceModel;
        }

        public void setDeviceModel(Object DeviceModel) {
            this.DeviceModel = DeviceModel;
        }

        public Object getDeviceOS() {
            return DeviceOS;
        }

        public void setDeviceOS(Object DeviceOS) {
            this.DeviceOS = DeviceOS;
        }

        public Object getDeviceOSVersion() {
            return DeviceOSVersion;
        }

        public void setDeviceOSVersion(Object DeviceOSVersion) {
            this.DeviceOSVersion = DeviceOSVersion;
        }

        public Object getDeviceToken() {
            return DeviceToken;
        }

        public void setDeviceToken(Object DeviceToken) {
            this.DeviceToken = DeviceToken;
        }

        public int getApplicationType() {
            return ApplicationType;
        }

        public void setApplicationType(int ApplicationType) {
            this.ApplicationType = ApplicationType;
        }

        public String getTicketRefNo() {
            return TicketRefNo;
        }

        public void setTicketRefNo(String TicketRefNo) {
            this.TicketRefNo = TicketRefNo;
        }

        public String getLastTicketDate() {
            return LastTicketDate;
        }

        public void setLastTicketDate(String LastTicketDate) {
            this.LastTicketDate = LastTicketDate;
        }

        public String getFrequentFlyerDetails() {
            return FrequentFlyerDetails;
        }

        public void setFrequentFlyerDetails(String FrequentFlyerDetails) {
            this.FrequentFlyerDetails = FrequentFlyerDetails;
        }

        public boolean isIsBlockTicket() {
            return IsBlockTicket;
        }

        public void setIsBlockTicket(boolean IsBlockTicket) {
            this.IsBlockTicket = IsBlockTicket;
        }

        public boolean isIsAgentPaymentGateway() {
            return IsAgentPaymentGateway;
        }

        public void setIsAgentPaymentGateway(boolean IsAgentPaymentGateway) {
            this.IsAgentPaymentGateway = IsAgentPaymentGateway;
        }

        public String getBookingResponseXML() {
            return BookingResponseXML;
        }

        public void setBookingResponseXML(String BookingResponseXML) {
            this.BookingResponseXML = BookingResponseXML;
        }

        public boolean isIsOfflineBooking() {
            return IsOfflineBooking;
        }

        public void setIsOfflineBooking(boolean IsOfflineBooking) {
            this.IsOfflineBooking = IsOfflineBooking;
        }

        public String getBookingRefNo() {
            return BookingRefNo;
        }

        public void setBookingRefNo(String BookingRefNo) {
            this.BookingRefNo = BookingRefNo;
        }

        public int getBookingStatus() {
            return BookingStatus;
        }

        public void setBookingStatus(int BookingStatus) {
            this.BookingStatus = BookingStatus;
        }

        public String getAPIRefNo() {
            return APIRefNo;
        }

        public void setAPIRefNo(String APIRefNo) {
            this.APIRefNo = APIRefNo;
        }

        public String getProvider() {
            return Provider;
        }

        public void setProvider(String Provider) {
            this.Provider = Provider;
        }

        public Object getPaymentId() {
            return PaymentId;
        }

        public void setPaymentId(Object PaymentId) {
            this.PaymentId = PaymentId;
        }

        public String getNames() {
            return Names;
        }

        public void setNames(String Names) {
            this.Names = Names;
        }

        public String getAges() {
            return ages;
        }

        public void setAges(String ages) {
            this.ages = ages;
        }

        public String getGenders() {
            return Genders;
        }

        public void setGenders(String Genders) {
            this.Genders = Genders;
        }

        public String getPanNumber() {
            return PanNumber;
        }

        public void setPanNumber(String PanNumber) {
            this.PanNumber = PanNumber;
        }

        public String getTelePhone() {
            return telePhone;
        }

        public void setTelePhone(String telePhone) {
            this.telePhone = telePhone;
        }

        public String getISD() {
            return ISD;
        }

        public void setISD(String ISD) {
            this.ISD = ISD;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public String getEmailId() {
            return EmailId;
        }

        public void setEmailId(String EmailId) {
            this.EmailId = EmailId;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getPsgrtype() {
            return psgrtype;
        }

        public void setPsgrtype(String psgrtype) {
            this.psgrtype = psgrtype;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getPostalCode() {
            return PostalCode;
        }

        public void setPostalCode(String PostalCode) {
            this.PostalCode = PostalCode;
        }

        public String getPassportDetails() {
            return PassportDetails;
        }

        public void setPassportDetails(String PassportDetails) {
            this.PassportDetails = PassportDetails;
        }

        public int getSMSUsageCount() {
            return SMSUsageCount;
        }

        public void setSMSUsageCount(int SMSUsageCount) {
            this.SMSUsageCount = SMSUsageCount;
        }

        public Object getImagePath() {
            return ImagePath;
        }

        public void setImagePath(Object ImagePath) {
            this.ImagePath = ImagePath;
        }

        public Object getImagePathRet() {
            return ImagePathRet;
        }

        public void setImagePathRet(Object ImagePathRet) {
            this.ImagePathRet = ImagePathRet;
        }

        public String getRule() {
            return Rule;
        }

        public void setRule(String Rule) {
            this.Rule = Rule;
        }

        public String getKeyRet() {
            return keyRet;
        }

        public void setKeyRet(String keyRet) {
            this.keyRet = keyRet;
        }

        public String getFlightId() {
            return FlightId;
        }

        public void setFlightId(String FlightId) {
            this.FlightId = FlightId;
        }

        public String getFlightIdRet() {
            return FlightIdRet;
        }

        public void setFlightIdRet(String FlightIdRet) {
            this.FlightIdRet = FlightIdRet;
        }

        public FareDetailsBean getFareDetails() {
            return FareDetails;
        }

        public void setFareDetails(FareDetailsBean FareDetails) {
            this.FareDetails = FareDetails;
        }

        public String getBookingDate() {
            return BookingDate;
        }

        public void setBookingDate(String BookingDate) {
            this.BookingDate = BookingDate;
        }

        public Object getPromoCode() {
            return PromoCode;
        }

        public void setPromoCode(Object PromoCode) {
            this.PromoCode = PromoCode;
        }

        public int getPromoCodeAmount() {
            return PromoCodeAmount;
        }

        public void setPromoCodeAmount(int PromoCodeAmount) {
            this.PromoCodeAmount = PromoCodeAmount;
        }

        public int getPostMarkup() {
            return PostMarkup;
        }

        public void setPostMarkup(int PostMarkup) {
            this.PostMarkup = PostMarkup;
        }

        public String getIsLCC() {
            return IsLCC;
        }

        public void setIsLCC(String IsLCC) {
            this.IsLCC = IsLCC;
        }

        public String getIsLCCRet() {
            return IsLCCRet;
        }

        public void setIsLCCRet(String IsLCCRet) {
            this.IsLCCRet = IsLCCRet;
        }

        public Object getBookedFrom() {
            return BookedFrom;
        }

        public void setBookedFrom(Object BookedFrom) {
            this.BookedFrom = BookedFrom;
        }

        public int getCreatedById() {
            return CreatedById;
        }

        public void setCreatedById(int CreatedById) {
            this.CreatedById = CreatedById;
        }

        public boolean isIsWallet() {
            return IsWallet;
        }

        public void setIsWallet(boolean IsWallet) {
            this.IsWallet = IsWallet;
        }

        public IsPartnerAgentDetailsBean getIsPartnerAgentDetails() {
            return IsPartnerAgentDetails;
        }

        public void setIsPartnerAgentDetails(IsPartnerAgentDetailsBean IsPartnerAgentDetails) {
            this.IsPartnerAgentDetails = IsPartnerAgentDetails;
        }

        public int getOcTax() {
            return OcTax;
        }

        public void setOcTax(int OcTax) {
            this.OcTax = OcTax;
        }

        public String getCurrencyID() {
            return CurrencyID;
        }

        public void setCurrencyID(String CurrencyID) {
            this.CurrencyID = CurrencyID;
        }

        public String getCurrencyValue() {
            return CurrencyValue;
        }

        public void setCurrencyValue(String CurrencyValue) {
            this.CurrencyValue = CurrencyValue;
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

        public int getNetFare() {
            return NetFare;
        }

        public void setNetFare(int NetFare) {
            this.NetFare = NetFare;
        }

        public int getCommission() {
            return Commission;
        }

        public void setCommission(int Commission) {
            this.Commission = Commission;
        }

        public int getBonus() {
            return Bonus;
        }

        public void setBonus(int Bonus) {
            this.Bonus = Bonus;
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

        public int getPartnerFee() {
            return PartnerFee;
        }

        public void setPartnerFee(int PartnerFee) {
            this.PartnerFee = PartnerFee;
        }

        public String getTransactionId() {
            return TransactionId;
        }

        public void setTransactionId(String TransactionId) {
            this.TransactionId = TransactionId;
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

        public int getConvenienceFeeTotal() {
            return ConvenienceFeeTotal;
        }

        public void setConvenienceFeeTotal(int ConvenienceFeeTotal) {
            this.ConvenienceFeeTotal = ConvenienceFeeTotal;
        }

        public int getEProductPrice() {
            return EProductPrice;
        }

        public void setEProductPrice(int EProductPrice) {
            this.EProductPrice = EProductPrice;
        }

        public int getPLBEarnedRet() {
            return PLBEarnedRet;
        }

        public void setPLBEarnedRet(int PLBEarnedRet) {
            this.PLBEarnedRet = PLBEarnedRet;
        }

        public int getTdsOnPLBRet() {
            return TdsOnPLBRet;
        }

        public void setTdsOnPLBRet(int TdsOnPLBRet) {
            this.TdsOnPLBRet = TdsOnPLBRet;
        }

        public int getActualBaseFareRet() {
            return ActualBaseFareRet;
        }

        public void setActualBaseFareRet(int ActualBaseFareRet) {
            this.ActualBaseFareRet = ActualBaseFareRet;
        }

        public int getNetFareRet() {
            return NetFareRet;
        }

        public void setNetFareRet(int NetFareRet) {
            this.NetFareRet = NetFareRet;
        }

        public int getBonusRet() {
            return BonusRet;
        }

        public void setBonusRet(int BonusRet) {
            this.BonusRet = BonusRet;
        }

        public int getCommissionRet() {
            return CommissionRet;
        }

        public void setCommissionRet(int CommissionRet) {
            this.CommissionRet = CommissionRet;
        }

        public int getTaxRet() {
            return TaxRet;
        }

        public void setTaxRet(int TaxRet) {
            this.TaxRet = TaxRet;
        }

        public int getSTaxRet() {
            return STaxRet;
        }

        public void setSTaxRet(int STaxRet) {
            this.STaxRet = STaxRet;
        }

        public int getSChargeRet() {
            return SChargeRet;
        }

        public void setSChargeRet(int SChargeRet) {
            this.SChargeRet = SChargeRet;
        }

        public int getTDiscountRet() {
            return TDiscountRet;
        }

        public void setTDiscountRet(int TDiscountRet) {
            this.TDiscountRet = TDiscountRet;
        }

        public int getTSDiscountRet() {
            return TSDiscountRet;
        }

        public void setTSDiscountRet(int TSDiscountRet) {
            this.TSDiscountRet = TSDiscountRet;
        }

        public int getTPartnerCommissionRet() {
            return TPartnerCommissionRet;
        }

        public void setTPartnerCommissionRet(int TPartnerCommissionRet) {
            this.TPartnerCommissionRet = TPartnerCommissionRet;
        }

        public int getEProductPriceRet() {
            return EProductPriceRet;
        }

        public void setEProductPriceRet(int EProductPriceRet) {
            this.EProductPriceRet = EProductPriceRet;
        }

        public int getTChargeRet() {
            return TChargeRet;
        }

        public void setTChargeRet(int TChargeRet) {
            this.TChargeRet = TChargeRet;
        }

        public int getTMarkupRet() {
            return TMarkupRet;
        }

        public void setTMarkupRet(int TMarkupRet) {
            this.TMarkupRet = TMarkupRet;
        }

        public int getConveniencefeeRet() {
            return ConveniencefeeRet;
        }

        public void setConveniencefeeRet(int ConveniencefeeRet) {
            this.ConveniencefeeRet = ConveniencefeeRet;
        }

        public int getPartnerFeeRet() {
            return PartnerFeeRet;
        }

        public void setPartnerFeeRet(int PartnerFeeRet) {
            this.PartnerFeeRet = PartnerFeeRet;
        }

        public int getConveniencefeeTypeRet() {
            return ConveniencefeeTypeRet;
        }

        public void setConveniencefeeTypeRet(int ConveniencefeeTypeRet) {
            this.ConveniencefeeTypeRet = ConveniencefeeTypeRet;
        }

        public String getTermsAndConditions() {
            return TermsAndConditions;
        }

        public void setTermsAndConditions(String TermsAndConditions) {
            this.TermsAndConditions = TermsAndConditions;
        }

        public String getOfferInformation() {
            return OfferInformation;
        }

        public void setOfferInformation(String OfferInformation) {
            this.OfferInformation = OfferInformation;
        }

        public GSTDetailsBean getGSTDetails() {
            return GSTDetails;
        }

        public void setGSTDetails(GSTDetailsBean GSTDetails) {
            this.GSTDetails = GSTDetails;
        }

        public Object getSSRRequests() {
            return SSRRequests;
        }

        public void setSSRRequests(Object SSRRequests) {
            this.SSRRequests = SSRRequests;
        }

        public boolean isIsHoldAllowedRet() {
            return IsHoldAllowedRet;
        }

        public void setIsHoldAllowedRet(boolean IsHoldAllowedRet) {
            this.IsHoldAllowedRet = IsHoldAllowedRet;
        }

        public boolean isIsHoldAllowed() {
            return IsHoldAllowed;
        }

        public void setIsHoldAllowed(boolean IsHoldAllowed) {
            this.IsHoldAllowed = IsHoldAllowed;
        }

        public boolean isIsGSTMandatoryRet() {
            return IsGSTMandatoryRet;
        }

        public void setIsGSTMandatoryRet(boolean IsGSTMandatoryRet) {
            this.IsGSTMandatoryRet = IsGSTMandatoryRet;
        }

        public boolean isIsGSTMandatory() {
            return IsGSTMandatory;
        }

        public void setIsGSTMandatory(boolean IsGSTMandatory) {
            this.IsGSTMandatory = IsGSTMandatory;
        }

        public Object getRequiredFields() {
            return RequiredFields;
        }

        public void setRequiredFields(Object RequiredFields) {
            this.RequiredFields = RequiredFields;
        }

        public Object getRequiredFieldsRet() {
            return RequiredFieldsRet;
        }

        public void setRequiredFieldsRet(Object RequiredFieldsRet) {
            this.RequiredFieldsRet = RequiredFieldsRet;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getSourceName() {
            return SourceName;
        }

        public void setSourceName(String SourceName) {
            this.SourceName = SourceName;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String Destination) {
            this.Destination = Destination;
        }

        public String getDestinationName() {
            return DestinationName;
        }

        public void setDestinationName(String DestinationName) {
            this.DestinationName = DestinationName;
        }

        public String getJourneyDate() {
            return JourneyDate;
        }

        public void setJourneyDate(String JourneyDate) {
            this.JourneyDate = JourneyDate;
        }

        public String getReturnDate() {
            return ReturnDate;
        }

        public void setReturnDate(String ReturnDate) {
            this.ReturnDate = ReturnDate;
        }

        public int getTripType() {
            return TripType;
        }

        public void setTripType(int TripType) {
            this.TripType = TripType;
        }

        public int getFlightType() {
            return FlightType;
        }

        public void setFlightType(int FlightType) {
            this.FlightType = FlightType;
        }

        public int getAdultPax() {
            return AdultPax;
        }

        public void setAdultPax(int AdultPax) {
            this.AdultPax = AdultPax;
        }

        public int getChildPax() {
            return ChildPax;
        }

        public void setChildPax(int ChildPax) {
            this.ChildPax = ChildPax;
        }

        public int getInfantPax() {
            return InfantPax;
        }

        public void setInfantPax(int InfantPax) {
            this.InfantPax = InfantPax;
        }

        public String getTravelClass() {
            return TravelClass;
        }

        public void setTravelClass(String TravelClass) {
            this.TravelClass = TravelClass;
        }

        public boolean isIsNonStopFlight() {
            return IsNonStopFlight;
        }

        public void setIsNonStopFlight(boolean IsNonStopFlight) {
            this.IsNonStopFlight = IsNonStopFlight;
        }

        public Object getFlightTimings() {
            return FlightTimings;
        }

        public void setFlightTimings(Object FlightTimings) {
            this.FlightTimings = FlightTimings;
        }

        public Object getAirlineName() {
            return AirlineName;
        }

        public void setAirlineName(Object AirlineName) {
            this.AirlineName = AirlineName;
        }

        public String getUser() {
            return User;
        }

        public void setUser(String User) {
            this.User = User;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public Object getIsGDS() {
            return IsGDS;
        }

        public void setIsGDS(Object IsGDS) {
            this.IsGDS = IsGDS;
        }

        public Object getAffiliateId() {
            return AffiliateId;
        }

        public void setAffiliateId(Object AffiliateId) {
            this.AffiliateId = AffiliateId;
        }

        public Object getWebsiteUrl() {
            return WebsiteUrl;
        }

        public void setWebsiteUrl(Object WebsiteUrl) {
            this.WebsiteUrl = WebsiteUrl;
        }

        public Object getMultiFlightsSearch() {
            return MultiFlightsSearch;
        }

        public void setMultiFlightsSearch(Object MultiFlightsSearch) {
            this.MultiFlightsSearch = MultiFlightsSearch;
        }

        public int getOnward_fare() {
            return onward_fare;
        }

        public void setOnward_fare(int onward_fare) {
            this.onward_fare = onward_fare;
        }

        public int getReturn_fare() {
            return return_fare;
        }

        public void setReturn_fare(int return_fare) {
            this.return_fare = return_fare;
        }

        public List<?> getEcommerceSegment() {
            return EcommerceSegment;
        }

        public void setEcommerceSegment(List<?> EcommerceSegment) {
            this.EcommerceSegment = EcommerceSegment;
        }

        public List<OnwardFlightSegmentsBean> getOnwardFlightSegments() {
            return OnwardFlightSegments;
        }

        public void setOnwardFlightSegments(List<OnwardFlightSegmentsBean> OnwardFlightSegments) {
            this.OnwardFlightSegments = OnwardFlightSegments;
        }

        public List<ReturnFlightSegmentsBean> getReturnFlightSegments() {
            return ReturnFlightSegments;
        }

        public void setReturnFlightSegments(List<ReturnFlightSegmentsBean> ReturnFlightSegments) {
            this.ReturnFlightSegments = ReturnFlightSegments;
        }

        public List<TicketsBean> getTickets() {
            return Tickets;
        }

        public void setTickets(List<TicketsBean> Tickets) {
            this.Tickets = Tickets;
        }

        public static class FareDetailsBean {
            /**
             * ChargeableFares : {"ActualBaseFare":0,"Tax":0,"STax":0,"SCharge":0,"TDiscount":0,"TPartnerCommission":0,"NetFare":0,"Conveniencefee":0,"ConveniencefeeType":0,"PartnerFareDatails":null}
             * NonchargeableFares : {"TCharge":0,"TMarkup":0,"TSdiscount":0}
             * FareBreakUp : {"FareAry":[]}
             * OCTax : 0
             * PartnerFee : 0
             * PLBEarned : 0
             * TdsOnPLB : 0
             * Bonus : 0
             * TotalFare : 0
             * ResponseStatus : 0
             * Status : 0
             * IsGSTMandatory : false
             * Message : null
             * RequiredFields : null
             */

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
                /**
                 * ActualBaseFare : 0
                 * Tax : 0
                 * STax : 0
                 * SCharge : 0
                 * TDiscount : 0
                 * TPartnerCommission : 0
                 * NetFare : 0
                 * Conveniencefee : 0
                 * ConveniencefeeType : 0
                 * PartnerFareDatails : null
                 */

                private int ActualBaseFare;
                private int Tax;
                private int STax;
                private int SCharge;
                private int TDiscount;
                private int TPartnerCommission;
                private int NetFare;
                private int Conveniencefee;
                private int ConveniencefeeType;
                private Object PartnerFareDatails;

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

                public Object getPartnerFareDatails() {
                    return PartnerFareDatails;
                }

                public void setPartnerFareDatails(Object PartnerFareDatails) {
                    this.PartnerFareDatails = PartnerFareDatails;
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
                private List<?> FareAry;

                public List<?> getFareAry() {
                    return FareAry;
                }

                public void setFareAry(List<?> FareAry) {
                    this.FareAry = FareAry;
                }
            }
        }

        public static class IsPartnerAgentDetailsBean {
            /**
             * AgentId :
             * ServiceId : null
             * ServiceName : null
             * Amount : null
             * UserCharges : null
             * ResCode : null
             * ResDesc : null
             * Requestid :
             * AgentFee : 0
             */

            private String AgentId;
            private Object ServiceId;
            private Object ServiceName;
            private Object Amount;
            private Object UserCharges;
            private Object ResCode;
            private Object ResDesc;
            private String Requestid;
            private int AgentFee;

            public String getAgentId() {
                return AgentId;
            }

            public void setAgentId(String AgentId) {
                this.AgentId = AgentId;
            }

            public Object getServiceId() {
                return ServiceId;
            }

            public void setServiceId(Object ServiceId) {
                this.ServiceId = ServiceId;
            }

            public Object getServiceName() {
                return ServiceName;
            }

            public void setServiceName(Object ServiceName) {
                this.ServiceName = ServiceName;
            }

            public Object getAmount() {
                return Amount;
            }

            public void setAmount(Object Amount) {
                this.Amount = Amount;
            }

            public Object getUserCharges() {
                return UserCharges;
            }

            public void setUserCharges(Object UserCharges) {
                this.UserCharges = UserCharges;
            }

            public Object getResCode() {
                return ResCode;
            }

            public void setResCode(Object ResCode) {
                this.ResCode = ResCode;
            }

            public Object getResDesc() {
                return ResDesc;
            }

            public void setResDesc(Object ResDesc) {
                this.ResDesc = ResDesc;
            }

            public String getRequestid() {
                return Requestid;
            }

            public void setRequestid(String Requestid) {
                this.Requestid = Requestid;
            }

            public int getAgentFee() {
                return AgentFee;
            }

            public void setAgentFee(int AgentFee) {
                this.AgentFee = AgentFee;
            }
        }

        public static class GSTDetailsBean {
            /**
             * GSTCompanyAddress :
             * GSTCompanyContactNumber :
             * GSTCompanyName :
             * GSTNumber :
             * GSTCompanyEmail :
             * GSTFirstName : null
             * GSTLastName : null
             */

            private String GSTCompanyAddress;
            private String GSTCompanyContactNumber;
            private String GSTCompanyName;
            private String GSTNumber;
            private String GSTCompanyEmail;
            private Object GSTFirstName;
            private Object GSTLastName;

            public String getGSTCompanyAddress() {
                return GSTCompanyAddress;
            }

            public void setGSTCompanyAddress(String GSTCompanyAddress) {
                this.GSTCompanyAddress = GSTCompanyAddress;
            }

            public String getGSTCompanyContactNumber() {
                return GSTCompanyContactNumber;
            }

            public void setGSTCompanyContactNumber(String GSTCompanyContactNumber) {
                this.GSTCompanyContactNumber = GSTCompanyContactNumber;
            }

            public String getGSTCompanyName() {
                return GSTCompanyName;
            }

            public void setGSTCompanyName(String GSTCompanyName) {
                this.GSTCompanyName = GSTCompanyName;
            }

            public String getGSTNumber() {
                return GSTNumber;
            }

            public void setGSTNumber(String GSTNumber) {
                this.GSTNumber = GSTNumber;
            }

            public String getGSTCompanyEmail() {
                return GSTCompanyEmail;
            }

            public void setGSTCompanyEmail(String GSTCompanyEmail) {
                this.GSTCompanyEmail = GSTCompanyEmail;
            }

            public Object getGSTFirstName() {
                return GSTFirstName;
            }

            public void setGSTFirstName(Object GSTFirstName) {
                this.GSTFirstName = GSTFirstName;
            }

            public Object getGSTLastName() {
                return GSTLastName;
            }

            public void setGSTLastName(Object GSTLastName) {
                this.GSTLastName = GSTLastName;
            }
        }

        public static class OnwardFlightSegmentsBean {
            /**
             * AirEquipType : 320
             * ArrivalAirportCode : BLR
             * ArrivalDateTime : 2020-01-01T09:50:00
             * ArrivalDateTimeZone : null
             * DepartureAirportCode : HYD
             * DepartureDateTime : 2020-01-01T08:25:00
             * DepartureDateTimeZone : null
             * Duration : 01:25 hrs
             * FlightNumber : 151
             * OperatingAirlineCode : 6E
             * OperatingAirlineFlightNumber : 151
             * RPH :
             * StopQuantity : 0
             * AirLineName : Indigo
             * AirportTax :
             * ImageFileName : 6E
             * ImagePath : null
             * ViaFlight :
             * Discount : 0
             * AirportTaxChild : 0
             * AirportTaxInfant : 0
             * AdultTaxBreakup : 0
             * ChildTaxBreakup : 0
             * InfantTaxBreakup : 0
             * OcTax : 0
             * BookingClass : {"Availability":"9","ResBookDesigCode":"L","IntBIC":""}
             * BookingClassFare : {"AdultFare":"","Bookingclass":"","ClassType":"L","Farebasiscode":"L0IP","Rule":"Refundable","AdultCommission":"","ChildCommission":"","CommissionOnTCharge":"","ChildFare":"","InfantFare":""}
             * IntNumStops :
             * IntOperatingAirlineName :
             * IntArrivalAirportName : Bangalore
             * IntDepartureAirportName : Hyderabad
             * IntMarketingAirlineCode : 151
             * IntLinkSellAgrmnt :
             * IntConx :
             * IntAirpChg :
             * IntInsideAvailOption :
             * IntGenTrafRestriction :
             * IntDaysOperates :
             * IntJourneyTime :
             * IntEndDate :
             * IntStartTerminal :
             * IntEndTerminal : 1
             * IntFltTm :
             * IntLSAInd :
             * IntMile : 0
             * Cabin : null
             * itineraryNumber : 637095282517603061-217-1499_ET0
             * BaggageAllowed : {"CheckInBaggage":"15 KG","HandBaggage":"7 KG"}
             * PaxWiseBaggageAllowed : []
             * AccumulatedDuration : 0
             * GroundTime : 00:00 hrs
             */

            private String AirEquipType;
            private String ArrivalAirportCode;
            private String ArrivalDateTime;
            private Object ArrivalDateTimeZone;
            private String DepartureAirportCode;
            private String DepartureDateTime;
            private Object DepartureDateTimeZone;
            private String Duration;
            private String FlightNumber;
            private String OperatingAirlineCode;
            private String OperatingAirlineFlightNumber;
            private String RPH;
            private String StopQuantity;
            private String AirLineName;
            private String AirportTax;
            private String ImageFileName;
            private Object ImagePath;
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
            private String IntNumStops;
            private String IntOperatingAirlineName;
            private String IntArrivalAirportName;
            private String IntDepartureAirportName;
            private String IntMarketingAirlineCode;
            private String IntLinkSellAgrmnt;
            private String IntConx;
            private String IntAirpChg;
            private String IntInsideAvailOption;
            private String IntGenTrafRestriction;
            private String IntDaysOperates;
            private String IntJourneyTime;
            private String IntEndDate;
            private String IntStartTerminal;
            private String IntEndTerminal;
            private String IntFltTm;
            private String IntLSAInd;
            private String IntMile;
            private Object Cabin;
            private String itineraryNumber;
            private BaggageAllowedBean BaggageAllowed;
            private String AccumulatedDuration;
            private String GroundTime;
            private List<?> PaxWiseBaggageAllowed;

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

            public Object getArrivalDateTimeZone() {
                return ArrivalDateTimeZone;
            }

            public void setArrivalDateTimeZone(Object ArrivalDateTimeZone) {
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

            public Object getDepartureDateTimeZone() {
                return DepartureDateTimeZone;
            }

            public void setDepartureDateTimeZone(Object DepartureDateTimeZone) {
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

            public String getAirportTax() {
                return AirportTax;
            }

            public void setAirportTax(String AirportTax) {
                this.AirportTax = AirportTax;
            }

            public String getImageFileName() {
                return ImageFileName;
            }

            public void setImageFileName(String ImageFileName) {
                this.ImageFileName = ImageFileName;
            }

            public Object getImagePath() {
                return ImagePath;
            }

            public void setImagePath(Object ImagePath) {
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

            public String getIntNumStops() {
                return IntNumStops;
            }

            public void setIntNumStops(String IntNumStops) {
                this.IntNumStops = IntNumStops;
            }

            public String getIntOperatingAirlineName() {
                return IntOperatingAirlineName;
            }

            public void setIntOperatingAirlineName(String IntOperatingAirlineName) {
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

            public String getIntLinkSellAgrmnt() {
                return IntLinkSellAgrmnt;
            }

            public void setIntLinkSellAgrmnt(String IntLinkSellAgrmnt) {
                this.IntLinkSellAgrmnt = IntLinkSellAgrmnt;
            }

            public String getIntConx() {
                return IntConx;
            }

            public void setIntConx(String IntConx) {
                this.IntConx = IntConx;
            }

            public String getIntAirpChg() {
                return IntAirpChg;
            }

            public void setIntAirpChg(String IntAirpChg) {
                this.IntAirpChg = IntAirpChg;
            }

            public String getIntInsideAvailOption() {
                return IntInsideAvailOption;
            }

            public void setIntInsideAvailOption(String IntInsideAvailOption) {
                this.IntInsideAvailOption = IntInsideAvailOption;
            }

            public String getIntGenTrafRestriction() {
                return IntGenTrafRestriction;
            }

            public void setIntGenTrafRestriction(String IntGenTrafRestriction) {
                this.IntGenTrafRestriction = IntGenTrafRestriction;
            }

            public String getIntDaysOperates() {
                return IntDaysOperates;
            }

            public void setIntDaysOperates(String IntDaysOperates) {
                this.IntDaysOperates = IntDaysOperates;
            }

            public String getIntJourneyTime() {
                return IntJourneyTime;
            }

            public void setIntJourneyTime(String IntJourneyTime) {
                this.IntJourneyTime = IntJourneyTime;
            }

            public String getIntEndDate() {
                return IntEndDate;
            }

            public void setIntEndDate(String IntEndDate) {
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

            public String getIntFltTm() {
                return IntFltTm;
            }

            public void setIntFltTm(String IntFltTm) {
                this.IntFltTm = IntFltTm;
            }

            public String getIntLSAInd() {
                return IntLSAInd;
            }

            public void setIntLSAInd(String IntLSAInd) {
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

            public List<?> getPaxWiseBaggageAllowed() {
                return PaxWiseBaggageAllowed;
            }

            public void setPaxWiseBaggageAllowed(List<?> PaxWiseBaggageAllowed) {
                this.PaxWiseBaggageAllowed = PaxWiseBaggageAllowed;
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
                 * AdultCommission :
                 * ChildCommission :
                 * CommissionOnTCharge :
                 * ChildFare :
                 * InfantFare :
                 */

                private String AdultFare;
                private String Bookingclass;
                private String ClassType;
                private String Farebasiscode;
                private String Rule;
                private String AdultCommission;
                private String ChildCommission;
                private String CommissionOnTCharge;
                private String ChildFare;
                private String InfantFare;

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

                public String getAdultCommission() {
                    return AdultCommission;
                }

                public void setAdultCommission(String AdultCommission) {
                    this.AdultCommission = AdultCommission;
                }

                public String getChildCommission() {
                    return ChildCommission;
                }

                public void setChildCommission(String ChildCommission) {
                    this.ChildCommission = ChildCommission;
                }

                public String getCommissionOnTCharge() {
                    return CommissionOnTCharge;
                }

                public void setCommissionOnTCharge(String CommissionOnTCharge) {
                    this.CommissionOnTCharge = CommissionOnTCharge;
                }

                public String getChildFare() {
                    return ChildFare;
                }

                public void setChildFare(String ChildFare) {
                    this.ChildFare = ChildFare;
                }

                public String getInfantFare() {
                    return InfantFare;
                }

                public void setInfantFare(String InfantFare) {
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

        public static class ReturnFlightSegmentsBean {
            /**
             * AirEquipType : 320
             * ArrivalAirportCode : HYD
             * ArrivalDateTime : 2020-01-03T20:00:00
             * ArrivalDateTimeZone : null
             * DepartureAirportCode : BLR
             * DepartureDateTime : 2020-01-03T18:45:00
             * DepartureDateTimeZone : null
             * Duration : 01:15 hrs
             * FlightNumber : 855
             * OperatingAirlineCode : 6E
             * OperatingAirlineFlightNumber : 855
             * RPH :
             * StopQuantity : 0
             * AirLineName : Indigo
             * AirportTax :
             * ImageFileName : 6E
             * ImagePath : null
             * ViaFlight :
             * Discount : 0
             * AirportTaxChild : 0
             * AirportTaxInfant : 0
             * AdultTaxBreakup : 0
             * ChildTaxBreakup : 0
             * InfantTaxBreakup : 0
             * OcTax : 0
             * BookingClass : {"Availability":"9","ResBookDesigCode":"L","IntBIC":""}
             * BookingClassFare : {"AdultFare":"","Bookingclass":"","ClassType":"L","Farebasiscode":"L0IP","Rule":"Refundable","AdultCommission":"","ChildCommission":"","CommissionOnTCharge":"","ChildFare":"","InfantFare":""}
             * IntNumStops :
             * IntOperatingAirlineName :
             * IntArrivalAirportName : Hyderabad
             * IntDepartureAirportName : Bangalore
             * IntMarketingAirlineCode : 855
             * IntLinkSellAgrmnt :
             * IntConx :
             * IntAirpChg :
             * IntInsideAvailOption :
             * IntGenTrafRestriction :
             * IntDaysOperates :
             * IntJourneyTime :
             * IntEndDate :
             * IntStartTerminal : 1
             * IntEndTerminal :
             * IntFltTm :
             * IntLSAInd :
             * IntMile : 0
             * Cabin : null
             * itineraryNumber : 637095285310831870-217-1499_ETR0
             * BaggageAllowed : {"CheckInBaggage":"15 KG","HandBaggage":"7 KG"}
             * PaxWiseBaggageAllowed : null
             * AccumulatedDuration : 0
             * GroundTime : 00:00 hrs
             */

            private String AirEquipType;
            private String ArrivalAirportCode;
            private String ArrivalDateTime;
            private Object ArrivalDateTimeZone;
            private String DepartureAirportCode;
            private String DepartureDateTime;
            private Object DepartureDateTimeZone;
            private String Duration;
            private String FlightNumber;
            private String OperatingAirlineCode;
            private String OperatingAirlineFlightNumber;
            private String RPH;
            private String StopQuantity;
            private String AirLineName;
            private String AirportTax;
            private String ImageFileName;
            private Object ImagePath;
            private String ViaFlight;
            private String Discount;
            private String AirportTaxChild;
            private String AirportTaxInfant;
            private String AdultTaxBreakup;
            private String ChildTaxBreakup;
            private String InfantTaxBreakup;
            private String OcTax;
            private BookingClassBeanX BookingClass;
            private BookingClassFareBeanX BookingClassFare;
            private String IntNumStops;
            private String IntOperatingAirlineName;
            private String IntArrivalAirportName;
            private String IntDepartureAirportName;
            private String IntMarketingAirlineCode;
            private String IntLinkSellAgrmnt;
            private String IntConx;
            private String IntAirpChg;
            private String IntInsideAvailOption;
            private String IntGenTrafRestriction;
            private String IntDaysOperates;
            private String IntJourneyTime;
            private String IntEndDate;
            private String IntStartTerminal;
            private String IntEndTerminal;
            private String IntFltTm;
            private String IntLSAInd;
            private String IntMile;
            private Object Cabin;
            private String itineraryNumber;
            private BaggageAllowedBeanX BaggageAllowed;
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

            public Object getArrivalDateTimeZone() {
                return ArrivalDateTimeZone;
            }

            public void setArrivalDateTimeZone(Object ArrivalDateTimeZone) {
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

            public Object getDepartureDateTimeZone() {
                return DepartureDateTimeZone;
            }

            public void setDepartureDateTimeZone(Object DepartureDateTimeZone) {
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

            public String getAirportTax() {
                return AirportTax;
            }

            public void setAirportTax(String AirportTax) {
                this.AirportTax = AirportTax;
            }

            public String getImageFileName() {
                return ImageFileName;
            }

            public void setImageFileName(String ImageFileName) {
                this.ImageFileName = ImageFileName;
            }

            public Object getImagePath() {
                return ImagePath;
            }

            public void setImagePath(Object ImagePath) {
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

            public BookingClassBeanX getBookingClass() {
                return BookingClass;
            }

            public void setBookingClass(BookingClassBeanX BookingClass) {
                this.BookingClass = BookingClass;
            }

            public BookingClassFareBeanX getBookingClassFare() {
                return BookingClassFare;
            }

            public void setBookingClassFare(BookingClassFareBeanX BookingClassFare) {
                this.BookingClassFare = BookingClassFare;
            }

            public String getIntNumStops() {
                return IntNumStops;
            }

            public void setIntNumStops(String IntNumStops) {
                this.IntNumStops = IntNumStops;
            }

            public String getIntOperatingAirlineName() {
                return IntOperatingAirlineName;
            }

            public void setIntOperatingAirlineName(String IntOperatingAirlineName) {
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

            public String getIntLinkSellAgrmnt() {
                return IntLinkSellAgrmnt;
            }

            public void setIntLinkSellAgrmnt(String IntLinkSellAgrmnt) {
                this.IntLinkSellAgrmnt = IntLinkSellAgrmnt;
            }

            public String getIntConx() {
                return IntConx;
            }

            public void setIntConx(String IntConx) {
                this.IntConx = IntConx;
            }

            public String getIntAirpChg() {
                return IntAirpChg;
            }

            public void setIntAirpChg(String IntAirpChg) {
                this.IntAirpChg = IntAirpChg;
            }

            public String getIntInsideAvailOption() {
                return IntInsideAvailOption;
            }

            public void setIntInsideAvailOption(String IntInsideAvailOption) {
                this.IntInsideAvailOption = IntInsideAvailOption;
            }

            public String getIntGenTrafRestriction() {
                return IntGenTrafRestriction;
            }

            public void setIntGenTrafRestriction(String IntGenTrafRestriction) {
                this.IntGenTrafRestriction = IntGenTrafRestriction;
            }

            public String getIntDaysOperates() {
                return IntDaysOperates;
            }

            public void setIntDaysOperates(String IntDaysOperates) {
                this.IntDaysOperates = IntDaysOperates;
            }

            public String getIntJourneyTime() {
                return IntJourneyTime;
            }

            public void setIntJourneyTime(String IntJourneyTime) {
                this.IntJourneyTime = IntJourneyTime;
            }

            public String getIntEndDate() {
                return IntEndDate;
            }

            public void setIntEndDate(String IntEndDate) {
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

            public String getIntFltTm() {
                return IntFltTm;
            }

            public void setIntFltTm(String IntFltTm) {
                this.IntFltTm = IntFltTm;
            }

            public String getIntLSAInd() {
                return IntLSAInd;
            }

            public void setIntLSAInd(String IntLSAInd) {
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

            public BaggageAllowedBeanX getBaggageAllowed() {
                return BaggageAllowed;
            }

            public void setBaggageAllowed(BaggageAllowedBeanX BaggageAllowed) {
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

            public static class BookingClassBeanX {
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

            public static class BookingClassFareBeanX {
                /**
                 * AdultFare :
                 * Bookingclass :
                 * ClassType : L
                 * Farebasiscode : L0IP
                 * Rule : Refundable
                 * AdultCommission :
                 * ChildCommission :
                 * CommissionOnTCharge :
                 * ChildFare :
                 * InfantFare :
                 */

                private String AdultFare;
                private String Bookingclass;
                private String ClassType;
                private String Farebasiscode;
                private String Rule;
                private String AdultCommission;
                private String ChildCommission;
                private String CommissionOnTCharge;
                private String ChildFare;
                private String InfantFare;

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

                public String getAdultCommission() {
                    return AdultCommission;
                }

                public void setAdultCommission(String AdultCommission) {
                    this.AdultCommission = AdultCommission;
                }

                public String getChildCommission() {
                    return ChildCommission;
                }

                public void setChildCommission(String ChildCommission) {
                    this.ChildCommission = ChildCommission;
                }

                public String getCommissionOnTCharge() {
                    return CommissionOnTCharge;
                }

                public void setCommissionOnTCharge(String CommissionOnTCharge) {
                    this.CommissionOnTCharge = CommissionOnTCharge;
                }

                public String getChildFare() {
                    return ChildFare;
                }

                public void setChildFare(String ChildFare) {
                    this.ChildFare = ChildFare;
                }

                public String getInfantFare() {
                    return InfantFare;
                }

                public void setInfantFare(String InfantFare) {
                    this.InfantFare = InfantFare;
                }
            }

            public static class BaggageAllowedBeanX {
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

        public static class TicketsBean {
            /**
             * ChangeRequestId : null
             * EticketNo : IC0QFD7O
             * TicketId : 996583
             * FlightuId :
             * PassuId : 451944
             * GivenName : Guru
             * SurName : null
             * NameReference : Mr. Guru Bahrath
             * TripType : 1
             * Status : 0
             */

            private Object ChangeRequestId;
            private String EticketNo;
            private String TicketId;
            private String FlightuId;
            private String PassuId;
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
