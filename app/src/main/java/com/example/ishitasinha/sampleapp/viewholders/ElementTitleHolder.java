package com.example.ishitasinha.sampleapp.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.util.ViewUtils;

/**
 * An object instance of ElementTitleHolder to load title Story element.
 */
public class ElementTitleHolder extends BaseElementTextHolder {

    TextView textTitle;

    public ElementTitleHolder(View itemView) {
        super(itemView);
    }

    /**
     * Static method to create TitleHolder
     *
     * @param parent {@link ViewGroup}
     * @return {@link ElementTitleHolder}
     */
    public static ElementTitleHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_title, parent);
        ElementTitleHolder holder = new ElementTitleHolder(view);
        holder.textTitle = (TextView) view.findViewById(R.id.qs_text_title);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {
        textLoader
                .text(element.text())
                .into(textTitle);
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
