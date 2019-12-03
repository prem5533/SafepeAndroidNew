package com.safepayu.wallet.models.request.booking_hotel;

import java.util.List;

public class AvailableHotelRequest {

    /**
     * childrenAges : ["9~5","11","6~10"]
     * arrivalDate : 30-03-2020
     * departureDate : 31-03-2020
     * destinationId : 5
     * hoteltype : 1
     * NoOfDays : 4
     * rooms : 4
     * adults : ["4","2","1","3"]
     * children : ["2","1","2"]
     */

    private String arrivalDate;
    private String departureDate;
    private String destinationId;
    private String hoteltype;
    private String NoOfDays;
    private String rooms;
    private List<String> childrenAges;
    private List<String> adults;
    private List<String> children;

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
        return NoOfDays;
    }

    public void setNoOfDays(String NoOfDays) {
        this.NoOfDays = NoOfDays;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
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
