package com.project.xetnghiem.api.services;

import com.project.xetnghiem.api.requestObj.ApptUpdateRequest;
import com.project.xetnghiem.api.responseObj.ResponseMessage;
import com.project.xetnghiem.models.Appointment;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AppointmentService {

    @GET("api/appointment/get-appointments-by-patient-id")
    Single<Response<List<Appointment>>> getPatientAppointment(@Query("patientId") int patientId);
@GET("api/appointment/get-results-by-account-id?")
    Single<Response<List<Appointment>>> getAccountAppointment(@Query("accountId") int accountId);

    @GET("api/appointment/get-new-appointments-by-patient-id")
    Single<Response<List<Appointment>>> getNewApptByPatientId(@Query("patientId") int patientId);

    @GET("api/appointment/get-old-appointments-by-patient-id")
    Single<Response<List<Appointment>>> getOldApptByPatientId(@Query("patientId") int patientId);

    @DELETE("api/appointment/delete-appointment")
    Single<Response<ResponseMessage>> cancelAppointment(@Query("appointmentCode") String appointmentCode);

    @DELETE("api/appointment/delete-appointment")
    Single<Response<ResponseMessage>> deleteAppointment(@Query("appointmentId") int apptId);

    @PUT("api/appointment/update-appointment")
    Single<Response<ResponseMessage>> updateAppointment(@Body ApptUpdateRequest appointment);
}
