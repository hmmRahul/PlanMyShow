package com.hmmRahulDev.planmyshow.listeners;

import com.hmmRahulDev.planmyshow.models.TVShow;

public interface WatchListListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchList(TVShow tvShow,int position);
}
