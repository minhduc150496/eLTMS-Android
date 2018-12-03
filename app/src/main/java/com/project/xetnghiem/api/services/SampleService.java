package com.project.xetnghiem.api.services;

import com.project.xetnghiem.api.responseObj.ResponseMessage;
import com.project.xetnghiem.models.SampleDto;
import com.project.xetnghiem.models.SampleGettingNurse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SampleService {
    @GET("api/sample/get-all")
    Single<Response<List<SampleDto>>> getAllSample();

    @GET("api/sample-getting/get-by-code-for-nurse")
    Single<Response<SampleGettingNurse>> searchSample(@Query("code") String code);
    @PUT("api/sample-getting/update-status-nurse-done")
    Single<Response<ResponseMessage>> updateStatus(@Query("sampleGettingId") int id);
}
