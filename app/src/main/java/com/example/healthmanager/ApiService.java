package com.example.healthmanager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/receive-heart-rate")
    Call<Void> sendHeartRate(@Body HeartRateData heartRateData);
}


