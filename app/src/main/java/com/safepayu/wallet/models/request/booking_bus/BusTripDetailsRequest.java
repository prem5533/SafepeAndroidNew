package com.safepayu.wallet.models.request.booking_bus;

public class BusTripDetailsRequest {

    /**
     * tripId : tripId
     * sourceId : sourceId
     * destinationId : destinationId
     * journeyDate  : journeyDate
     * tripType  : tripType
     * userType  : userType
     * provider  : provider
     * travelOperator  : travelOperator
     * user  : user
     * returnDate  : returnDate
     *
     */

    private String tripId;
    private String sourceId;
    private String destinationId;
    private String journeyDate;
    private String tripType;
    private String userType;
    private String provider;
    private String travelOperator;
    private String user;
    private String returnDate;
    private String seatsAvailable;
    /**
     * source : delhi
     * destination : agra
     */

    private String source;
    private String destination;
    private String busType;
    
    /**
     * busType : 2+2
     * seatsAvailable : seatsAvailable
     */

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTravelOperator() {
        return travelOperator;
    }

    public void setTravelOperator(String travelOperator) {
        this.travelOperator = travelOperator;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

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

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }
}
