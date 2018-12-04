package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Patient {
    @SerializedName("id")
    private int id;
    @SerializedName("FullName")
    private String name;
//    @SerializedName("address")
//    private String address;
    @SerializedName("PhoneNumber")
    private String phone;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @SerializedName("gender")
    private String gender;
    @SerializedName("AvatarURL")
    private String avatar;
    @SerializedName("Email")
    private String email;
    @SerializedName("IdentityCardNumber")
    private String identityCardNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return "http://eltms.azurewebsites.net" + avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }
}
