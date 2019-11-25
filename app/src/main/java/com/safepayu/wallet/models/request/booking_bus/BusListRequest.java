package com.safepayu.wallet.models.request.booking_bus;

public class BusListRequest {

    /**
     * sourceId : 100
     * destinationId : 109
     * JourneyDate : 01-01-2020
     * tripType : 1
     * returnDate : 03-01-2020
     */

    private String sourceId;
    private String destinationId;
    private String JourneyDate;
    private String tripType;
    private String returnDate;

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
        return JourneyDate;
    }

    public void setJourneyDate(String JourneyDate) {
        this.JourneyDate = JourneyDate;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
