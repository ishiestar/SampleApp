package com.example.ishitasinha.sampleapp.utility;

import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ishitasinha on 09/05/16.
 */
public class StoryItems extends RealmObject {

    @PrimaryKey
    String id;
    @Required
    String headline;
    String author;
    long timestamp;

    public StoryItems() {

    }
    public StoryItems(String id, String headline, String author) {
        this.author = author;
        this.headline = headline;
        this.id = id;
        this.timestamp = System.currentTimeMillis();
        Log.v(StoryItems.class.getSimpleName(), "updated: author=" + getAuthor() + ", headline=" + getHeadline());
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeadline() {
        return headline;
    }

    public String getId() {
        return id;
    }
}
