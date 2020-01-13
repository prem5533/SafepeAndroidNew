package com.safepayu.wallet.models.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse{
    @SerializedName("access_token")
    String accessToken;
    @SerializedName("expires_in")
    String tokenExpiresIn;
    @SerializedName("userid")
    String userId;

    private String remember_me;
    private String token;


    public LoginResponse() {
    }

    public LoginResponse(String accessToken, String tokenExpiresIn, String userId) {
        this.accessToken = accessToken;
        this.tokenExpiresIn = tokenExpiresIn;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenExpiresIn() {
        return tokenExpiresIn;
    }

    public void setTokenExpiresIn(String tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemember_me() {
        return remember_me;
    }

    public void setRemember_me(String remember_me) {
        this.remember_me = remember_me;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
