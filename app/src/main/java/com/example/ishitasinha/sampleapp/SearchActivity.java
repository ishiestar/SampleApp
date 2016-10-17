package com.example.ishitasinha.sampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.adapters.ListAdapter;
import com.example.ishitasinha.sampleapp.utility.Paginator;
import com.quintype.core.Quintype;
import com.quintype.core.data.Callback;
import com.quintype.core.story.StorySearchResult;

public class SearchActivity extends AppCompatActivity {

    public static final String LOG_TAG = SearchActivity.class.getSimpleName();
    Activity activity = this;
    EditText searchEditText;
    RecyclerView resultsRecyclerView;
    ListAdapter adapter;
    String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        resultsRecyclerView = (RecyclerView) findViewById(R.id.rv_search_results);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void performSearch(View view) {
        searchTerm = searchEditText.getText().toString();
        if (searchTerm.equals("")) {
            Toast.makeText(this, "Please enter a term to search for.", Toast.LENGTH_SHORT).show();
            return;
        }
        Quintype.story()
                .getStoriesBySearch()
                .term(searchTerm)
                .limit(10)
                .execute(new Callback<StorySearchResult.Results>() {
            @Override
            public void onSuccess(StorySearchResult.Results results) {
                if( results.result().stories().size()>0) {
                    adapter = new ListAdapter(results.result().stories(), null);
                    resultsRecyclerView.setAdapter(adapter);
                    resultsRecyclerView.addOnScrollListener(new Paginator(activity, adapter, searchTerm));
                } else
                    Toast.makeText(getApplicationContext(), "No stories found. Please refine your search term.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v(LOG_TAG, "Failed! " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
