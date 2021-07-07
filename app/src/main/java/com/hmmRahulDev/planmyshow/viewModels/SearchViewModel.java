package com.hmmRahulDev.planmyshow.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hmmRahulDev.planmyshow.repositories.SearchTVShowRepository;
import com.hmmRahulDev.planmyshow.responses.TVShowsResponse;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel() {
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowsResponse> searchTVShow(String query, int page)
    {
        return searchTVShowRepository.searchTVShow(query,page);
    }

}
