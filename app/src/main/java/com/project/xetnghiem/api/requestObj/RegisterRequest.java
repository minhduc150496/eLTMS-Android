package com.project.xetnghiem.api.requestObj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterRequest implements Serializable{
    //    @SerializedName("address")
//    private String address;
    @SerializedName("PhoneNumber")
    private String phone;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("Password")
    private String password;
    @SerializedName("Email")
    private String email;
    @SerializedName("AvatarURL")
    private String avatar;
    @SerializedName("IdentityCardNumber")
    private String identityCardNumber;

   public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }
}
