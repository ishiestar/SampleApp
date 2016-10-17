package com.example.ishitasinha.sampleapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.ishitasinha.sampleapp.viewholders.BaseElementHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementBigFactHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementBlockQuoteHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementCustomHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementImageHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementJSEmbedHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementQuoteHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementSoundcloudHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementSummaryHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementTagHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementTextHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementTitleHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementWebViewHolder;
import com.example.ishitasinha.sampleapp.viewholders.ElementYoutubeHolder;
import com.example.ishitasinha.sampleapp.viewholders.TitleHolder;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.story.ElementViewType;
import com.quintype.coreui.story.StoryElementBinder;
import com.quintype.coreui.story.StoryPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * An object instance of StoryAdapter.
 * Story is a collection of story elements.
 * We load individual story elements using an adapter into a list for better
 * performance.
 * <p></p>
 * All the holders in this adapter should extend a {@link BaseElementHolder}
 * <p></p>
 * For custom adapters holders should implement {@link StoryElementBinder} for the story presenter.
 */
public class StoryAdapter extends RecyclerView.Adapter<BaseElementHolder> implements StoryPresenter.BinderCallback {

    StoryPresenter storyPresenter;
    List<StoryElement> imageElements = new ArrayList<>();

    public StoryAdapter(StoryPresenter presenter) {
        if (presenter == null) {
            throw new NullPointerException("Presenter cannot be null");
        }
        presenter.binderCallback(this);
        this.storyPresenter = presenter;
        imageElements.clear();
        imageElements.add(storyPresenter.story().getHeroElement());
        for (int i = 1; i < storyPresenter.getElementCount(); i++) {
            if (storyPresenter.getElement(i).isTypeImage())
                imageElements.add(storyPresenter.getElement(i));
        }
        storyPresenter.insertElementBinder(0, TitleHolder.class);
        storyPresenter.insertElementBinder(storyPresenter.getElementCount(), ElementTagHolder.class);
    }

    public List<StoryElement> getImages() {
        return imageElements;
    }

    @Override
    public int getItemViewType(int position) {
        return storyPresenter.getElementViewType(position);
    }

    @Override
    public BaseElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (BaseElementHolder) storyPresenter.getElementBinderForViewType(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseElementHolder holder, int position) {
        storyPresenter.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return storyPresenter.getElementCount();
    }

    /**
     * Register {@link Class} for known view types.
     *
     * @param viewType {@link ElementViewType}
     * @return {@link Class}
     */
    @Override
    public Class<? extends StoryElementBinder> onRegisterViewTypeClass(@ElementViewType int viewType) {
        Class cls;
        switch (viewType) {
            case ElementViewType.TEXT: {
                cls = ElementTextHolder.class;
                break;
            }
            case ElementViewType.IMAGE: {
                cls = ElementImageHolder.class;
                break;
            }
            case ElementViewType.AUDIO: {
                cls = ElementWebViewHolder.class;
                break;
            }
            case ElementViewType.BIG_FACT: {
                cls = ElementBigFactHolder.class;
                break;
            }
            case ElementViewType.BLOCK_QUOTE: {
                cls = ElementQuoteHolder.class;
                break;
            }
            case ElementViewType.BLURB: {
                cls = ElementTextHolder.class;
                break;
            }
            case ElementViewType.GALLERY: {
                cls = ElementWebViewHolder.class;
                break;
            }
            case ElementViewType.GIF: {
                cls = ElementImageHolder.class;
                break;
            }
            case ElementViewType.MEDIA: {
                cls = ElementWebViewHolder.class;
                break;
            }
            case ElementViewType.JS_EMBED: {
                cls = ElementJSEmbedHolder.class;
                break;
            }
            case ElementViewType.QUOTE: {
                cls = ElementQuoteHolder.class;
                break;
            }
            case ElementViewType.SLIDESHOW: {
                cls = ElementWebViewHolder.class;
                break;
            }
            case ElementViewType.SOUND_CLOUD: {
                cls = ElementSoundcloudHolder.class;
                break;
            }
            case ElementViewType.SUMMARY: {
                cls = ElementSummaryHolder.class;
                break;
            }
            case ElementViewType.TITLE: {
                cls = ElementTitleHolder.class;
                break;
            }
            case ElementViewType.TWEET: {
                cls = ElementWebViewHolder.class;
                break;
            }
            case ElementViewType.VIDEO: {
                cls = ElementWebViewHolder.class;
                break;
            }
            case ElementViewType.YOUTUBE: {
                cls = ElementYoutubeHolder.class;
                break;
            }
            default:
            case ElementViewType.UNKNOWN: {
                cls = ElementWebViewHolder.class;
                break;
            }
        }

        return cls;
    }

    /**
     * Get {@link StoryElementBinder} for type {@link Class}
     *
     * @param parent {@link ViewGroup}
     * @param cls    {@link Class} for known viewType see {@link #onRegisterViewTypeClass(int)}. For unknown viewTypes see {@link StoryPresenter#insertElement(StoryElement, int, Class)}
     * @return {@link StoryElementBinder}
     */
    @Override
    public StoryElementBinder onCreateElementBinder(ViewGroup parent, Class<? extends StoryElementBinder> cls) {
        StoryElementBinder binder;
        if (cls.equals(ElementTextHolder.class)) {
            binder = ElementTextHolder.create(parent);
        } else if (cls.equals(ElementImageHolder.class)) {
            binder = ElementImageHolder.create(parent,imageElements);
        } else if (cls.equals(ElementSoundcloudHolder.class)) {
            binder = ElementSoundcloudHolder.create(parent);
        } else if (cls.equals(ElementJSEmbedHolder.class)) {
            binder = ElementJSEmbedHolder.create(parent);
        } else if (cls.equals(ElementTitleHolder.class)) {
            binder = ElementTitleHolder.create(parent);
        } else if (cls.equals(ElementSummaryHolder.class)) {
            binder = ElementSummaryHolder.create(parent);
        } else if (cls.equals(ElementBigFactHolder.class)) {
            binder = ElementBigFactHolder.create(parent);
        } else if (cls.equals(ElementTextHolder.class)) {
            binder = ElementTextHolder.create(parent);
        } else if (cls.equals(ElementBlockQuoteHolder.class)) {
            binder = ElementQuoteHolder.create(parent);
        } else if (cls.equals(ElementQuoteHolder.class)) {
            binder = ElementQuoteHolder.create(parent);
        } else if (cls.equals(ElementCustomHolder.class)) {
            //For custom view type
            binder = ElementCustomHolder.create(parent);
        } else if (cls.equals(TitleHolder.class)) {
            binder = TitleHolder.create(parent, storyPresenter.story());
        } else if (cls.equals(ElementTagHolder.class)) {
            binder = ElementTagHolder.create(parent, storyPresenter.story());
        } else if (cls.equals(ElementYoutubeHolder.class)) {
            binder = ElementYoutubeHolder.create(parent);
        } else {
            binder = ElementWebViewHolder.create(parent);
        }
        return binder;
    }

    @Override
    public void onViewDetachedFromWindow(BaseElementHolder holder) {
        if(holder instanceof ElementYoutubeHolder){
            ((ElementYoutubeHolder) holder).recycleYoutubeThumbnailLoader();
            Log.v(StoryAdapter.class.getSimpleName(), "thumnailLoader recycled");
        }
        super.onViewDetachedFromWindow(holder);
    }
}
