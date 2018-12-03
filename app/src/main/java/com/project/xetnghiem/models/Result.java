package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("IndexName")
    private String indexName;
    @SerializedName("IndexValue")
    private String value;
    @SerializedName("LowNormalHigh")
    private String lowNormalHigh;
    @SerializedName("NormalRange")
    private String normalRange;
    @SerializedName("Unit")
    private String unit;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
