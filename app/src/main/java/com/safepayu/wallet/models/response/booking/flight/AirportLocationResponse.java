package com.safepayu.wallet.models.response.booking.flight;

import java.util.List;

public class AirportLocationResponse {
    /**
     * status : true
     * message : Available Airports
     * data : [{"AirportCode":"KTM","City":"Khatmand","Country":"India","AirportDesc":"Agatti Island","Type":"C"},{"AirportCode":"PKR","City":"Pokhara","Country":"India","AirportDesc":"Agatti Island","Type":"C"},{"AirportCode":"IXA","City":"Agartala","Country":"India","AirportDesc":"Singerbhil","Type":"C"},{"AirportCode":"AGX","City":"Agatti Island","Country":"India","AirportDesc":"Agatti Island","Type":"C"},{"AirportCode":"AGR","City":"Agra","Country":"India","AirportDesc":"Kheria","Type":"C"},{"AirportCode":"AMD","City":"AHMEDABAD","Country":"India","AirportDesc":"Ahmedabad","Type":"C"},{"AirportCode":"AKD","City":"Akola","Country":"India","AirportDesc":"Akola","Type":"C"},{"AirportCode":"RGH","City":"Balurghat","Country":"India","AirportDesc":"Balurghat","Type":"C"},{"AirportCode":"BLR","City":"BANGALORE","Country":"India","AirportDesc":"Bangalore International Airport","Type":"C"},{"AirportCode":"BEK","City":"Bareli","Country":"India","AirportDesc":"Bareli","Type":"C"},{"AirportCode":"IXG","City":"Belgaum","Country":"India","AirportDesc":"Sambre","Type":"C"},{"AirportCode":"BUP","City":"Bhatinda","Country":"India","AirportDesc":"Bhatinda","Type":"C"},{"AirportCode":"BHU","City":"Bhavnagar","Country":"India","AirportDesc":"Bhavnagar","Type":"C"},{"AirportCode":"BHO","City":"Bhopal","Country":"India","AirportDesc":"Bhopal","Type":"C"},{"AirportCode":"VDY","City":"Vijaynagar","Country":"India","AirportDesc":"Vijaynagar","Type":"C"},{"AirportCode":"BBI","City":"Bhubaneswar","Country":"India","AirportDesc":"Bhubaneswar","Type":"C"},{"AirportCode":"BHJ","City":"Bhuj","Country":"India","AirportDesc":"Rudra Mata","Type":"C"},{"AirportCode":"KUU","City":"Bhuntar","Country":"India","AirportDesc":"Kullu Manali","Type":"C"},{"AirportCode":"BKB","City":"Bikaner","Country":"India","AirportDesc":"Bikaner","Type":"C"},{"AirportCode":"GAU","City":"Guwahati","Country":"India","AirportDesc":"Guwahati","Type":"C"},{"AirportCode":"PAB","City":"Bilaspur","Country":"India","AirportDesc":"Bilaspur","Type":"C"},{"AirportCode":"RPR","City":"Raipur","Country":"India","AirportDesc":"Raipur","Type":"C"},{"AirportCode":"RJA","City":"Rajahmundry","Country":"India","AirportDesc":"Rajahmundry","Type":"C"},{"AirportCode":"RAJ","City":"Rajkot","Country":"India","AirportDesc":"Civil","Type":"C"},{"AirportCode":"RJI","City":"Rajouri","Country":"India","AirportDesc":"Rajouri","Type":"C"},{"AirportCode":"RMD","City":"Ramagundam","Country":"India","AirportDesc":"Ramagundam","Type":"C"},{"AirportCode":"IXR","City":"Ranchi","Country":"India","AirportDesc":"Ranchi","Type":"C"},{"AirportCode":"RTC","City":"Ratnagiri","Country":"India","AirportDesc":"Ratnagiri","Type":"C"},{"AirportCode":"IXQ","City":"Kamalpur","Country":"India","AirportDesc":"Kamalpur","Type":"C"},{"AirportCode":"IXY","City":"Kandla","Country":"India","AirportDesc":"Kandla","Type":"C"},{"AirportCode":"KNU","City":"Kanpur","Country":"India","AirportDesc":"Kanpur","Type":"C"},{"AirportCode":"IXK","City":"Keshod","Country":"India","AirportDesc":"Keshod","Type":"C"},{"AirportCode":"HJR","City":"Khajuraho","Country":"India","AirportDesc":"Khajuraho","Type":"C"},{"AirportCode":"IXN","City":"Khowai","Country":"India","AirportDesc":"Khowai","Type":"C"},{"AirportCode":"MOH","City":"Mohanbari","Country":"India","AirportDesc":"Mohanbari","Type":"C"},{"AirportCode":"BOM","City":"MUMBAI","Country":"India","AirportDesc":"Chhatrapati Shivaji International","Type":"C"},{"AirportCode":"MZA","City":"Muzaffarnagar","Country":"India","AirportDesc":"Muzaffarnagar","Type":"C"},{"AirportCode":"MZU","City":"Muzaffarpur","Country":"India","AirportDesc":"Muzaffarpur","Type":"C"},{"AirportCode":"MYQ","City":"Mysore","Country":"India","AirportDesc":"Mysore","Type":"C"},{"AirportCode":"NAG","City":"Nagpur","Country":"India","AirportDesc":"Sonegaon","Type":"C"},{"AirportCode":"NDC","City":"Nanded","Country":"India","AirportDesc":"Nanded","Type":"C"},{"AirportCode":"ISK","City":"Nasik","Country":"India","AirportDesc":"Gandhinagar Arpt","Type":"C"},{"AirportCode":"DEL","City":"NEW DELHI","Country":"India","AirportDesc":"Indira Gandhi Intl","Type":"C"},{"AirportCode":"NVY","City":"Neyveli","Country":"India","AirportDesc":"Neyveli","Type":"C"},{"AirportCode":"DMU","City":"Dimapur","Country":"India","AirportDesc":"Dimapur","Type":"C"},{"AirportCode":"DIU","City":"Diu","Country":"India","AirportDesc":"Diu","Type":"C"},{"AirportCode":"GAU","City":"Gawahati","Country":"India","AirportDesc":"Borjhar","Type":"C"},{"AirportCode":"GAY","City":"Gaya","Country":"India","AirportDesc":"Gaya","Type":"C"},{"AirportCode":"GOI","City":"Goa","Country":"India","AirportDesc":"Dabolim","Type":"C"},{"AirportCode":"GOP","City":"Gorakhpur","Country":"India","AirportDesc":"Gorakhpur","Type":"C"},{"AirportCode":"GUX","City":"Guna","Country":"India","AirportDesc":"Guna","Type":"C"},{"AirportCode":"GWL","City":"Gwalior","Country":"India","AirportDesc":"Gwalior","Type":"C"},{"AirportCode":"CBD","City":"Car Nicobar","Country":"India","AirportDesc":"Car Nicobar","Type":"C"},{"AirportCode":"IXC","City":"Chandigarh","Country":"India","AirportDesc":"Chandigarh","Type":"C"},{"AirportCode":"MAA","City":"CHENNAI","Country":"India","AirportDesc":"Madras International [Meenambakkam]","Type":"C"},{"AirportCode":"CJB","City":"Coimbatore","Country":"India","AirportDesc":"Peelamedu","Type":"C"},{"AirportCode":"COH","City":"Cooch Behar","Country":"India","AirportDesc":"Cooch Behar","Type":"C"},{"AirportCode":"CDP","City":"Cuddapah","Country":"India","AirportDesc":"Cuddapah","Type":"C"},{"AirportCode":"NMB","City":"Daman","Country":"India","AirportDesc":"Daman","Type":"C"},{"AirportCode":"DAE","City":"Daparizo","Country":"India","AirportDesc":"Daparizo","Type":"C"},{"AirportCode":"DAI","City":"Darjeeling","Country":"India","AirportDesc":"Darjeeling","Type":"C"},{"AirportCode":"DED","City":"Dehra Dun","Country":"India","AirportDesc":"Dehra Dun","Type":"C"},{"AirportCode":"DEP","City":"Deparizo","Country":"India","AirportDesc":"Deparizo","Type":"C"},{"AirportCode":"DBD","City":"Dhanbad","Country":"India","AirportDesc":"Dhanbad","Type":"C"},{"AirportCode":"DHM","City":"Dharamsala","Country":"India","AirportDesc":"Gaggal Airport","Type":"C"},{"AirportCode":"DIB","City":"Dibrugarh","Country":"India","AirportDesc":"Dibrugarh","Type":"C"},{"AirportCode":"OMN","City":"Osmanabad","Country":"India","AirportDesc":"Osmanabad","Type":"C"},{"AirportCode":"PGH","City":"Pantnagar","Country":"India","AirportDesc":"Pantnagar","Type":"C"},{"AirportCode":"IXT","City":"Pasighat","Country":"India","AirportDesc":"Pasighat","Type":"C"},{"AirportCode":"IXP","City":"Pathankot","Country":"India","AirportDesc":"Pathankot","Type":"C"},{"AirportCode":"PAT","City":"Patna","Country":"India","AirportDesc":"Patna","Type":"C"},{"AirportCode":"PNY","City":"Pondicherry","Country":"India","AirportDesc":"Pondicherry","Type":"C"},{"AirportCode":"PBD","City":"Porbandar","Country":"India","AirportDesc":"Porbandar","Type":"C"},{"AirportCode":"IXZ","City":"Port Blair","Country":"India","AirportDesc":"Port Blair","Type":"C"},{"AirportCode":"PNQ","City":"Pune","Country":"India","AirportDesc":"Lohegaon","Type":"C"},{"AirportCode":"PUT","City":"Puttaparthi","Country":"India","AirportDesc":"Puttaprathe","Type":"C"},{"AirportCode":"SXR","City":"Srinagar","Country":"India","AirportDesc":"Srinagar","Type":"C"},{"AirportCode":"STV","City":"Surat","Country":"India","AirportDesc":"Surat","Type":"C"},{"AirportCode":"TEZ","City":"Tezpur","Country":"India","AirportDesc":"Salonibari","Type":"C"},{"AirportCode":"TEI","City":"Tezu","Country":"India","AirportDesc":"Tezu","Type":"C"},{"AirportCode":"TJV","City":"Thanjavur","Country":"India","AirportDesc":"Thanjavur","Type":"C"},{"AirportCode":"TRZ","City":"Tiruchirapally","Country":"India","AirportDesc":"Civil","Type":"C"},{"AirportCode":"TIR","City":"Tirupati","Country":"India","AirportDesc":"Tirupati","Type":"C"},{"AirportCode":"TRV","City":"Trivandrum","Country":"India","AirportDesc":"Thiruvananthapuram International","Type":"C"},{"AirportCode":"TCR","City":"Tuticorin","Country":"India","AirportDesc":"Tuticorin","Type":"C"},{"AirportCode":"UDR","City":"Udaipur","Country":"India","AirportDesc":"Dabok","Type":"C"},{"AirportCode":"BDQ","City":"VADODARA","Country":"India","AirportDesc":"Vadodara","Type":"C"},{"AirportCode":"VNS","City":"Varanasi","Country":"India","AirportDesc":"Varanasi","Type":"C"},{"AirportCode":"REW","City":"Rewa","Country":"India","AirportDesc":"Rewa","Type":"C"},{"AirportCode":"RRK","City":"Rourkela","Country":"India","AirportDesc":"Rourkela","Type":"C"},{"AirportCode":"RUP","City":"Rupsi","Country":"India","AirportDesc":"Rupsi","Type":"C"},{"AirportCode":"SXV","City":"Salem","Country":"India","AirportDesc":"Salem","Type":"C"},{"AirportCode":"TNI","City":"Satna","Country":"India","AirportDesc":"Satna","Type":"C"},{"AirportCode":"SHL","City":"Shillong","Country":"India","AirportDesc":"Shillong","Type":"C"},{"AirportCode":"SSE","City":"Sholapur","Country":"India","AirportDesc":"Sholapur","Type":"C"},{"AirportCode":"IXS","City":"Silchar","Country":"India","AirportDesc":"Kumbhirgram","Type":"C"},{"AirportCode":"SLV","City":"Simla","Country":"India","AirportDesc":"Simla","Type":"C"},{"AirportCode":"VGA","City":"Vijayawada","Country":"India","AirportDesc":"Vijayawada","Type":"C"},{"AirportCode":"VTZ","City":"Vishakhapatnam","Country":"India","AirportDesc":"Vishakhapatnam","Type":"C"},{"AirportCode":"WGC","City":"Warangal","Country":"India","AirportDesc":"Warangal","Type":"C"},{"AirportCode":"AJL","City":"AIJWAL","Country":"India","AirportDesc":"India","Type":"D"},{"AirportCode":"IXD","City":"ALLAHABAD","Country":"Bamrauli","AirportDesc":"Warangal","Type":"D"},{"AirportCode":"IXV","City":"ALONG","Country":"India","AirportDesc":"India","Type":"D"},{"AirportCode":"ATQ","City":"AMRITSAR","Country":"India","AirportDesc":"India","Type":"D"},{"AirportCode":"IXU","City":"AURANGABAD","Country":"India","AirportDesc":"India","Type":"D"},{"AirportCode":"BEP","City":"BELLARY","Country":"India","AirportDesc":"India","Type":"D"},{"AirportCode":"CCJ","City":"CALICUT","Country":"India","AirportDesc":"India","Type":"D"},{"AirportCode":"LTU","City":"LATUR","Country":"India","AirportDesc":"India","Type":"D"},{"AirportCode":"COK","City":"KOCHI","Country":"India","AirportDesc":"Kochi","Type":"C"},{"AirportCode":"KLH","City":"Kolhapur","Country":"India","AirportDesc":"Kolhapur","Type":"C"},{"AirportCode":"CCU","City":"KOLKATA","Country":"India","AirportDesc":"Netaji Subhas Chandra","Type":"C"},{"AirportCode":"KTU","City":"Kota","Country":"India","AirportDesc":"Kota","Type":"C"},{"AirportCode":"IXL","City":"Leh","Country":"India","AirportDesc":"Bakula Rimpoche","Type":"C"},{"AirportCode":"IXI","City":"Lilabari","Country":"India","AirportDesc":"Lilabari","Type":"C"},{"AirportCode":"LKO","City":"Lucknow","Country":"India","AirportDesc":"Amausi","Type":"C"},{"AirportCode":"LUH","City":"Ludhiana","Country":"India","AirportDesc":"Ludhiana","Type":"C"},{"AirportCode":"IXM","City":"Madurai","Country":"India","AirportDesc":"Madurai","Type":"C"},{"AirportCode":"LDA","City":"Malda","Country":"India","AirportDesc":"Malda","Type":"C"},{"AirportCode":"IXE","City":"Mangalore","Country":"India","AirportDesc":"Mangalore","Type":"C"},{"AirportCode":"HSS","City":"Hissar","Country":"India","AirportDesc":"Hissar","Type":"C"},{"AirportCode":"HBX","City":"Hubli","Country":"India","AirportDesc":"Hubli","Type":"C"},{"AirportCode":"HYD","City":"HYDERABAD","Country":"India","AirportDesc":"Rajiv Gandhi Airport","Type":"C"},{"AirportCode":"IMF","City":"Imphal","Country":"India","AirportDesc":"Municipal","Type":"C"},{"AirportCode":"IDR","City":"Indore","Country":"India","AirportDesc":"Devi Ahilyabai Holkar","Type":"C"},{"AirportCode":"JLR","City":"Jabalpur","Country":"India","AirportDesc":"Jabalpur","Type":"C"},{"AirportCode":"JGB","City":"Jagdalpur","Country":"India","AirportDesc":"Jagdalpur","Type":"C"},{"AirportCode":"JAI","City":"Jaipur","Country":"India","AirportDesc":"Sanganeer","Type":"C"},{"AirportCode":"JSA","City":"Jaisalmer","Country":"India","AirportDesc":"Jaisalmer","Type":"C"},{"AirportCode":"IXJ","City":"Jammu","Country":"India","AirportDesc":"Satwari","Type":"C"},{"AirportCode":"JGA","City":"Jamnagar","Country":"India","AirportDesc":"Govardhanpur","Type":"C"},{"AirportCode":"IXW","City":"Jamshedpur","Country":"India","AirportDesc":"Sonari","Type":"C"},{"AirportCode":"JDH","City":"Jodhpur","Country":"India","AirportDesc":"Jodhpur","Type":"C"},{"AirportCode":"PYB","City":"Jeypore","Country":"India","AirportDesc":"Jeypore","Type":"C"},{"AirportCode":"JRH","City":"Jorhat","Country":"India","AirportDesc":"Rowriah","Type":"C"},{"AirportCode":"IXH","City":"Kailashahar","Country":"India","AirportDesc":"Kailashahar","Type":"C"},{"AirportCode":"IXB","City":"Bagdogra","Country":"India","AirportDesc":"Bagdogra","Type":"D"},{"AirportCode":"SAG","City":"Shirdi","Country":"India","AirportDesc":"Shirdi Airport","Type":"C"}]
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
         * AirportCode : KTM
         * City : Khatmand
         * Country : India
         * AirportDesc : Agatti Island
         * Type : C
         */

        private String AirportCode;
        private String City;
        private String Country;
        private String AirportDesc;
        private String Type;

        public String getAirportCode() {
            return AirportCode;
        }

        public void setAirportCode(String AirportCode) {
            this.AirportCode = AirportCode;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String Country) {
            this.Country = Country;
        }

        public String getAirportDesc() {
            return AirportDesc;
        }

        public void setAirportDesc(String AirportDesc) {
            this.AirportDesc = AirportDesc;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }
    }
}
