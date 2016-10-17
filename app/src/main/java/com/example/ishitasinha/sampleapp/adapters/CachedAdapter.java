package com.example.ishitasinha.sampleapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.utility.StoryItems;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ishitasinha on 09/05/16.
 */
public class CachedAdapter extends RecyclerView.Adapter<CachedAdapter.DataHolder> {
    private Realm mRealm;
    private RealmResults<StoryItems> mResults;

    public CachedAdapter(Realm realm, RealmResults<StoryItems> results) {
        mRealm = realm;
        setResults(results);
    }

    public StoryItems getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mResults.get(position).getTimestamp();
    }
    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        StoryItems data = mResults.get(position);
        holder.headline.setText(data.getHeadline());
        holder.author.setText(data.getAuthor());
        Log.v(CachedAdapter.class.getSimpleName(), "Results: " + mResults.get(position).getHeadline());
    }

    public void setResults(RealmResults<StoryItems> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public static class DataHolder extends RecyclerView.ViewHolder {

        TextView headline, author;

        public DataHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            author = (TextView) itemView.findViewById(R.id.author);
        }
    }
}

