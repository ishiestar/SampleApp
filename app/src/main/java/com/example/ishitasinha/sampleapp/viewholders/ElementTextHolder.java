package com.example.ishitasinha.sampleapp.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.util.ViewUtils;

/**
 * An object instance for ElementTextHolder used to load text Story Element.
 */
public class ElementTextHolder extends BaseElementTextHolder {

    TextView textView;

    ElementTextHolder(View itemView) {
        super(itemView);
    }

    /**
     * Static method to get TextHolder
     *
     * @param parent {@link ViewGroup}
     * @return {@link ElementTextHolder}
     */
    public static ElementTextHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_text, parent);
        ElementTextHolder holder = new ElementTextHolder(view);
        holder.textView = (TextView) view.findViewById(R.id.qs_text);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {
        textLoader.text(element.text())
                .into(textView);
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
