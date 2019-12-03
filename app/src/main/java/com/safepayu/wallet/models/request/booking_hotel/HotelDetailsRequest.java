package com.safepayu.wallet.models.request.booking_hotel;

import java.util.List;

public class HotelDetailsRequest {

    /**
     * cityId : 5
     * hotelId : H!0758554
     * webService : vrl7vl2rbacmxak4jmphlvnx6u
     * childrenAges : [""]
     * arrivalDate : 30-03-2020
     * departureDate : 31-03-2020
     * destinationId : 5
     * hoteltype : 1
     * noOfDays : 1
     * rooms : 4
     * adults : ["1"]
     * children : [""]
     * provider : Z+HjKTsZveEfDC1+wXUA4A==
     * roomscount : 1
     */

    private String cityId;
    private String hotelId;
    private String webService;
    private String arrivalDate;
    private String departureDate;
    private String destinationId;
    private String hoteltype;
    private String noOfDays;
    private String rooms;
    private String provider;
    private String roomscount;
    private List<String> childrenAges;
    private List<String> adults;
    private List<String> children;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getWebService() {
        return webService;
    }

    public void setWebService(String webService) {
        this.webService = webService;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getHoteltype() {
        return hoteltype;
    }

    public void setHoteltype(String hoteltype) {
        this.hoteltype = hoteltype;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRoomscount() {
        return roomscount;
    }

    public void setRoomscount(String roomscount) {
        this.roomscount = roomscount;
    }

    public List<String> getChildrenAges() {
        return childrenAges;
    }

    public void setChildrenAges(List<String> childrenAges) {
        this.childrenAges = childrenAges;
    }

    public List<String> getAdults() {
        return adults;
    }

    public void setAdults(List<String> adults) {
        this.adults = adults;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
