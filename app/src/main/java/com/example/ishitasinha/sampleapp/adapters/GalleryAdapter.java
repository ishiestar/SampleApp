package com.example.ishitasinha.sampleapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.target.Target;
import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.ImageLoader;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ishitasinha on 11/05/16.
 */
public class GalleryAdapter extends PagerAdapter {

    List<StoryElement> imageElements;
    ImageLoader imageLoader = ImageLoader.create();
    PhotoViewAttacher mAttacher;
    PhotoView photo;

    public GalleryAdapter(List<StoryElement> imageElements) {
        this.imageElements = imageElements;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Context context = container.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.gallery_item_view, container, false);
        photo = (PhotoView) itemView.findViewById(R.id.photo);

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;

        imageLoader
                .width(width)
                .enableIndicator(true)
                .quality(1F)
                .useFocalPoints(true)
                .using(imageElements.get(position))
                .glide(photo)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(photo);

//        Picasso.with(context)
//                .load(imageLoader
//                        .width(width)
//                        .using(imageElements.get(position))
//                        .url())
//                .into(photo/*, callback*/);

//        mAttacher = new PhotoViewAttacher(photo);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageElements.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
