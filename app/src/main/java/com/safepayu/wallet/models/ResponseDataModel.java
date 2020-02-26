package com.safepayu.wallet.models;

import com.safepayu.wallet.models.response.AllListData;

import java.util.List;

public class ResponseDataModel {

    public String balance_amount;
    public String investment_interest;
    public String fb_interest;
    public String fdbal_amount;
    public String wallet_amount;
    public double investment_total;

    public String name;
    public String mobile;
    public String fdReferId;
    public String image;
    public String amount;
    public String bonus_amount;
    public int showprofile;

    public List<AllListData> bank;
    public List<String> instruction;
    public String tnc;
}
