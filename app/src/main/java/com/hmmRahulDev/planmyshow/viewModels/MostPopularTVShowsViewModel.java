package com.hmmRahulDev.planmyshow.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hmmRahulDev.planmyshow.repositories.MostPopularTVShowsRepositories;
import com.hmmRahulDev.planmyshow.responses.TVShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepositories mostPopularTVShowsRepositories;

    public MostPopularTVShowsViewModel()
    {
        mostPopularTVShowsRepositories = new MostPopularTVShowsRepositories();
    }
    public LiveData<TVShowsResponse> getMostPopularTVShows(int page)
    {
        return mostPopularTVShowsRepositories.getMostPopularTVShows(page);
    }

}
