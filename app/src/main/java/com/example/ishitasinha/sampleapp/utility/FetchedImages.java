package com.example.ishitasinha.sampleapp.utility;

import android.os.Parcel;
import android.os.Parcelable;

import com.quintype.core.story.StoryElement;

import java.util.List;

/**
 * Created by ishitasinha on 10/05/16.
 */
public class FetchedImages implements Parcelable {

    public List<StoryElement> storyElements;

    public FetchedImages(List<StoryElement> storyElements) {
        this.storyElements = storyElements;
    }

    protected FetchedImages(Parcel in) {
        storyElements = in.createTypedArrayList(StoryElement.CREATOR);
    }

    public static final Creator<FetchedImages> CREATOR = new Creator<FetchedImages>() {
        @Override
        public FetchedImages createFromParcel(Parcel in) {
            return new FetchedImages(in);
        }

        @Override
        public FetchedImages[] newArray(int size) {
            return new FetchedImages[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(storyElements);
    }
}
