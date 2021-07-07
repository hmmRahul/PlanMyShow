package com.hmmRahulDev.planmyshow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hmmRahulDev.planmyshow.R;
import com.hmmRahulDev.planmyshow.adapters.TVShowsAdapter;
import com.hmmRahulDev.planmyshow.databinding.ActivityMainBinding;

import com.hmmRahulDev.planmyshow.listeners.TVShowsListener;
import com.hmmRahulDev.planmyshow.models.TVShow;

import com.hmmRahulDev.planmyshow.viewModels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();
    }

    public void doInitialization() {
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //it detects when you reach end of your recycler view
                if(!activityMainBinding.tvShowsRecyclerView.canScrollHorizontally(1))
                {
                    if(currentPage <= totalAvailablePages)
                    {
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });

        activityMainBinding.imagewatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WatchListActivity.class));
            }
        });

        activityMainBinding.imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
            }
        });

        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();
        //whenever there is any change in Data(Live data) observer will execute this following code in mainActivity (Livedata provides us facility
        // to execute any code when there is a change in data)
        //here are making new Observer changed to lambda function
        viewModel.getMostPopularTVShows(currentPage).observe(this,mostPopularTVShowResponse ->
                {
                    toggleLoading();
                    if (mostPopularTVShowResponse != null) {
                        totalAvailablePages = mostPopularTVShowResponse.getTotalPages();
                        if (mostPopularTVShowResponse.getTvShows() != null)
                        {
                            int oldCount = tvShows.size();
                            tvShows.addAll(mostPopularTVShowResponse.getTvShows());
                            tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                        }
                    }


                });
    }

    private void toggleLoading()
    {
        if(currentPage == 1)
        {
            if(activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading())
            {
                activityMainBinding.setIsLoading(false);
            }else
            {
                activityMainBinding.setIsLoading(true);
            }
        }else
        {
               if(activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore())
               {
                   activityMainBinding.setIsLoadingMore(false);
               }
               else
               {
                  activityMainBinding.setIsLoadingMore(true);
               }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
       intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}