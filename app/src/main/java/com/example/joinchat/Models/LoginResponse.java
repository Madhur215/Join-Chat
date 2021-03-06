package com.example.joinchat.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    private String token;
    private String name;

    public LoginResponse(String token, String name) {
        this.token = token;
        this.name = name;

    }

    public String getToken() {
        return token;
    }

    public String getUserName(){
        return name;
    }
}
