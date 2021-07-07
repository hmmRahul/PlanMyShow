package com.hmmRahulDev.planmyshow.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hmmRahulDev.planmyshow.database.TVSHowsDatabase;
import com.hmmRahulDev.planmyshow.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {

    private TVSHowsDatabase tvsHowsDatabase;

    public WatchListViewModel(@NonNull Application application)
    {
        super(application);
        tvsHowsDatabase = TVSHowsDatabase.getTvsHowsDatabase(application);

    }

    public Flowable<List<TVShow>> loadWatchList()
    {
        return tvsHowsDatabase.tvShowDao().getWatchList();
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow)
    {
        return  tvsHowsDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}
