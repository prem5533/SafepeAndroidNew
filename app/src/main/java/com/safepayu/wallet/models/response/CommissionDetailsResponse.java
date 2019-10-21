package com.safepayu.wallet.models.response;

public class CommissionDetailsResponse {


    /**
     * status : true
     * message : Commission Business Details
     * totleftbusiness : 365000
     * totrightbusiness : 3695000
     * totmatchtbusiness : 365000
     * totdirectbusiness : 60000
     * totmatchincome : 0
     * totbinaryincome : 900.00
     * totdirectincome : 6000.00
     * totincome : 6900
     * totwithdrawwallet : 4370
     * totcurrentcommwallet : 342830
     */

    private boolean status;
    private String message;
    private int totleftbusiness;
    private int totrightbusiness;
    private int totmatchtbusiness;
    private int totdirectbusiness;
    private int totmatchincome;
    private String totbinaryincome;
    private String totdirectincome;
    private int totincome;
    private int totwithdrawwallet;
    private int totcurrentcommwallet;

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

    public int getTotleftbusiness() {
        return totleftbusiness;
    }

    public void setTotleftbusiness(int totleftbusiness) {
        this.totleftbusiness = totleftbusiness;
    }

    public int getTotrightbusiness() {
        return totrightbusiness;
    }

    public void setTotrightbusiness(int totrightbusiness) {
        this.totrightbusiness = totrightbusiness;
    }

    public int getTotmatchtbusiness() {
        return totmatchtbusiness;
    }

    public void setTotmatchtbusiness(int totmatchtbusiness) {
        this.totmatchtbusiness = totmatchtbusiness;
    }

    public int getTotdirectbusiness() {
        return totdirectbusiness;
    }

    public void setTotdirectbusiness(int totdirectbusiness) {
        this.totdirectbusiness = totdirectbusiness;
    }

    public int getTotmatchincome() {
        return totmatchincome;
    }

    public void setTotmatchincome(int totmatchincome) {
        this.totmatchincome = totmatchincome;
    }

    public String getTotbinaryincome() {
        return totbinaryincome;
    }

    public void setTotbinaryincome(String totbinaryincome) {
        this.totbinaryincome = totbinaryincome;
    }

    public String getTotdirectincome() {
        return totdirectincome;
    }

    public void setTotdirectincome(String totdirectincome) {
        this.totdirectincome = totdirectincome;
    }

    public int getTotincome() {
        return totincome;
    }

    public void setTotincome(int totincome) {
        this.totincome = totincome;
    }

    public int getTotwithdrawwallet() {
        return totwithdrawwallet;
    }

    public void setTotwithdrawwallet(int totwithdrawwallet) {
        this.totwithdrawwallet = totwithdrawwallet;
    }

    public int getTotcurrentcommwallet() {
        return totcurrentcommwallet;
    }

    public void setTotcurrentcommwallet(int totcurrentcommwallet) {
        this.totcurrentcommwallet = totcurrentcommwallet;
    }
}
