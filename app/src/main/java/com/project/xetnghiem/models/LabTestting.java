package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LabTestting {
    @SerializedName("LabTestingId")
    private int labTestingId;
    @SerializedName("LabTestName")
    private String labTestName;
    @SerializedName("LabTestingIndexes")
    private List<LabTestingIndex> listLabTestingIndex;

    public int getLabTestingId() {
        return labTestingId;
    }

    public void setLabTestingId(int labTestingId) {
        this.labTestingId = labTestingId;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public List<LabTestingIndex> getListLabTestingIndex() {
        return listLabTestingIndex;
    }

    public void setListLabTestingIndex(List<LabTestingIndex> listLabTestingIndex) {
        this.listLabTestingIndex = listLabTestingIndex;
    }
}
