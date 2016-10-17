package com.example.ishitasinha.sampleapp.viewholders;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.ImageSliderActivity;
import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.utility.FetchedImages;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.ImageLoader;
import com.quintype.coreui.TextLoader;
import com.quintype.coreui.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * An object instance of ElementImageHolder used to load an image
 */
public class ElementImageHolder extends BaseElementHolder implements View.OnClickListener {

    ImageLoader imageLoader = ImageLoader.create()
            .width(400)
            .useFocalPoints(true)
            .enableIndicator(true);
    TextLoader textLoader = TextLoader.create()
            .linkUnderline(true)
            .linkColor(Color.parseColor("#00A1FF"));
    ImageView imageView;
    TextView captionView;
    int position;
    List<StoryElement> imageElements = new ArrayList<>();
    FetchedImages images;

    public ElementImageHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public static ElementImageHolder create(ViewGroup parent, List<StoryElement> imageElements) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_image, parent);
        ElementImageHolder holder = new ElementImageHolder(view);
        holder.imageView = (ImageView) view.findViewById(R.id.qs_image_element);
        holder.imageElements.addAll(imageElements);
        holder.images = new FetchedImages(imageElements);
        holder.captionView = (TextView) view.findViewById(R.id.qs_image_caption);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {
        /*//default image loading
        imageLoader
                .using(element)
                .into(imageView);*/

        //image loading using glide methods
        imageLoader
                .enableIndicator(true)
                .useFocalPoints(true)
                .using(element)
                .glide(imageView) //use default glide methods
                .into(imageView);
        if (!element.title().isEmpty()) {
            captionView.setVisibility(View.VISIBLE);
            textLoader.text(element.title()).into(captionView);
        }

        if (imageElements.contains(element))
            position = imageElements.indexOf(element);
    }

    @Override
    public View getView() {
        return itemView;
    }

    @Override
    public boolean recreate() {
        return false;
    }

    @Override
    public void onClick(View view) {
        if (!imageElements.isEmpty()) {
            Intent intent = new Intent(view.getContext(), ImageSliderActivity.class);
            intent.putExtra("ELEMENTS", images);
            intent.putExtra("POSITION", position);
            view.getContext().startActivity(intent);
        } else {
            Toast.makeText(view.getContext(), "Image list is empty.", Toast.LENGTH_SHORT).show();
        }
    }
}
