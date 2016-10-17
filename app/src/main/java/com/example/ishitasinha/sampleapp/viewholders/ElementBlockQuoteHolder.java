package com.example.ishitasinha.sampleapp.viewholders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.util.ViewUtils;

public class ElementBlockQuoteHolder extends BaseElementTextHolder {

    public ElementBlockQuoteHolder(View itemView) {
        super(itemView);
    }

    public static ElementBlockQuoteHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_storylayout_block_quote, parent);

        ElementBlockQuoteHolder holder = new ElementBlockQuoteHolder(view);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {

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
