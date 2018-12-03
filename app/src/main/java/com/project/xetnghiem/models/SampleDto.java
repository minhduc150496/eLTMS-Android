package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SampleDto {
    @SerializedName("SampleId")
    private int sampleId;
    @SerializedName("SampleGroupId")
    private int sampleGroupId;
    @SerializedName("SampleName")
    private String sampleName;
    @SerializedName("LabTests")
    private List<LabTest> labTests;
    @SerializedName("SampleDuration")
    private int sampleDuration;
    @SerializedName("OpenTime")
    private float openTime;
    @SerializedName("CloseTime")
    private float closeTime;
    @SerializedName("Description")
    private String description;
    @SerializedName("SlotDtos")
    private List<Slot> slotDtos;
    @SerializedName("SlotId")
    private int selectedSlotId;

    private String timeStr;
    private String dateStr;

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public List<LabTest> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<LabTest> labTests) {
        this.labTests = labTests;
    }

    public int getSampleDuration() {
        return sampleDuration;
    }

    public void setSampleDuration(int sampleDuration) {
        this.sampleDuration = sampleDuration;
    }

    public float getOpenTime() {
        return openTime;
    }

    public void setOpenTime(float openTime) {
        this.openTime = openTime;
    }

    public float getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(float closeTime) {
        this.closeTime = closeTime;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getSampleGroupId() {
        return sampleGroupId;
    }

    public void setSampleGroupId(int sampleGroupId) {
        this.sampleGroupId = sampleGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Slot> getSlotDtos() {
        return slotDtos;
    }

    public void setSlotDtos(List<Slot> slotDtos) {
        this.slotDtos = slotDtos;
    }

    public int getSelectedSlotId() {
        return selectedSlotId;
    }

    public void setSelectedSlotId(int selectedSlotId) {
        this.selectedSlotId = selectedSlotId;
    }
}
