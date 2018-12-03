package com.project.xetnghiem.models;

import com.google.gson.annotations.SerializedName;

import java.security.KeyStore;
import java.util.List;

public class ResultOfAppointmentDto {
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("AppointmentCode")
    private String appointmentCode;
    @SerializedName("IsEmergency")
    private boolean isEmergency;
    @SerializedName("DoctorName")
    private String doctorName;
    @SerializedName("PatientName")
    private String patientName;
    @SerializedName("PatientBirthYear")
    private String patientBirthYear;
    @SerializedName("PatientGender")
    private String patientGender;
    @SerializedName("PatientAddress")
    private String patientAddress;
    @SerializedName("TestPurpose")
    private String testPurpose;
    @SerializedName("EnterTime")
    private String enterTime;
    @SerializedName("ReturnTime")
    private String returnTime;
    @SerializedName("Conclusion")
    private String conclusion;
    @SerializedName("SampleGettings")
    private List<SampleGetting> listSampleGetting;


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentCode() {
        return appointmentCode;
    }

    public void setAppointmentCode(String appointmentCode) {
        this.appointmentCode = appointmentCode;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBirthYear() {
        return patientBirthYear;
    }

    public void setPatientBirthYear(String patientBirthYear) {
        this.patientBirthYear = patientBirthYear;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getTestPurpose() {
        return testPurpose;
    }

    public void setTestPurpose(String testPurpose) {
        this.testPurpose = testPurpose;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public List<SampleGetting> getListSampleGetting() {
        return listSampleGetting;
    }

    public void setListSampleGetting(List<SampleGetting> listSampleGetting) {
        this.listSampleGetting = listSampleGetting;
    }
}
