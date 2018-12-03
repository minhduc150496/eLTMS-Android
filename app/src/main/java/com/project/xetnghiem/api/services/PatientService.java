package com.project.xetnghiem.api.services;

import com.project.xetnghiem.api.requestObj.UpdatePatientRequest;
import com.project.xetnghiem.api.responseObj.SuccessResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientService {
    @POST("api/patient/update")
Single<Response<SuccessResponse>> changePatientInfo(@Body UpdatePatientRequest request);
    @Multipart
    @POST("api/patient/changeAvatar")
    Single<Response<SuccessResponse>> changeAvatar(
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part id);
}
