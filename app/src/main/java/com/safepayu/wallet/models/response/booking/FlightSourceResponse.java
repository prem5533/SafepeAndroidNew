package com.safepayu.wallet.models.response.booking;

public class FlightSourceResponse {

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
