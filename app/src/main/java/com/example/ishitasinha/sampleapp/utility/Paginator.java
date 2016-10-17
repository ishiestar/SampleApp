package com.example.ishitasinha.sampleapp.utility;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.MainActivity;
import com.example.ishitasinha.sampleapp.SearchActivity;
import com.example.ishitasinha.sampleapp.SectionDetailsActivity;
import com.example.ishitasinha.sampleapp.StoriesByTagActivity;
import com.example.ishitasinha.sampleapp.adapters.ListAdapter;
import com.quintype.core.Quintype;
import com.quintype.core.data.Callback;
import com.quintype.core.story.Story;
import com.quintype.core.story.StorySearchResult;

import java.util.List;

/**
 * Created by ishitasinha on 03/05/16.
 */
public class Paginator extends RecyclerView.OnScrollListener {
    Activity callingActivity;
    private int previousTotal = 0,
            firstVisibleItem,
            visibleItemCount,
            totalItemCount;
    private boolean isLoading = true;
    private int visibleThreshold = 10;
    ListAdapter rvAdapter;
    String searchTerm;

    public Paginator(Activity activity, ListAdapter adapter, String searchTerm) {
        Log.v("Paginator", "Activity instance of MainActivity: " + (activity instanceof MainActivity));
        callingActivity = activity;
        rvAdapter = adapter;
        this.searchTerm = searchTerm;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = recyclerView.getLayoutManager().getItemCount();
        firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        final Context context = recyclerView.getContext();
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            if (callingActivity instanceof MainActivity)
                Quintype.story()
                        .getStories()
                        .offset(firstVisibleItem + visibleThreshold)
                        .limit(10)
                        .execute(new Callback<List<Story>>() {
                            @Override
                            public void onSuccess(List<Story> stories) {
                                rvAdapter.addAll(stories);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

            if (callingActivity instanceof SearchActivity)
                Quintype.story()
                        .getStoriesBySearch()
                        .term(searchTerm)
                        .limit(10)
                        .offset(firstVisibleItem + visibleThreshold)
                        .execute(new Callback<StorySearchResult.Results>() {
                            @Override
                            public void onSuccess(StorySearchResult.Results results) {
                                if (results.result().stories().size() > 0)
                                    rvAdapter.addAll(results.result().stories());
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

            if (callingActivity instanceof SectionDetailsActivity)
                Quintype.story()
                        .getStories()
                        .section(searchTerm)
                        .offset(firstVisibleItem + visibleThreshold)
                        .limit(10)
                        .execute(new Callback<List<Story>>() {
                            @Override
                            public void onSuccess(List<Story> storyList) {
                                if (storyList.size() > 0)
                                    rvAdapter.addAll(storyList);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });
            if(callingActivity instanceof StoriesByTagActivity)
                Quintype.story()
                        .getStories()
                        .tag(searchTerm)
                        .offset(firstVisibleItem + visibleThreshold)
                        .limit(10)
                        .execute(new Callback<List<Story>>() {
                            @Override
                            public void onSuccess(List<Story> storyList) {
                                if (storyList.size() > 0)
                                    rvAdapter.addAll(storyList);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

            isLoading = true;
        }
    }
}
