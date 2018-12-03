package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class SampleGettingNurse {
    @SerializedName("PatientName")
    private String patientName;
    @SerializedName("SampleGettingCode")
    private int sampleGettingCode;
    @SerializedName("SampleGettingId")
    private int sampleGettingId;
    @SerializedName("SampleName")
    private String sampleName;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getSampleGettingCode() {
        return sampleGettingCode;
    }

    public void setSampleGettingCode(int sampleGettingCode) {
        this.sampleGettingCode = sampleGettingCode;
    }

    public int getSampleGettingId() {
        return sampleGettingId;
    }

    public void setSampleGettingId(int sampleGettingId) {
        this.sampleGettingId = sampleGettingId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }
}
