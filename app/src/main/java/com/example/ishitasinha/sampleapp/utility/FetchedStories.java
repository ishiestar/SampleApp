package com.example.ishitasinha.sampleapp.utility;

import android.os.Parcel;
import android.os.Parcelable;

import com.quintype.core.story.Story;

import java.util.List;

/**
 * Created by ishitasinha on 02/05/16.
 */
public class FetchedStories implements Parcelable {

    public List<Story> stories;

    public FetchedStories(List<Story> stories) {
        this.stories = stories;
    }

    protected FetchedStories(Parcel in) {
        stories = in.createTypedArrayList(Story.CREATOR);
    }

    public static final Creator<FetchedStories> CREATOR = new Creator<FetchedStories>() {
        @Override
        public FetchedStories createFromParcel(Parcel in) {
            return new FetchedStories(in);
        }

        @Override
        public FetchedStories[] newArray(int size) {
            return new FetchedStories[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeTypedList(stories);
    }
}
