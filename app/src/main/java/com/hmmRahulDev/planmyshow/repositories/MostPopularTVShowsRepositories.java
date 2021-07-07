package com.hmmRahulDev.planmyshow.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hmmRahulDev.planmyshow.network.ApiClient;
import com.hmmRahulDev.planmyshow.network.ApiService;
import com.hmmRahulDev.planmyshow.responses.TVShowsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepositories {
    private ApiService apiService;

    public MostPopularTVShowsRepositories() {
        //.create is used for enlisting all the functions in ApiService
        apiService = ApiClient.getRetrofit().create(ApiService.class);

    }

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page) {
        //Live data only send update to that activity which is in activeState(two types 1. Livedata 2. MutableLiveData)
        //MutableLiveData is both read/write but only LiveData is Only Read
        MutableLiveData<TVShowsResponse> data = new MutableLiveData<>();
        //we are calling the method of ApiService and it will be called using ApiClient
        // (that's why we are initialize APIService with ApiClient object)
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowsResponse> cal, @NonNull Response<TVShowsResponse> response) {
                //it will set the Data get from response(response -> return a object of type TVShowsResponse)
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
