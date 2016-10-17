package com.example.ishitasinha.sampleapp.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.StoryElement;

public class ElementCustomHolder extends BaseElementHolder {

    TextView textView;

    public ElementCustomHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(StoryElement element) {
        textView.setText("ElementCustomHolder");
    }

    @Override
    public View getView() {
        return itemView;
    }

    @Override
    public boolean recreate() {
        return false;
    }

    public static ElementCustomHolder create(ViewGroup parent) {
        View view = com.quintype.coreui.util.ViewUtils.inflate(R.layout.qs_story_layout_text, parent);
        ElementCustomHolder customHolder = new ElementCustomHolder(view);
        customHolder.textView = (TextView) view.findViewById(R.id.qs_text);
        return customHolder;
    }
}
