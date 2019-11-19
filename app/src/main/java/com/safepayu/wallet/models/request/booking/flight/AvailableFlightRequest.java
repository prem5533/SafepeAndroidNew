package com.safepayu.wallet.models.request.booking.flight;

public class AvailableFlightRequest {

    /**
     * source : HYD
     * destination : BLR
     * journeyDate : 01-01-2020
     * tripType : 1
     * user :
     * userType : 5
     * adults : 1
     * infants : 0
     * children : 0
     * flightType : 1
     * returnDate :
     * travelClass : E
     */

    private String source;
    private String destination;
    private String journeyDate;
    private String tripType;
    private String user;
    private String userType;
    private String adults;
    private String infants;
    private String children;
    private String flightType;
    private String returnDate;
    private String travelClass;
  // private String   count;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getInfants() {
        return infants;
    }

    public void setInfants(String infants) {
        this.infants = infants;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    /*public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }*/
}
