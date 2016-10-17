package com.example.ishitasinha.sampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.adapters.ListAdapter;
import com.example.ishitasinha.sampleapp.utility.Paginator;
import com.quintype.core.Quintype;
import com.quintype.core.data.Callback;
import com.quintype.core.story.Story;

import java.util.List;

public class StoriesByTagActivity extends AppCompatActivity {

    String tagName;
    RecyclerView recyclerView;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_details);

        tagName = getIntent().getStringExtra("TAG");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Stories tagged by \"" + tagName + "\"");

        recyclerView = (RecyclerView) findViewById(R.id.section_stories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Quintype.story()
                .getStories()
                .limit(10)
                .tag(tagName)
                .execute(new Callback<List<Story>>() {
                    @Override
                    public void onSuccess(List<Story> storyList) {
                        if (storyList.size() > 0) {
                            ListAdapter adapter = new ListAdapter(storyList, null);
                            recyclerView.setAdapter(adapter);
                            recyclerView.addOnScrollListener(new Paginator(activity, adapter, tagName));
                        } else
                            Toast.makeText(getApplicationContext(), "There are no stories by this tag.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
