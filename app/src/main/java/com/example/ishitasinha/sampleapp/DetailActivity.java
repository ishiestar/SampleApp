package com.example.ishitasinha.sampleapp;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.adapters.StoryViewPagerAdapter;
import com.example.ishitasinha.sampleapp.utility.FetchedImages;
import com.example.ishitasinha.sampleapp.utility.FetchedStories;
import com.quintype.core.story.Story;
import com.quintype.coreui.ImageLoader;
import com.quintype.coreui.TextLoader;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener {

    public static final String LOG_TAG = DetailActivity.class.getSimpleName();

    String storyId;
    int adapterPosition;
    ImageView toolbarImage;
    CollapsingToolbarLayout toolbarLayout;
    TextView captionTextView;
    ViewPager viewPager;
    StoryViewPagerAdapter adapter;
    List<Story> storyList;
    FetchedStories fetchedStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_detail_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storyId = getIntent().getStringExtra("ID");
        Log.v(LOG_TAG, storyId);
        adapterPosition = getIntent().getIntExtra("POSITION", 0);
        fetchedStories = getIntent().getParcelableExtra("STORY_LIST");
        for (Story story : fetchedStories.stories) {
            Log.d(LOG_TAG, "story = " + story.id());
        }
        storyList = fetchedStories.stories;
        toolbarImage = (ImageView) findViewById(R.id.hero_image_toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        captionTextView = (TextView) findViewById(R.id.hero_image_caption);
        adapter = new StoryViewPagerAdapter(getSupportFragmentManager(), storyList);

        viewPager = (ViewPager) findViewById(R.id.story_viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapterPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapterPosition = position;
                setHeroImage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setHeroImage();

        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vpCurrentItem = viewPager.getCurrentItem();
                DetailFragment currentFragment = (DetailFragment) adapter.getRegisteredFragment(vpCurrentItem);
                currentFragment.openGallery();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setHeroImage() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320.0f, getResources().getDisplayMetrics());
        ImageLoader imageLoader = ImageLoader.create();
        imageLoader
                .autoQuality()
                .quality(1f)
                .width(width)
                .height(height)
                .useFocalPoints(false)
                .using(storyList.get(adapterPosition))
                .into(toolbarImage);
        TextLoader.create()
                .linkColor(Color.parseColor("#00A1FF"))
                .linkUnderline(true)
                .text(storyList.get(adapterPosition).heroImageCaption())
                .into(captionTextView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(FetchedImages images) {
//        Log.v(LOG_TAG, "images.isEmpty(): " + (images == null));
//        if (currentImages == null) {
//            previousImages = images;
//            currentImages = images;
//        } else {
//            previousImages = currentImages;
//            this.currentImages = images;
//        }
//        this.images.add(images);
    }


}
