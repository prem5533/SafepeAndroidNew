package com.safepayu.wallet.models.response.booking.hotel;

import java.util.List;

public class AvailableHotelsResponse {

    /**
     * status : true
     * mesage : Data retrieved successfully
     * data : {"AvailableHotels":[{"HotelId":"H!0758554","HotelName":"Aditya Inn Gachibowli II","CategoryList":null,
     * "PropertyType":null,"IsPrime":false,"Description":"Property Location When you stay at Hotel Aditya Homes in Hyderabad,
     * youll be within a 10-minute drive of G.M.C. Balayogi Athletic Stadium and Gachibowli Indoor Stadium.  This hotel is 2.1 mi
     * (3.4 km) from Kotla Vijayabhaskara Reddy Botanical Garden and 2.8 mi (4.5 km) from Sohrabji Godrej Green Business
     * Centre.Rooms Make yourself at home in one of the 80 air-conditioned rooms featuring flat-screen televisions.
     * Complimentary wireless Internet access keeps you connected, and cable programming is available for your entertainment.
     * Private bathrooms with showers feature rainfall showerheads and complimentary toiletries.Amenities This hotel offers
     * designated smoking areas.Dining A complimentary continental breakfast is included.Business, Other Amenities Featured
     * amenities include a 24-hour front desk and laundry facilities.","HotelChain":"","StarRating":"3","Rooms":0,"MinRate":0,
     * "RPH":"C!020801","WebService":"vrl7vl2rbacmxak4jmphlvnx6u","HotelAddress":"Flat 69,70,71 Gachibowli,
     * Behind Domino's Pizza and ICICI Bank, Vinayak Nagar, Hyderabad 500032","PostalCode":"","City":"C!020801",
     * "LocationInfo":null,"PhoneNumber":"","Fax":"","Email":"","Website":"","Checkintime":null,"Checkouttime":null,
     * "CreditCards":null,"HotelServices":null,"RoomServices":null,"Facilities":"Air conditioning , Housekeeping - daily ,
     * Laundry/Valet service , 24-hour front desk , Complimentary wireless internet","CountryCode":"IN","AirportCode":null,
     * "SupplierType":"","PropertyCategory":null,"Provider":"Z+HjKTsZveEfDC1+wXUA4A==",
     * "RoomDetails":[{"Soldout":false,"RoomIndex":"1","RateType":"","HotelPackage":null,
     * "RoomType":"Classic Double or Twin Room - India Citizens Only","RoomBasis":"","RoomTypeCode":"wcmqxydh5arcrwsvu2nwmfi",
     * "RatePlanCode":"4dgfbkrr4ircpusquocweho45ljldqni7g76xw32yvgbq","Validdays":null,
     * "WsKey":"5hhvhkrt5arfjjbtv2irkh5e5lmk5s5c6k76pvd53zcrv7hdcpix5ahse4hctyxb6u","ExtGuestTotal":0,
     * "RoomTotal":1017.82,"RoomNetTotal":1017.82,"PartnerFareDatails":{"NetFares":"1017.82","Commission":"0",
     * "CommissionType":0},"ServicetaxTotal":36.18,"Discount":0,"Commission":0,"expediaPropertyId":null,
     * "RoomCancellationPolicy":"Last date for cancellation without charges: 25 Mar 2020 23:59:59. 100% will be charged,
     * if cancelled after 27 Mar 2020 00:00:00","NoOfPax":0,"RefundRule":"Refundable Fare","Inclusions":"","Exclusions":null,
     * "RoomCount":0,"MaxAdultOccupancy":0,"MaxChildOccupancy":0,"BedType":"","RoomView":null,"RoomDescription":"",
     * "RoomAmenities":"","IncExcCondition":null,"IsInclusion":false,"Images":null},{"Soldout":false,"RoomIndex":"2",
     * "RateType":"","HotelPackage":null,"RoomType":"Classic Double or Twin Room - India Citizens Only","RoomBasis":"",
     * "RoomTypeCode":"wcmqxydh5arcrwsvu2nwmfi","RatePlanCode":"4dgvbkrw5mrcpuswvwcwohw45ljldqni7g76vvt4yvgry","Validdays":null,
     * "WsKey":"5hev3krw4ercpizpy2iw236uslmklqfc7g36pwd63zart7xkdpjh3bxweaesvyxb6vlq","ExtGuestTotal":0,"RoomTotal":1079.11,
     * "RoomNetTotal":1079.11,"PartnerFareDatails":{"NetFares":"1079.11","Commission":"0","CommissionType":0},
     * "ServicetaxTotal":38.89,"Discount":0,"Commission":0,"expediaPropertyId":null,"RoomCancellationPolicy":"Last date for
     * cancellation without charges: 25 Mar 2020 23:59:59. 100% will be charged, if cancelled after 27 Mar 2020 00:00:00",
     * "NoOfPax":0,"RefundRule":"Refundable Fare","Inclusions":"Breakfast,Breakfast","Exclusions":null,"RoomCount":0,
     * "MaxAdultOccupancy":0,"MaxChildOccupancy":0,"BedType":"","RoomView":null,"RoomDescription":"","RoomAmenities":"",
     * "IncExcCondition":null,"IsInclusion":false,"Images":null}],"HotelImages":[{"Imagedesc":"Hotel Images","
     * Imagepath":"https://cdn.grnconnect.com/H!0758554/d66ec4ac77580354cfc9a5a6cd2bf78a.jpg"}],"HotelPolicy":null,
     * "ConvenienceFee":0,"ConvenienceFeeType":1,"ConvenienceFeeTotal":0,"AffiliateId":null,
     * RoomCombination":"OPENCOMBINATION","RoomChain":"1,2","Latitude":"17.44161239","Longitude":"78.35747376","Rating":null,
     * "Floors":0,"Alias":"","Punchline":null,"MapURL":null,"VideoURL":null,"PromoTitle":null,"PromoDetail":null,"Distances":null,
     * "AdditionalInfo":"","Awards":null,"Events":null,"OtherFees":null,"Facebook":"","Twitter":null,"Linkedin":""}],
     * "ProvidersCount":1,"ResponseStatus":200,"Message":null}
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
         * AvailableHotels : [{"HotelId":"H!0758554","HotelName":"Aditya Inn Gachibowli II","CategoryList":null,"PropertyType":null,"IsPrime":false,"Description":"Property Location When you stay at Hotel Aditya Homes in Hyderabad, youll be within a 10-minute drive of G.M.C. Balayogi Athletic Stadium and Gachibowli Indoor Stadium.  This hotel is 2.1 mi (3.4 km) from Kotla Vijayabhaskara Reddy Botanical Garden and 2.8 mi (4.5 km) from Sohrabji Godrej Green Business Centre.Rooms Make yourself at home in one of the 80 air-conditioned rooms featuring flat-screen televisions. Complimentary wireless Internet access keeps you connected, and cable programming is available for your entertainment. Private bathrooms with showers feature rainfall showerheads and complimentary toiletries.Amenities This hotel offers designated smoking areas.Dining A complimentary continental breakfast is included.Business, Other Amenities Featured amenities include a 24-hour front desk and laundry facilities.","HotelChain":"","StarRating":"3","Rooms":0,"MinRate":0,"RPH":"C!020801","WebService":"vrl7vl2rbacmxak4jmphlvnx6u","HotelAddress":"Flat 69,70,71 Gachibowli, Behind Domino's Pizza and ICICI Bank, Vinayak Nagar, Hyderabad 500032","PostalCode":"","City":"C!020801","LocationInfo":null,"PhoneNumber":"","Fax":"","Email":"","Website":"","Checkintime":null,"Checkouttime":null,"CreditCards":null,"HotelServices":null,"RoomServices":null,"Facilities":"Air conditioning , Housekeeping - daily , Laundry/Valet service , 24-hour front desk , Complimentary wireless internet","CountryCode":"IN","AirportCode":null,"SupplierType":"","PropertyCategory":null,"Provider":"Z+HjKTsZveEfDC1+wXUA4A==","RoomDetails":[{"Soldout":false,"RoomIndex":"1","RateType":"","HotelPackage":null,"RoomType":"Classic Double or Twin Room - India Citizens Only","RoomBasis":"","RoomTypeCode":"wcmqxydh5arcrwsvu2nwmfi","RatePlanCode":"4dgfbkrr4ircpusquocweho45ljldqni7g76xw32yvgbq","Validdays":null,"WsKey":"5hhvhkrt5arfjjbtv2irkh5e5lmk5s5c6k76pvd53zcrv7hdcpix5ahse4hctyxb6u","ExtGuestTotal":0,"RoomTotal":1017.82,"RoomNetTotal":1017.82,"PartnerFareDatails":{"NetFares":"1017.82","Commission":"0","CommissionType":0},"ServicetaxTotal":36.18,"Discount":0,"Commission":0,"expediaPropertyId":null,"RoomCancellationPolicy":"Last date for cancellation without charges: 25 Mar 2020 23:59:59. 100% will be charged, if cancelled after 27 Mar 2020 00:00:00","NoOfPax":0,"RefundRule":"Refundable Fare","Inclusions":"","Exclusions":null,"RoomCount":0,"MaxAdultOccupancy":0,"MaxChildOccupancy":0,"BedType":"","RoomView":null,"RoomDescription":"","RoomAmenities":"","IncExcCondition":null,"IsInclusion":false,"Images":null},{"Soldout":false,"RoomIndex":"2","RateType":"","HotelPackage":null,"RoomType":"Classic Double or Twin Room - India Citizens Only","RoomBasis":"","RoomTypeCode":"wcmqxydh5arcrwsvu2nwmfi","RatePlanCode":"4dgvbkrw5mrcpuswvwcwohw45ljldqni7g76vvt4yvgry","Validdays":null,"WsKey":"5hev3krw4ercpizpy2iw236uslmklqfc7g36pwd63zart7xkdpjh3bxweaesvyxb6vlq","ExtGuestTotal":0,"RoomTotal":1079.11,"RoomNetTotal":1079.11,"PartnerFareDatails":{"NetFares":"1079.11","Commission":"0","CommissionType":0},"ServicetaxTotal":38.89,"Discount":0,"Commission":0,"expediaPropertyId":null,"RoomCancellationPolicy":"Last date for cancellation without charges: 25 Mar 2020 23:59:59. 100% will be charged, if cancelled after 27 Mar 2020 00:00:00","NoOfPax":0,"RefundRule":"Refundable Fare","Inclusions":"Breakfast,Breakfast","Exclusions":null,"RoomCount":0,"MaxAdultOccupancy":0,"MaxChildOccupancy":0,"BedType":"","RoomView":null,"RoomDescription":"","RoomAmenities":"","IncExcCondition":null,"IsInclusion":false,"Images":null}],"HotelImages":[{"Imagedesc":"Hotel Images","Imagepath":"https://cdn.grnconnect.com/H!0758554/d66ec4ac77580354cfc9a5a6cd2bf78a.jpg"}],"HotelPolicy":null,"ConvenienceFee":0,"ConvenienceFeeType":1,"ConvenienceFeeTotal":0,"AffiliateId":null,"RoomCombination":"OPENCOMBINATION","RoomChain":"1,2","Latitude":"17.44161239","Longitude":"78.35747376","Rating":null,"Floors":0,"Alias":"","Punchline":null,"MapURL":null,"VideoURL":null,"PromoTitle":null,"PromoDetail":null,"Distances":null,"AdditionalInfo":"","Awards":null,"Events":null,"OtherFees":null,"Facebook":"","Twitter":null,"Linkedin":""}]
         * ProvidersCount : 1
         * ResponseStatus : 200
         * Message : null
         */

        private int ProvidersCount;
        private int ResponseStatus;
        private Object Message;
        private List<AvailableHotelsBean> AvailableHotels;

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

        public Object getMessage() {
            return Message;
        }

        public void setMessage(Object Message) {
            this.Message = Message;
        }

        public List<AvailableHotelsBean> getAvailableHotels() {
            return AvailableHotels;
        }

        public void setAvailableHotels(List<AvailableHotelsBean> AvailableHotels) {
            this.AvailableHotels = AvailableHotels;
        }

        public static class AvailableHotelsBean {
            /**
             * HotelId : H!0758554
             * HotelName : Aditya Inn Gachibowli II
             * CategoryList : null
             * PropertyType : null
             * IsPrime : false
             * Description : Property Location When you stay at Hotel Aditya Homes in Hyderabad, youll be within a 10-minute drive of G.M.C. Balayogi Athletic Stadium and Gachibowli Indoor Stadium.  This hotel is 2.1 mi (3.4 km) from Kotla Vijayabhaskara Reddy Botanical Garden and 2.8 mi (4.5 km) from Sohrabji Godrej Green Business Centre.Rooms Make yourself at home in one of the 80 air-conditioned rooms featuring flat-screen televisions. Complimentary wireless Internet access keeps you connected, and cable programming is available for your entertainment. Private bathrooms with showers feature rainfall showerheads and complimentary toiletries.Amenities This hotel offers designated smoking areas.Dining A complimentary continental breakfast is included.Business, Other Amenities Featured amenities include a 24-hour front desk and laundry facilities.
             * HotelChain :
             * StarRating : 3
             * Rooms : 0
             * MinRate : 0
             * RPH : C!020801
             * WebService : vrl7vl2rbacmxak4jmphlvnx6u
             * HotelAddress : Flat 69,70,71 Gachibowli, Behind Domino's Pizza and ICICI Bank, Vinayak Nagar, Hyderabad 500032
             * PostalCode :
             * City : C!020801
             * LocationInfo : null
             * PhoneNumber :
             * Fax :
             * Email :
             * Website :
             * Checkintime : null
             * Checkouttime : null
             * CreditCards : null
             * HotelServices : null
             * RoomServices : null
             * Facilities : Air conditioning , Housekeeping - daily , Laundry/Valet service , 24-hour front desk , Complimentary wireless internet
             * CountryCode : IN
             * AirportCode : null
             * SupplierType :
             * PropertyCategory : null
             * Provider : Z+HjKTsZveEfDC1+wXUA4A==
             * RoomDetails : [{"Soldout":false,"RoomIndex":"1","RateType":"","HotelPackage":null,"RoomType":"Classic Double or Twin Room - India Citizens Only","RoomBasis":"","RoomTypeCode":"wcmqxydh5arcrwsvu2nwmfi","RatePlanCode":"4dgfbkrr4ircpusquocweho45ljldqni7g76xw32yvgbq","Validdays":null,"WsKey":"5hhvhkrt5arfjjbtv2irkh5e5lmk5s5c6k76pvd53zcrv7hdcpix5ahse4hctyxb6u","ExtGuestTotal":0,"RoomTotal":1017.82,"RoomNetTotal":1017.82,"PartnerFareDatails":{"NetFares":"1017.82","Commission":"0","CommissionType":0},"ServicetaxTotal":36.18,"Discount":0,"Commission":0,"expediaPropertyId":null,"RoomCancellationPolicy":"Last date for cancellation without charges: 25 Mar 2020 23:59:59. 100% will be charged, if cancelled after 27 Mar 2020 00:00:00","NoOfPax":0,"RefundRule":"Refundable Fare","Inclusions":"","Exclusions":null,"RoomCount":0,"MaxAdultOccupancy":0,"MaxChildOccupancy":0,"BedType":"","RoomView":null,"RoomDescription":"","RoomAmenities":"","IncExcCondition":null,"IsInclusion":false,"Images":null},{"Soldout":false,"RoomIndex":"2","RateType":"","HotelPackage":null,"RoomType":"Classic Double or Twin Room - India Citizens Only","RoomBasis":"","RoomTypeCode":"wcmqxydh5arcrwsvu2nwmfi","RatePlanCode":"4dgvbkrw5mrcpuswvwcwohw45ljldqni7g76vvt4yvgry","Validdays":null,"WsKey":"5hev3krw4ercpizpy2iw236uslmklqfc7g36pwd63zart7xkdpjh3bxweaesvyxb6vlq","ExtGuestTotal":0,"RoomTotal":1079.11,"RoomNetTotal":1079.11,"PartnerFareDatails":{"NetFares":"1079.11","Commission":"0","CommissionType":0},"ServicetaxTotal":38.89,"Discount":0,"Commission":0,"expediaPropertyId":null,"RoomCancellationPolicy":"Last date for cancellation without charges: 25 Mar 2020 23:59:59. 100% will be charged, if cancelled after 27 Mar 2020 00:00:00","NoOfPax":0,"RefundRule":"Refundable Fare","Inclusions":"Breakfast,Breakfast","Exclusions":null,"RoomCount":0,"MaxAdultOccupancy":0,"MaxChildOccupancy":0,"BedType":"","RoomView":null,"RoomDescription":"","RoomAmenities":"","IncExcCondition":null,"IsInclusion":false,"Images":null}]
             * HotelImages : [{"Imagedesc":"Hotel Images","Imagepath":"https://cdn.grnconnect.com/H!0758554/d66ec4ac77580354cfc9a5a6cd2bf78a.jpg"}]
             * HotelPolicy : null
             * ConvenienceFee : 0
             * ConvenienceFeeType : 1
             * ConvenienceFeeTotal : 0
             * AffiliateId : null
             * RoomCombination : OPENCOMBINATION
             * RoomChain : 1,2
             * Latitude : 17.44161239
             * Longitude : 78.35747376
             * Rating : null
             * Floors : 0
             * Alias :
             * Punchline : null
             * MapURL : null
             * VideoURL : null
             * PromoTitle : null
             * PromoDetail : null
             * Distances : null
             * AdditionalInfo :
             * Awards : null
             * Events : null
             * OtherFees : null
             * Facebook :
             * Twitter : null
             * Linkedin :
             */

            private String HotelId;
            private String HotelName;
            private Object CategoryList;
            private Object PropertyType;
            private boolean IsPrime;
            private String Description;
            private String HotelChain;
            private String StarRating;
            private int Rooms;
            private int MinRate;
            private String RPH;
            private String WebService;
            private String HotelAddress;
            private String PostalCode;
            private String City;
            private Object LocationInfo;
            private String PhoneNumber;
            private String Fax;
            private String Email;
            private String Website;
            private Object Checkintime;
            private Object Checkouttime;
            private Object CreditCards;
            private Object HotelServices;
            private Object RoomServices;
            private String Facilities;
            private String CountryCode;
            private Object AirportCode;
            private String SupplierType;
            private Object PropertyCategory;
            private String Provider;
            private Object HotelPolicy;
            private int ConvenienceFee;
            private int ConvenienceFeeType;
            private int ConvenienceFeeTotal;
            private Object AffiliateId;
            private String RoomCombination;
            private String RoomChain;
            private String Latitude;
            private String Longitude;
            private Object Rating;
            private int Floors;
            private String Alias;
            private Object Punchline;
            private Object MapURL;
            private Object VideoURL;
            private Object PromoTitle;
            private Object PromoDetail;
            private Object Distances;
            private String AdditionalInfo;
            private Object Awards;
            private Object Events;
            private Object OtherFees;
            private String Facebook;
            private Object Twitter;
            private String Linkedin;
            private List<RoomDetailsBean> RoomDetails;
            private List<HotelImagesBean> HotelImages;

            public String getHotelId() {
                return HotelId;
            }

            public void setHotelId(String HotelId) {
                this.HotelId = HotelId;
            }

            public String getHotelName() {
                return HotelName;
            }

            public void setHotelName(String HotelName) {
                this.HotelName = HotelName;
            }

            public Object getCategoryList() {
                return CategoryList;
            }

            public void setCategoryList(Object CategoryList) {
                this.CategoryList = CategoryList;
            }

            public Object getPropertyType() {
                return PropertyType;
            }

            public void setPropertyType(Object PropertyType) {
                this.PropertyType = PropertyType;
            }

            public boolean isIsPrime() {
                return IsPrime;
            }

            public void setIsPrime(boolean IsPrime) {
                this.IsPrime = IsPrime;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getHotelChain() {
                return HotelChain;
            }

            public void setHotelChain(String HotelChain) {
                this.HotelChain = HotelChain;
            }

            public String getStarRating() {
                return StarRating;
            }

            public void setStarRating(String StarRating) {
                this.StarRating = StarRating;
            }

            public int getRooms() {
                return Rooms;
            }

            public void setRooms(int Rooms) {
                this.Rooms = Rooms;
            }

            public int getMinRate() {
                return MinRate;
            }

            public void setMinRate(int MinRate) {
                this.MinRate = MinRate;
            }

            public String getRPH() {
                return RPH;
            }

            public void setRPH(String RPH) {
                this.RPH = RPH;
            }

            public String getWebService() {
                return WebService;
            }

            public void setWebService(String WebService) {
                this.WebService = WebService;
            }

            public String getHotelAddress() {
                return HotelAddress;
            }

            public void setHotelAddress(String HotelAddress) {
                this.HotelAddress = HotelAddress;
            }

            public String getPostalCode() {
                return PostalCode;
            }

            public void setPostalCode(String PostalCode) {
                this.PostalCode = PostalCode;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }

            public Object getLocationInfo() {
                return LocationInfo;
            }

            public void setLocationInfo(Object LocationInfo) {
                this.LocationInfo = LocationInfo;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public String getFax() {
                return Fax;
            }

            public void setFax(String Fax) {
                this.Fax = Fax;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public String getWebsite() {
                return Website;
            }

            public void setWebsite(String Website) {
                this.Website = Website;
            }

            public Object getCheckintime() {
                return Checkintime;
            }

            public void setCheckintime(Object Checkintime) {
                this.Checkintime = Checkintime;
            }

            public Object getCheckouttime() {
                return Checkouttime;
            }

            public void setCheckouttime(Object Checkouttime) {
                this.Checkouttime = Checkouttime;
            }

            public Object getCreditCards() {
                return CreditCards;
            }

            public void setCreditCards(Object CreditCards) {
                this.CreditCards = CreditCards;
            }

            public Object getHotelServices() {
                return HotelServices;
            }

            public void setHotelServices(Object HotelServices) {
                this.HotelServices = HotelServices;
            }

            public Object getRoomServices() {
                return RoomServices;
            }

            public void setRoomServices(Object RoomServices) {
                this.RoomServices = RoomServices;
            }

            public String getFacilities() {
                return Facilities;
            }

            public void setFacilities(String Facilities) {
                this.Facilities = Facilities;
            }

            public String getCountryCode() {
                return CountryCode;
            }

            public void setCountryCode(String CountryCode) {
                this.CountryCode = CountryCode;
            }

            public Object getAirportCode() {
                return AirportCode;
            }

            public void setAirportCode(Object AirportCode) {
                this.AirportCode = AirportCode;
            }

            public String getSupplierType() {
                return SupplierType;
            }

            public void setSupplierType(String SupplierType) {
                this.SupplierType = SupplierType;
            }

            public Object getPropertyCategory() {
                return PropertyCategory;
            }

            public void setPropertyCategory(Object PropertyCategory) {
                this.PropertyCategory = PropertyCategory;
            }

            public String getProvider() {
                return Provider;
            }

            public void setProvider(String Provider) {
                this.Provider = Provider;
            }

            public Object getHotelPolicy() {
                return HotelPolicy;
            }

            public void setHotelPolicy(Object HotelPolicy) {
                this.HotelPolicy = HotelPolicy;
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

            public int getConvenienceFeeTotal() {
                return ConvenienceFeeTotal;
            }

            public void setConvenienceFeeTotal(int ConvenienceFeeTotal) {
                this.ConvenienceFeeTotal = ConvenienceFeeTotal;
            }

            public Object getAffiliateId() {
                return AffiliateId;
            }

            public void setAffiliateId(Object AffiliateId) {
                this.AffiliateId = AffiliateId;
            }

            public String getRoomCombination() {
                return RoomCombination;
            }

            public void setRoomCombination(String RoomCombination) {
                this.RoomCombination = RoomCombination;
            }

            public String getRoomChain() {
                return RoomChain;
            }

            public void setRoomChain(String RoomChain) {
                this.RoomChain = RoomChain;
            }

            public String getLatitude() {
                return Latitude;
            }

            public void setLatitude(String Latitude) {
                this.Latitude = Latitude;
            }

            public String getLongitude() {
                return Longitude;
            }

            public void setLongitude(String Longitude) {
                this.Longitude = Longitude;
            }

            public Object getRating() {
                return Rating;
            }

            public void setRating(Object Rating) {
                this.Rating = Rating;
            }

            public int getFloors() {
                return Floors;
            }

            public void setFloors(int Floors) {
                this.Floors = Floors;
            }

            public String getAlias() {
                return Alias;
            }

            public void setAlias(String Alias) {
                this.Alias = Alias;
            }

            public Object getPunchline() {
                return Punchline;
            }

            public void setPunchline(Object Punchline) {
                this.Punchline = Punchline;
            }

            public Object getMapURL() {
                return MapURL;
            }

            public void setMapURL(Object MapURL) {
                this.MapURL = MapURL;
            }

            public Object getVideoURL() {
                return VideoURL;
            }

            public void setVideoURL(Object VideoURL) {
                this.VideoURL = VideoURL;
            }

            public Object getPromoTitle() {
                return PromoTitle;
            }

            public void setPromoTitle(Object PromoTitle) {
                this.PromoTitle = PromoTitle;
            }

            public Object getPromoDetail() {
                return PromoDetail;
            }

            public void setPromoDetail(Object PromoDetail) {
                this.PromoDetail = PromoDetail;
            }

            public Object getDistances() {
                return Distances;
            }

            public void setDistances(Object Distances) {
                this.Distances = Distances;
            }

            public String getAdditionalInfo() {
                return AdditionalInfo;
            }

            public void setAdditionalInfo(String AdditionalInfo) {
                this.AdditionalInfo = AdditionalInfo;
            }

            public Object getAwards() {
                return Awards;
            }

            public void setAwards(Object Awards) {
                this.Awards = Awards;
            }

            public Object getEvents() {
                return Events;
            }

            public void setEvents(Object Events) {
                this.Events = Events;
            }

            public Object getOtherFees() {
                return OtherFees;
            }

            public void setOtherFees(Object OtherFees) {
                this.OtherFees = OtherFees;
            }

            public String getFacebook() {
                return Facebook;
            }

            public void setFacebook(String Facebook) {
                this.Facebook = Facebook;
            }

            public Object getTwitter() {
                return Twitter;
            }

            public void setTwitter(Object Twitter) {
                this.Twitter = Twitter;
            }

            public String getLinkedin() {
                return Linkedin;
            }

            public void setLinkedin(String Linkedin) {
                this.Linkedin = Linkedin;
            }

            public List<RoomDetailsBean> getRoomDetails() {
                return RoomDetails;
            }

            public void setRoomDetails(List<RoomDetailsBean> RoomDetails) {
                this.RoomDetails = RoomDetails;
            }

            public List<HotelImagesBean> getHotelImages() {
                return HotelImages;
            }

            public void setHotelImages(List<HotelImagesBean> HotelImages) {
                this.HotelImages = HotelImages;
            }

            public static class RoomDetailsBean {
                /**
                 * Soldout : false
                 * RoomIndex : 1
                 * RateType :
                 * HotelPackage : null
                 * RoomType : Classic Double or Twin Room - India Citizens Only
                 * RoomBasis :
                 * RoomTypeCode : wcmqxydh5arcrwsvu2nwmfi
                 * RatePlanCode : 4dgfbkrr4ircpusquocweho45ljldqni7g76xw32yvgbq
                 * Validdays : null
                 * WsKey : 5hhvhkrt5arfjjbtv2irkh5e5lmk5s5c6k76pvd53zcrv7hdcpix5ahse4hctyxb6u
                 * ExtGuestTotal : 0
                 * RoomTotal : 1017.82
                 * RoomNetTotal : 1017.82
                 * PartnerFareDatails : {"NetFares":"1017.82","Commission":"0","CommissionType":0}
                 * ServicetaxTotal : 36.18
                 * Discount : 0
                 * Commission : 0
                 * expediaPropertyId : null
                 * RoomCancellationPolicy : Last date for cancellation without charges: 25 Mar 2020 23:59:59. 100% will be charged, if cancelled after 27 Mar 2020 00:00:00
                 * NoOfPax : 0
                 * RefundRule : Refundable Fare
                 * Inclusions :
                 * Exclusions : null
                 * RoomCount : 0
                 * MaxAdultOccupancy : 0
                 * MaxChildOccupancy : 0
                 * BedType :
                 * RoomView : null
                 * RoomDescription :
                 * RoomAmenities :
                 * IncExcCondition : null
                 * IsInclusion : false
                 * Images : null
                 */

                private boolean Soldout;
                private String RoomIndex;
                private String RateType;
                private Object HotelPackage;
                private String RoomType;
                private String RoomBasis;
                private String RoomTypeCode;
                private String RatePlanCode;
                private Object Validdays;
                private String WsKey;
                private int ExtGuestTotal;
                private double RoomTotal;
                private double RoomNetTotal;
                private PartnerFareDatailsBean PartnerFareDatails;
                private double ServicetaxTotal;
                private int Discount;
                private int Commission;
                private Object expediaPropertyId;
                private String RoomCancellationPolicy;
                private int NoOfPax;
                private String RefundRule;
                private String Inclusions;
                private Object Exclusions;
                private int RoomCount;
                private int MaxAdultOccupancy;
                private int MaxChildOccupancy;
                private String BedType;
                private Object RoomView;
                private String RoomDescription;
                private String RoomAmenities;
                private Object IncExcCondition;
                private boolean IsInclusion;
                private Object Images;

                public boolean isSoldout() {
                    return Soldout;
                }

                public void setSoldout(boolean Soldout) {
                    this.Soldout = Soldout;
                }

                public String getRoomIndex() {
                    return RoomIndex;
                }

                public void setRoomIndex(String RoomIndex) {
                    this.RoomIndex = RoomIndex;
                }

                public String getRateType() {
                    return RateType;
                }

                public void setRateType(String RateType) {
                    this.RateType = RateType;
                }

                public Object getHotelPackage() {
                    return HotelPackage;
                }

                public void setHotelPackage(Object HotelPackage) {
                    this.HotelPackage = HotelPackage;
                }

                public String getRoomType() {
                    return RoomType;
                }

                public void setRoomType(String RoomType) {
                    this.RoomType = RoomType;
                }

                public String getRoomBasis() {
                    return RoomBasis;
                }

                public void setRoomBasis(String RoomBasis) {
                    this.RoomBasis = RoomBasis;
                }

                public String getRoomTypeCode() {
                    return RoomTypeCode;
                }

                public void setRoomTypeCode(String RoomTypeCode) {
                    this.RoomTypeCode = RoomTypeCode;
                }

                public String getRatePlanCode() {
                    return RatePlanCode;
                }

                public void setRatePlanCode(String RatePlanCode) {
                    this.RatePlanCode = RatePlanCode;
                }

                public Object getValiddays() {
                    return Validdays;
                }

                public void setValiddays(Object Validdays) {
                    this.Validdays = Validdays;
                }

                public String getWsKey() {
                    return WsKey;
                }

                public void setWsKey(String WsKey) {
                    this.WsKey = WsKey;
                }

                public int getExtGuestTotal() {
                    return ExtGuestTotal;
                }

                public void setExtGuestTotal(int ExtGuestTotal) {
                    this.ExtGuestTotal = ExtGuestTotal;
                }

                public double getRoomTotal() {
                    return RoomTotal;
                }

                public void setRoomTotal(double RoomTotal) {
                    this.RoomTotal = RoomTotal;
                }

                public double getRoomNetTotal() {
                    return RoomNetTotal;
                }

                public void setRoomNetTotal(double RoomNetTotal) {
                    this.RoomNetTotal = RoomNetTotal;
                }

                public PartnerFareDatailsBean getPartnerFareDatails() {
                    return PartnerFareDatails;
                }

                public void setPartnerFareDatails(PartnerFareDatailsBean PartnerFareDatails) {
                    this.PartnerFareDatails = PartnerFareDatails;
                }

                public double getServicetaxTotal() {
                    return ServicetaxTotal;
                }

                public void setServicetaxTotal(double ServicetaxTotal) {
                    this.ServicetaxTotal = ServicetaxTotal;
                }

                public int getDiscount() {
                    return Discount;
                }

                public void setDiscount(int Discount) {
                    this.Discount = Discount;
                }

                public int getCommission() {
                    return Commission;
                }

                public void setCommission(int Commission) {
                    this.Commission = Commission;
                }

                public Object getExpediaPropertyId() {
                    return expediaPropertyId;
                }

                public void setExpediaPropertyId(Object expediaPropertyId) {
                    this.expediaPropertyId = expediaPropertyId;
                }

                public String getRoomCancellationPolicy() {
                    return RoomCancellationPolicy;
                }

                public void setRoomCancellationPolicy(String RoomCancellationPolicy) {
                    this.RoomCancellationPolicy = RoomCancellationPolicy;
                }

                public int getNoOfPax() {
                    return NoOfPax;
                }

                public void setNoOfPax(int NoOfPax) {
                    this.NoOfPax = NoOfPax;
                }

                public String getRefundRule() {
                    return RefundRule;
                }

                public void setRefundRule(String RefundRule) {
                    this.RefundRule = RefundRule;
                }

                public String getInclusions() {
                    return Inclusions;
                }

                public void setInclusions(String Inclusions) {
                    this.Inclusions = Inclusions;
                }

                public Object getExclusions() {
                    return Exclusions;
                }

                public void setExclusions(Object Exclusions) {
                    this.Exclusions = Exclusions;
                }

                public int getRoomCount() {
                    return RoomCount;
                }

                public void setRoomCount(int RoomCount) {
                    this.RoomCount = RoomCount;
                }

                public int getMaxAdultOccupancy() {
                    return MaxAdultOccupancy;
                }

                public void setMaxAdultOccupancy(int MaxAdultOccupancy) {
                    this.MaxAdultOccupancy = MaxAdultOccupancy;
                }

                public int getMaxChildOccupancy() {
                    return MaxChildOccupancy;
                }

                public void setMaxChildOccupancy(int MaxChildOccupancy) {
                    this.MaxChildOccupancy = MaxChildOccupancy;
                }

                public String getBedType() {
                    return BedType;
                }

                public void setBedType(String BedType) {
                    this.BedType = BedType;
                }

                public Object getRoomView() {
                    return RoomView;
                }

                public void setRoomView(Object RoomView) {
                    this.RoomView = RoomView;
                }

                public String getRoomDescription() {
                    return RoomDescription;
                }

                public void setRoomDescription(String RoomDescription) {
                    this.RoomDescription = RoomDescription;
                }

                public String getRoomAmenities() {
                    return RoomAmenities;
                }

                public void setRoomAmenities(String RoomAmenities) {
                    this.RoomAmenities = RoomAmenities;
                }

                public Object getIncExcCondition() {
                    return IncExcCondition;
                }

                public void setIncExcCondition(Object IncExcCondition) {
                    this.IncExcCondition = IncExcCondition;
                }

                public boolean isIsInclusion() {
                    return IsInclusion;
                }

                public void setIsInclusion(boolean IsInclusion) {
                    this.IsInclusion = IsInclusion;
                }

                public Object getImages() {
                    return Images;
                }

                public void setImages(Object Images) {
                    this.Images = Images;
                }

                public static class PartnerFareDatailsBean {
                    /**
                     * NetFares : 1017.82
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

            public static class HotelImagesBean {
                /**
                 * Imagedesc : Hotel Images
                 * Imagepath : https://cdn.grnconnect.com/H!0758554/d66ec4ac77580354cfc9a5a6cd2bf78a.jpg
                 */

                private String Imagedesc;
                private String Imagepath;

                public String getImagedesc() {
                    return Imagedesc;
                }

                public void setImagedesc(String Imagedesc) {
                    this.Imagedesc = Imagedesc;
                }

                public String getImagepath() {
                    return Imagepath;
                }

                public void setImagepath(String Imagepath) {
                    this.Imagepath = Imagepath;
                }
            }
        }
    }
}
