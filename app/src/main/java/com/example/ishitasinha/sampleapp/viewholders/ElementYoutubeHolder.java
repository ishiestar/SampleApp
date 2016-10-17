package com.example.ishitasinha.sampleapp.viewholders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.ishitasinha.sampleapp.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.util.ViewUtils;

/**
 * An object instance representing ElementYoutubeHolder used to load
 * Youtube thumbnails and preview videos
 */
public class ElementYoutubeHolder extends BaseElementHolder implements YouTubeThumbnailView.OnInitializedListener {

    YouTubeThumbnailView thumbView;
    YouTubeThumbnailLoader thumbLoader;
    String youTubeKey = null;
//    private PublishSubject<Pair<String, Object>> eventSubject;
    String videoId;
    Context context;
    public ElementYoutubeHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final StoryElement element) {
        if (element.url() == null) {
            return;
        }
        Uri uri = Uri.parse(element.url());
        videoId = uri.getQueryParameter("v");
        thumbView.setTag(videoId);
        thumbView.initialize(youTubeKey, this);
        thumbView.setImageResource(R.drawable.placeholder);

        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {

            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                itemView.removeOnAttachStateChangeListener(this);
                if (thumbLoader != null) {
                    try {
                        thumbLoader.setOnThumbnailLoadedListener(null);
                        thumbLoader.release();
//                        for (YouTubeThumbnailLoader loader : viewLoaderMap.values()) {
//                            loader.setOnThumbnailLoadedListener(null);
//                            loader.release();
//                        }
                        Log.v(ElementYoutubeHolder.class.getSimpleName(), "thumbLoader released");
                    } catch (IllegalStateException ile) {
                        //May be this youtube thumbnail loader already is released, so catch the exception here
                    }
                }
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = view.getContext();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, youTubeKey, videoId);
                context.startActivity(intent);
//                Pair<String, Object> youtubeClickEvent = new Pair<String, Object>(StoryFragment.EVENT_NAME_STORY_ELEM_CLICKED,element);
//                eventSubject.onNext(youtubeClickEvent);
            }
        });
    }

    @Override
    public View getView() {
        return itemView;
    }

    @Override
    public boolean recreate() {
        return true;
    }

    /**
     * Static method to get YoutubeHolder
     *
     * @param parent {@link ViewGroup}
     * @return {@link ElementYoutubeHolder}
     */
    public static ElementYoutubeHolder create(ViewGroup parent/*, PublishSubject<Pair<String, Object>> eventSubject*/) {
        Log.v(ElementYoutubeHolder.class.getSimpleName(), "created element youtube holder");
        View view = ViewUtils.inflate(R.layout.qs_story_layout_youtube, parent);
        ElementYoutubeHolder holder = new ElementYoutubeHolder(view);
        holder.thumbView = (YouTubeThumbnailView) view.findViewById(R.id.qs_youtube_thumbnail);
        holder.youTubeKey = parent.getContext().getString(R.string.qs_youtube_key);
//        holder.eventSubject = eventSubject;
        return holder;
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        Log.v(ElementYoutubeHolder.class.getSimpleName(), "initialization success");
        String videoId = (String) youTubeThumbnailView.getTag();
        youTubeThumbnailLoader.setVideo(videoId);
        thumbLoader = youTubeThumbnailLoader;
//        viewLoaderMap.put(youTubeThumbnailView, youTubeThumbnailLoader);
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        Log.v(ElementYoutubeHolder.class.getSimpleName(), "initialization failure: " + youTubeInitializationResult.toString());
        thumbLoader = null;
    }

    public void recycleYoutubeThumbnailLoader() {
        if (thumbLoader != null) {
            thumbLoader.release();
        }
    }
}
