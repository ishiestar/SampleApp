package com.example.ishitasinha.sampleapp.viewholders;

import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.commons.StringUtils;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.util.ViewUtils;
import com.quintype.coreui.util.html.HtmlUtils;

import timber.log.Timber;

/**
 * An object instance of ElementSoundcloudHolder used to load souncloud audio into webview.
 */
public class ElementSoundcloudHolder extends BaseElementHolder {

    WebView webView;
    //StoryElement ID this view holder is bounded by.
    String boundedId;

    public ElementSoundcloudHolder(View itemView) {
        super(itemView);
    }

    /**
     * Static method to create SoundCloudHolder
     *
     * @param parent {@link ViewGroup}
     * @return {@link ElementSoundcloudHolder}
     */
    public static ElementSoundcloudHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_soundcloud, parent);
        ElementSoundcloudHolder holder = new ElementSoundcloudHolder(view);
        holder.webView = (WebView) view.findViewById(R.id.qs_webview_soundcloud);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {

        if (!StringUtils.equalsAnyIgnoreCase(element.id(), boundedId)) {
            boundedId = element.id();
            webView.getSettings().setJavaScriptEnabled(true);
            String html = webView.getResources().getString(R.string.qs_soundcloud_html);
            html = html.replace("replace", element.embedUrl());
            Timber.d("Sound cloud html to load is %s", html);
            webView.loadData(html, "text/html; charset=UTF-8", "UTF-8");

            Spanned title = HtmlUtils.getHtml(element.title());
        } else {
            Timber.d("Skipping soundcloud binding to same elem %s", boundedId);
        }
    }

    @Override
    public View getView() {
        return itemView;
    }

    @Override
    public boolean recreate() {
        return true;
    }
}
