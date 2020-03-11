package com.safepayu.wallet.ecommerce.model.request;

public class SendMessageToVendorRequest {

    /**
     * custtype : app
     * orderid : 562
     * venueid : 201911011148462
     * from : cust
     * to : venue
     * message : Hello
     */

    private String custtype;
    private String orderid;
    private String venueid;
    private String from;
    private String to;
    private String message;

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getVenueid() {
        return venueid;
    }

    public void setVenueid(String venueid) {
        this.venueid = venueid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
