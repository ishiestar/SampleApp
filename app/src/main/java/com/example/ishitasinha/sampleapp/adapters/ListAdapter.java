package com.example.ishitasinha.sampleapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.DetailActivity;
import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.utility.FetchedStories;
import com.example.ishitasinha.sampleapp.utility.StoryItems;
import com.quintype.core.story.Story;
import com.quintype.coreui.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by ishitasinha on 22/04/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    List<Story> stories = new ArrayList<>();
    Context context;
    Realm realm;

    public ListAdapter(List<Story> stories, Realm realm) {
        this.stories.addAll(stories);
        this.realm = realm;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.headline.setText(story.headline().trim());
        holder.author.setText(story.authorName().trim());
        ImageLoader imageLoader = ImageLoader.create();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100.0f, context.getResources().getDisplayMetrics());
        if (story.heroImageS3Key() != null)
            imageLoader
                    .autoQuality()
                    .quality(0.5f)
                    .height(px)
                    .width(px)
                    .useFocalPoints(true)
                    .using(story)
                    .into(holder.image);
        if (realm != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(new StoryItems(story.id(), story.headline(), story.authorName()));
            realm.commitTransaction();
        }
    }

    public void addAll(List<Story> newItems) {
        stories.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView
                author,
                headline;

        public ListViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            headline = (TextView) itemView.findViewById(R.id.headline);
            image = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Story selectedStory = stories.get(adapterPosition);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("ID", selectedStory.id());
            intent.putExtra("POSITION", adapterPosition);
            intent.putExtra("STORY_LIST", new FetchedStories(stories));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, image, image.getTransitionName());
                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }
        }
    }
}
