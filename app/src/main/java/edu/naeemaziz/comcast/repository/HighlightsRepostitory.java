package edu.naeemaziz.comcast.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.naeemaziz.comcast.networking.RetrofitClient;
import edu.naeemaziz.comcast.networking.RetrofitRequest;
import edu.naeemaziz.comcast.response.HiglightsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HighlightsRepostitory {

    RetrofitRequest apiRequest;

    public HighlightsRepostitory() {
        apiRequest = RetrofitClient.getRetrofitClient().create(RetrofitRequest.class);
    }

    public LiveData<HiglightsResponse> getHighlightsResponse() {

        MutableLiveData<HiglightsResponse> data = new MutableLiveData<>();

        apiRequest.getHighLights().enqueue(new Callback<HiglightsResponse>() {
            @Override
            public void onResponse(Call<HiglightsResponse> call, Response<HiglightsResponse> response) {
                if (response != null && response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HiglightsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
