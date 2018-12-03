package com.project.xetnghiem.api.services;

import com.project.xetnghiem.models.Slot;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface SlotService {
    @GET("api/slot/get-available-slots")
    Single<Response<List<Slot>>>  getAvaiableSlots();
}
