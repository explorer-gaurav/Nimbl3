package com.gauravsaluja.nimbl3.network.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class TokenBody {

    @SerializedName("grant_type")
    @Expose
    private String grantType;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    public TokenBody() {

    }

    public TokenBody(String grantType, String username, String password) {
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}