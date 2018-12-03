package com.project.xetnghiem.api.requestObj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApptUpdateRequest {

    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("GettingDate")
    private String gettingDate;//2018-11-04
    @SerializedName("SampleGettingDtos")
    private List<ApptCreateRequest.SampleGettingDtos> list;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getGettingDate() {
        return gettingDate;
    }

    public void setGettingDate(String gettingDate) {
        this.gettingDate = gettingDate;
    }

    public List<ApptCreateRequest.SampleGettingDtos> getList() {
        return list;
    }

    public void setList(List<ApptCreateRequest.SampleGettingDtos> list) {
        this.list = list;
    }
}
