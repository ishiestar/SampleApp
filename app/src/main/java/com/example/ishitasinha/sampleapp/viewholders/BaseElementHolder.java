package com.example.ishitasinha.sampleapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.quintype.coreui.story.StoryElementBinder;

/**
 * An instance of BaseElementHolder to be used with {@link android.support.v7.widget.RecyclerView.Adapter}
 * <p/>
 * implements {@link StoryElementBinder}
 */
public abstract class BaseElementHolder extends RecyclerView.ViewHolder implements StoryElementBinder {

    public BaseElementHolder(View itemView) {
        super(itemView);
    }
}
