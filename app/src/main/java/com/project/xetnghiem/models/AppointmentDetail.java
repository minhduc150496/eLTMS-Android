package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AppointmentDetail implements Serializable {

    @SerializedName("SampleId")
    private int sampleId;
    @SerializedName("LabTestIds")
    private List<Integer> labTestIds;
    @SerializedName("SampleName")
    private String sampleName;
    @SerializedName("StartTime")
    private String startTime;
    @SerializedName("GettingDate")
    private String gettingDate;
    @SerializedName("FinishTime")
    private String finishTime;
    @SerializedName("SlotId")
    private int slotId;
    @SerializedName("TableId")
    private int tableId;
    @SerializedName("LabTests")
    private List<LabTest> labTests;
    @SerializedName("FmStartTime")
    private String fmStartTime;
    @SerializedName("FmFinishTime")
    private String fmFinishTime;
    private String appointmentCode;
    private boolean isHeader = false;

    public AppointmentDetail(){}
    public AppointmentDetail(boolean isHeader, String appointmentCode) {
        this.setHeader(isHeader);
        this.appointmentCode = appointmentCode;
    }


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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getGettingDate() {
        return gettingDate;
    }

    public void setGettingDate(String gettingDate) {
        this.gettingDate = gettingDate;
    }

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }

    public List<Integer> getLabTestIds() {
        return labTestIds;
    }

    public void setLabTestIds(List<Integer> labTestIds) {
        this.labTestIds = labTestIds;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public List<LabTest> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<LabTest> labTests) {
        this.labTests = labTests;
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
