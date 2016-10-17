package com.example.ishitasinha.sampleapp.viewholders;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.Story;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.TextLoader;
import com.quintype.coreui.util.ViewUtils;

public class ElementBigFactHolder extends BaseElementTextHolder {

    ImageView imageBg;
    TextView textContent;
    TextView textAttribution;

    public ElementBigFactHolder(View itemView) {
        super(itemView);
    }

    public static ElementBigFactHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_big_fact, parent);
        ElementBigFactHolder holder = new ElementBigFactHolder(view);
        holder.textContent = (TextView) view.findViewById(R.id.qs_text_content);
        holder.textAttribution = (TextView) view.findViewById(R.id.qs_text_attribution);
        holder.imageBg = (ImageView) view.findViewById(R.id.qs_image_i_bg);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {

        textLoader
                .text(element.subTypeMeta().content())
                .urlClickListener(new TextLoader.UrlClickListener() {
                    @Override
                    public void onUrlClicked(View widget, Uri urlClicked, Story slugStory) {

                    }
                })
                .into(textContent);

        textLoader.text(element.subTypeMeta().attribution())
                .urlClickListener(new TextLoader.UrlClickListener() {
                    @Override
                    public void onUrlClicked(View widget, Uri urlClicked, Story slugStory) {

                    }
                })
                .into(textAttribution);

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
