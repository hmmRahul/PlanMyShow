package com.hmmRahulDev.planmyshow.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hmmRahulDev.planmyshow.database.TVSHowsDatabase;
import com.hmmRahulDev.planmyshow.models.TVShow;
import com.hmmRahulDev.planmyshow.repositories.TVShowDetailsRepository;
import com.hmmRahulDev.planmyshow.responses.TVShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

//AndroidViewModel is used instead of ViewModel because it have Context with itself
//which can be used
public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;
    private TVSHowsDatabase tvShowsDatabase;

    public TVShowDetailsViewModel(@NonNull Application application)
    {
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
      tvShowsDatabase = TVSHowsDatabase.getTvsHowsDatabase(application);
    }

    public LiveData<TVShowDetailsResponse>getTvShowDetails(String tvShowId)
    {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchList(TVShow tvShow)
    {
        return tvShowsDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TVShow> getTVShowFromWatchList(String tvShowId)
    {
        return tvShowsDatabase.tvShowDao().getTVShowFromWatchList(tvShowId);
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow)
    {
        return tvShowsDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}
