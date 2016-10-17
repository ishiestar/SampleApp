package com.example.ishitasinha.sampleapp.viewholders;

import android.net.Uri;
import android.text.TextUtils;
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
 * An object instance of ElementJSEmbedHolder used to load JSEmbed into a WebView
 */
public class ElementJSEmbedHolder extends BaseElementHolder {

    WebView webView;
    //StoryElement ID this view holder is bounded by.
    String boundedId;

    public ElementJSEmbedHolder(View itemView) {
        super(itemView);
    }

    /**
     * Static method to create JSEmbedHolder
     *
     * @param parent {@link ViewGroup}
     * @return {@link ElementJSEmbedHolder}
     */
    public static ElementJSEmbedHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_jsembed, parent);
        ElementJSEmbedHolder holder = new ElementJSEmbedHolder(view);
        holder.webView = (WebView) view.findViewById(R.id.qs_webview_jsembed);
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

    /**
     * Load {@link StoryElement} into WebView
     *
     * @param elem
     */
    private void loadWebView(StoryElement elem) {
        String loadedUrl = (String) webView.getTag();
        String urlToLoad = Quintype.config().baseUrl() + elem.pageUrl();
        if (!urlToLoad.equals(loadedUrl)) {
            webView.setTag(urlToLoad);
            String htmlContent = webView.getContext().getString(R.string.qs_frameless_html_css);
            Timber.d("Force loading url %s", elem.pageUrl());
            if (!TextUtils.isEmpty(elem.decodedJsEmbed())) {
                String htmlData = htmlContent.replaceAll("replace", elem.decodedJsEmbed());
                Timber.d("Data is %s ", htmlData);
                webView.loadDataWithBaseURL("http://localhost/", htmlData, "text/html; charset=UTF-8", "UTF-8", null);
            } else {
                webView.loadUrl(urlToLoad);
            }
        } else {
            Timber.d("Skip loading url %s", elem.pageUrl());
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
