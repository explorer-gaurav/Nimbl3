package com.gauravsaluja.nimbl3.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class Token implements Parcelable {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private Integer expiresIn;
    @SerializedName("created_at")
    private Integer createdAt;

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeString(this.tokenType);
        dest.writeValue(this.expiresIn);
        dest.writeValue(this.createdAt);
    }

    public Token() {

    }

    public Token(String accessToken, String tokenType, Integer expiresIn, Integer createdAt) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    protected Token(Parcel in) {
        this.accessToken = in.readString();
        this.tokenType = in.readString();
        this.expiresIn = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdAt = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel source) {
            return new Token(source);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}