package com.example.ishitasinha.sampleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.adapters.CachedAdapter;
import com.example.ishitasinha.sampleapp.adapters.ListAdapter;
import com.example.ishitasinha.sampleapp.utility.Paginator;
import com.example.ishitasinha.sampleapp.utility.StoryItems;
import com.quintype.core.Quintype;
import com.quintype.core.QuintypeConfig;
import com.quintype.core.data.Callback;
import com.quintype.core.story.Story;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    LinearLayoutManager manager;
    RecyclerView recyclerView;
    ListAdapter rvAdapter;
    SwipeRefreshLayout refreshLayout;
    Activity activity = this;
    RealmConfiguration configuration;
    Realm realm;
    CachedAdapter adapter;

    Quintype.StatusEvent event = new Quintype.StatusEvent() {
        @Override
        public void onStatusChanged(int status) {
            switch (status) {
                case Quintype.CONNECTED:
                    Log.v(LOG_TAG, "connected!");
                    fetchData();
                    break;
                case Quintype.CONNECTING:
                    Log.v(LOG_TAG, "connecting...");
                    break;
                case Quintype.DISCONNECTED:
                    Log.v(LOG_TAG, "disconnected!");
                    break;
                default:
                    Log.v(LOG_TAG, "invalid status!");
                    break;
            }
        }

        @Override
        public void onFailed(Throwable throwable) {
            Log.e(LOG_TAG, throwable.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configuration = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(configuration);
        recyclerView = (RecyclerView) findViewById(R.id.all_stories_list);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_widget);
        refreshLayout.setOnRefreshListener(this);
        if (isNetworkAvailable()) {
            Quintype.subscribeEvent(event);
            QuintypeConfig config = QuintypeConfig.builder(getApplicationContext())
                    .baseUrl(getString(R.string.qs_base_url))
                    .build();
            Quintype.init(config);
        } else {
            showCachedData();
            Log.d(LOG_TAG, "No network available!");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    public void launchSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void fetchData() {
        refreshLayout.setRefreshing(true);
        Quintype.story().getStories().offset(0).limit(10).execute(new Callback<List<Story>>() {
            @Override
            public void onSuccess(List<Story> stories) {
                rvAdapter = new ListAdapter(stories, realm);
                recyclerView.setAdapter(rvAdapter);
                recyclerView.addOnScrollListener(new Paginator(activity, rvAdapter, null));
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void showCachedData() {
//Asynchronous query
        RealmResults<StoryItems> mResults = realm.where(StoryItems.class).findAll();

        if (mResults.isEmpty()) {
            Log.v(LOG_TAG, "mResults is empty.");
        } else {
            Log.v(LOG_TAG, "mResults is not empty.");
        }

        adapter = new CachedAdapter(realm, mResults);
        //Tell me when the results are loaded so that I can tell my Adapter to update what it shows
        mResults.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "onChange triggered", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sections) {
            Intent intent = new Intent(this, SectionsActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Quintype.unsubscribeEvent(event);
    }
}
