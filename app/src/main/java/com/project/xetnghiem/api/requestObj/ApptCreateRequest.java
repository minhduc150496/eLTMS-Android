package com.project.xetnghiem.api.requestObj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ApptCreateRequest implements Serializable {

    @SerializedName("PatientId")
    private int patientId;

    @SerializedName("isOnl")
    private boolean isOn = true;
//    @SerializedName("GettingDate")
//    private String gettingDate;//2018-11-04
    @SerializedName("SampleGettingDtos")
    private List<SampleGettingDtos> list;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public List<SampleGettingDtos> getList() {
        return list;
    }

    public void setList(List<SampleGettingDtos> list) {
        this.list = list;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

//    public String getGettingDate() {
//        return gettingDate;
//    }
//
//    public void setGettingDate(String gettingDate) {
//        this.gettingDate = gettingDate;
//    }

    public static class SampleGettingDtos {
        @SerializedName("SampleId")
        private int sampleId;
        @SerializedName("LabTestIds")
        private List<Integer> labTestIds;
        @SerializedName("GettingDate")
        private String getttingDate;
        @SerializedName("SlotId")
        private  int slotId;
//        @SerializedName("StartTime")
//        private String startTime;
//        @SerializedName("FinishTime")
//        private String finishTime;

        public int getSampleId() {
            return sampleId;
        }

        public void setSampleId(int sampleId) {
            this.sampleId = sampleId;
        }

        public List<Integer> getLabTestIds() {
            return labTestIds;
        }

        public void setLabTestIds(List<Integer> labTestIds) {
            this.labTestIds = labTestIds;
        }

//        public String getStartTime() {
//            return startTime;
//        }
//
//        public void setStartTime(String startTime) {
//            this.startTime = startTime;
//        }
//
//        public String getFinishTime() {
//            return finishTime;
//        }
//
//        public void setFinishTime(String finishTime) {
//            this.finishTime = finishTime;
//        }

        public int getSlotId() {
            return slotId;
        }

        public void setSlotId(int slotId) {
            this.slotId = slotId;
        }

        public String getGetttingDate() {
            return getttingDate;
        }

        public void setGetttingDate(String getttingDate) {
            this.getttingDate = getttingDate;
        }
    }
}
