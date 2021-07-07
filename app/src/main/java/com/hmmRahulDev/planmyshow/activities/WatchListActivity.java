package com.hmmRahulDev.planmyshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hmmRahulDev.planmyshow.R;
import com.hmmRahulDev.planmyshow.adapters.WatchListAdapter;
import com.hmmRahulDev.planmyshow.databinding.ActivityWatchListBinding;
import com.hmmRahulDev.planmyshow.listeners.WatchListListener;
import com.hmmRahulDev.planmyshow.models.TVShow;
import com.hmmRahulDev.planmyshow.utilities.TempDataHolder;
import com.hmmRahulDev.planmyshow.viewModels.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchListListener {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel viewModel;
    private WatchListAdapter watchListAdapter;
    private List<TVShow> watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        activityWatchListBinding.imageBack.setOnClickListener(v -> onBackPressed());
        watchList = new ArrayList<>();
        loadWatchList();
    }

    private void loadWatchList() {
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    if(watchList.size() > 0)
                    {
                       watchList.clear();
                    }
                    watchList.addAll(tvShows);
                    watchListAdapter = new WatchListAdapter(watchList,this);
                    activityWatchListBinding.watchListRecyclerView.setAdapter(watchListAdapter);
                    activityWatchListBinding.watchListRecyclerView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TempDataHolder.IS_WATCHLIST_UPDATED)
        {
            loadWatchList();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
    Intent intent = new Intent(getApplicationContext(),TVShowDetailsActivity.class);
    intent.putExtra("tvShow",tvShow);
    startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchList(TVShow tvShow, int position) {
      CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
      compositeDisposableForDelete.add(viewModel.removeTVShowFromWatchList(tvShow)
      .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
      .subscribe(()->{
          watchList.remove(position);
          watchListAdapter.notifyItemRemoved(position);
          watchListAdapter.notifyItemRangeChanged(position,watchListAdapter.getItemCount());
          compositeDisposableForDelete.dispose();
      }));
    }
}