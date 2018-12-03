package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class LabTestingIndex {
    @SerializedName("LabTestingIndexId")
    private int LabTestingIndexId;
    @SerializedName("IndexName")
    private String indexName;
    @SerializedName("IndexValue")
    private String indexValue;
    @SerializedName("LowNormalHigh")
    private String lowNormalHigh;
    @SerializedName("NormalRange")
    private String normalRange;
    @SerializedName("Unit")
    private String unit;

    public int getLabTestingIndexId() {
        return LabTestingIndexId;
    }

    public void setLabTestingIndexId(int labTestingIndexId) {
        LabTestingIndexId = labTestingIndexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    public String getLowNormalHigh() {
        return lowNormalHigh;
    }

    public void setLowNormalHigh(String lowNormalHigh) {
        this.lowNormalHigh = lowNormalHigh;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
