package com.example.ishitasinha.sampleapp.viewholders;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.commons.StringUtils;
import com.quintype.core.Quintype;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.util.ViewUtils;

import timber.log.Timber;

/**
 * An object instance of ElementWebViewHolder used to load generic Story Elements if no holder is found.
 */
public class ElementWebViewHolder extends BaseElementHolder {

    WebView webView;
    //StoryElement ID this view holder is bounded by.
    String boundedId;

    public ElementWebViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Static method to create WebViewHolder
     *
     * @param parent {@link ViewGroup}
     * @return {@link ElementWebViewHolder}
     */
    public static ElementWebViewHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_webview, parent);
        ElementWebViewHolder holder = new ElementWebViewHolder(view);
        holder.webView = (WebView) view.findViewById(R.id.qs_webview);
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.getSettings().setSupportZoom(false);
        holder.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //this makes sure that long click never gives text selection on webview
        holder.webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //do nothing
                return true;
            }
        });
        return holder;
    }

    @Override
    public void bind(StoryElement element) {
        if (!StringUtils.equalsAnyIgnoreCase(element.id(), boundedId)) {
            boundedId = element.id();
            webView.loadUrl("about:blank");
            loadWebView(element);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Uri uriClicked = Uri.parse(url);
                    return true;
                }
            });
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

    private void loadWebView(StoryElement elem) {
        String loadedUrl = (String) webView.getTag();
        String urlToLoad = Quintype.config().baseUrl() + elem.pageUrl();
        if (!urlToLoad.equals(loadedUrl)) {
            webView.setTag(urlToLoad);
            Timber.d("Force loading url %s", elem.pageUrl());
            webView.loadUrl(urlToLoad);
        } else {
            Timber.d("Skip loading url %s", elem.pageUrl());
        }
    }
}
