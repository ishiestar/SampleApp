package com.example.ishitasinha.sampleapp.viewholders;

import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.Story;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.TextLoader;
import com.quintype.coreui.util.ViewUtils;

public class ElementQuoteHolder extends BaseElementTextHolder {

    ImageView imageBg;
    TextView textAttribution;
    TextView textContent;

    public ElementQuoteHolder(View itemView) {
        super(itemView);
    }

    public static ElementQuoteHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_quote, parent);
        ElementQuoteHolder holder = new ElementQuoteHolder(view);
        holder.imageBg = (ImageView) view.findViewById(R.id.qs_image_quote_bg);
        holder.textAttribution = (TextView) view.findViewById(R.id.qs_text_attribution);
        holder.textContent = (TextView) view.findViewById(R.id.qs_text_content);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {

        textAttribution.setVisibility(View.GONE);

        if (element.isTypeVirtualQuote()) {

        } else if (element.isTypeQuote() || element.isTypeBlockQuote()) {
            //int quoteId = R.drawable.ic_quote_circle;
            if (element.isTypeQuote()) {
                //quoteId = R.drawable.ic_quote_bubble;
            }

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textContent.getLayoutParams();
            float margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                    textContent.getContext().getResources().getDisplayMetrics());
            layoutParams.bottomMargin = Math.round(margin);
            textContent.setLayoutParams(layoutParams);
            itemView.setVisibility(View.VISIBLE);
            textContent.setVisibility(View.VISIBLE);
            textLoader
                    .text(element.subTypeMeta().content())
                    .into(textContent);
            
            if (element.subTypeMeta() != null && !TextUtils.isEmpty(element.subTypeMeta().attribution())) {
                textAttribution.setVisibility(View.VISIBLE);
                StringBuilder builder = new StringBuilder(element.subTypeMeta().attribution());
                if (element.isTypeBlockQuote()) {
                    textAttribution.setGravity(Gravity.LEFT | Gravity.START);
                } else {
                    builder.insert(0, " - ");
                    textAttribution.setGravity(Gravity.RIGHT | Gravity.END);
                }

                textLoader.text(builder.toString())
                        .urlClickListener(new TextLoader.UrlClickListener() {
                            @Override
                            public void onUrlClicked(View widget, Uri urlClicked, Story slugStory) {

                            }
                        })
                        .into(textAttribution);
            }
            //ImageUtils.tintImageViewDInt(mQuoteBgImageView, quoteId, colorCode);
        }
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
