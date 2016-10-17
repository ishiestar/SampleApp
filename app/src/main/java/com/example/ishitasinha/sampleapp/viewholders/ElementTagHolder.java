package com.example.ishitasinha.sampleapp.viewholders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.StoriesByTagActivity;
import com.quintype.core.story.Story;
import com.quintype.core.story.StoryElement;
import com.quintype.core.story.Tag;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by ishitasinha on 03/05/16.
 */
public class ElementTagHolder extends BaseElementHolder {

    TagContainerLayout tagContainer;
    List<String> tags = new ArrayList<>();

    public ElementTagHolder(View itemView, Story story) {
        super(itemView);
        List<Tag> storyTags = story.tags();
        for (Tag tag : storyTags) {
            tags.add(tag.name());
        }
    }

    public static ElementTagHolder create(ViewGroup parent, Story story) {
        final Context context = parent.getContext();
        View view = com.quintype.coreui.util.ViewUtils.inflate(R.layout.story_layout_tag, parent);
        ElementTagHolder holder = new ElementTagHolder(view, story);
        holder.tagContainer = (TagContainerLayout) view.findViewById(R.id.tag_container);
        holder.tagContainer.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(context, StoriesByTagActivity.class);
                intent.putExtra("TAG", text);
                context.startActivity(intent);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }
        });
        return holder;
    }

    @Override
    public void bind(StoryElement element) {
        tagContainer.setTags(tags);
    }

    @Override
    public View getView() {
        return itemView;
    }

    @Override
    public boolean recreate() {
        return false;
    }
}
