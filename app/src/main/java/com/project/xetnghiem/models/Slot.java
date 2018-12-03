package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

public class Slot {
    @SerializedName("SlotId")
    private int slotId;
    @SerializedName("SampleGroupId")
    private int sampleGroupId;
    @SerializedName("SampleId")
    private int sampleId;
    @SerializedName("StartTime")
    private int startTime;
    @SerializedName("FinishTime")
    private int finishTime;
    @SerializedName("FmStartTime")
    private String fmStartTime;
    @SerializedName("FmFinishTime")
    private String fmFinishTime;
    @SerializedName("Date")
    private String date;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("RemainQuantity")
    private int remainQuantity;

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getSampleGroupId() {
        return sampleGroupId;
    }

    public void setSampleGroupId(int sampleGroupId) {
        this.sampleGroupId = sampleGroupId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(int remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public String getFmStartTime() {
        return fmStartTime;
    }

    public void setFmStartTime(String fmStartTime) {
        this.fmStartTime = fmStartTime;
    }

    public String getFmFinishTime() {
        return fmFinishTime;
    }

    public void setFmFinishTime(String fmFinishTime) {
        this.fmFinishTime = fmFinishTime;
    }
}
