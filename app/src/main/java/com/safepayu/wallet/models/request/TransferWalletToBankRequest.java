package com.safepayu.wallet.models.request;

public class TransferWalletToBankRequest {


    /**
     * beneId : 56489643
     * amount : 2000.00
     */

    private String beneId;
    private String amount;

    public String getBeneId() {
        return beneId;
    }

    public void setBeneId(String beneId) {
        this.beneId = beneId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
