package com.example.ishitasinha.sampleapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.example.ishitasinha.sampleapp.adapters.GalleryAdapter;
import com.example.ishitasinha.sampleapp.utility.FetchedImages;
import com.quintype.core.story.StoryElement;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class ImageSliderActivity extends AppCompatActivity {

    ViewPager viewPager;
    CircleIndicator indicator;
    int position;
    FetchedImages fetchedImages;
    List<StoryElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        position = getIntent().getIntExtra("POSITION", 0);
        fetchedImages = getIntent().getParcelableExtra("ELEMENTS");
        elements = fetchedImages.storyElements;

        viewPager = (ViewPager) findViewById(R.id.image_slider);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        GalleryAdapter adapter = new GalleryAdapter(elements);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        indicator.setViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Log.e(ImageSliderActivity.class.getSimpleName(), e.getMessage());
            return false;
        }
    }
}
