package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Appointment implements Serializable {
    @SerializedName("AppointmentCode")
    private String appointmentCode;
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("DoctorName")
    private String doctorName;
    @SerializedName("PatientId")
    private int patientId;
    @SerializedName("PatientName")
    private String patientName;
    @SerializedName("TestPurpose")
    private String testPurpose;
    @SerializedName("Conclusion")
    private String conclusion;
    @SerializedName("Status")
    private String status;
    @SerializedName("SampleGettingDtos")
    private List<AppointmentDetail> listApptDetail;


    public List<AppointmentDetail> getListApptDetail() {
        return listApptDetail;
    }

    public void setListApptDetail(List<AppointmentDetail> listApptDetail) {
        this.listApptDetail = listApptDetail;
    }

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDoctorName() {
//        return doctorName;
        return "";
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTestPurpose() {
//        return testPurpose;
        return "";
    }

    public void setTestPurpose(String testPurpose) {
        this.testPurpose = testPurpose;
    }

    public String getConclusion() {
//        return conclusion;
        return "";
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
