package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SampleGetting {
    @SerializedName("SampleGettingId")
    private int SampleGettingId;
    @SerializedName("SampleName")
    private String sampleName;
    @SerializedName("LabTestings")
    private List<LabTestting> listLabTesting;

    public int getSampleGettingId() {
        return SampleGettingId;
    }

    public void setSampleGettingId(int sampleGettingId) {
        SampleGettingId = sampleGettingId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public List<LabTestting> getListLabTesting() {
        return listLabTesting;
    }

    public void setListLabTesting(List<LabTestting> listLabTesting) {
        this.listLabTesting = listLabTesting;
    }
}
