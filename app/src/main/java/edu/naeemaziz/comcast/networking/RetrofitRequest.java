package edu.naeemaziz.comcast.networking;

import edu.naeemaziz.comcast.response.HiglightsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitRequest {

    @GET("interview/demoapp.json")
    Call<HiglightsResponse> getHighLights();
}
