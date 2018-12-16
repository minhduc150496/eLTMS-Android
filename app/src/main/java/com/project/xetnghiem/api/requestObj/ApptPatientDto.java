package com.project.xetnghiem.api.requestObj;

import com.google.gson.annotations.SerializedName;

public class ApptPatientDto {
    @SerializedName("FullName")
    private String name;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @SerializedName("PhoneNumber")
    private String phone;
    @SerializedName("HomeAddress")
    private String address;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
