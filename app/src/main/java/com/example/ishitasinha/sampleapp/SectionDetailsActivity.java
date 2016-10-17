package com.example.ishitasinha.sampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.adapters.ListAdapter;
import com.example.ishitasinha.sampleapp.utility.Paginator;
import com.quintype.core.Quintype;
import com.quintype.core.data.Callback;
import com.quintype.core.story.Story;

import java.util.List;

public class SectionDetailsActivity extends AppCompatActivity {

    String section;
    RecyclerView sectionStories;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_details);

        section = getIntent().getStringExtra("SECTION");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(section);

        sectionStories = (RecyclerView) findViewById(R.id.section_stories);
        sectionStories.setLayoutManager(new LinearLayoutManager(this));

        Quintype.story()
                .getStories()
                .section(section)
                .limit(10)
                .execute(new Callback<List<Story>>() {
                    @Override
                    public void onSuccess(List<Story> storyList) {
                        if (storyList.size() > 0) {
                            ListAdapter adapter = new ListAdapter(storyList, null);
                            sectionStories.setAdapter(adapter);
                            sectionStories.addOnScrollListener(new Paginator(activity, adapter, section));
                        } else
                            Toast.makeText(getApplicationContext(), "There are no stories in this section", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
