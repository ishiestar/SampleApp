package com.example.ishitasinha.sampleapp.viewholders;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.commons.StringUtils;
import com.quintype.core.story.StoryElement;
import com.quintype.coreui.util.ViewUtils;

import timber.log.Timber;

/**
 * An object instance of ElementSummaryHolder used to load Summary story element.
 */
public class ElementSummaryHolder extends BaseElementTextHolder {

    //views
    TextView textSummary;
    TextView textView;
    ImageView imageExpandedButton;
    View summaryPanel;

    //local variables
    String boundedId;
    boolean isExpanded;

    public ElementSummaryHolder(View itemView) {
        super(itemView);
    }

    /**
     * Static method to create SummaryHolder
     *
     * @param parent {@link ViewGroup}
     * @return {@link ElementSummaryHolder}
     */
    public static ElementSummaryHolder create(ViewGroup parent) {
        View view = ViewUtils.inflate(R.layout.qs_story_layout_summary, parent);
        ElementSummaryHolder holder = new ElementSummaryHolder(view);
        holder.textSummary = (TextView) view.findViewById(R.id.qs_text_summary);
        holder.textView = (TextView) view.findViewById(R.id.qs_text);
        holder.summaryPanel = view.findViewById(R.id.qs_frame_summary);
        holder.imageExpandedButton = (ImageView) view.findViewById(R.id.qs_image_summary_expand);
        return holder;
    }

    @Override
    public void bind(StoryElement element) {
        if (!StringUtils.equalsIgnoreCase(element.id(), boundedId)) {
            boundedId = element.id();
            Timber.d("Binding new element for id %s", boundedId);
            //check if text view holder has set item view visibility
            if (itemView.getVisibility() == View.VISIBLE) {
                textView.setVisibility(View.VISIBLE);
                isExpanded = true;
                textView.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                    textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                } else  {
                                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                                addClickListener();
                            }
                        });
            }
        } else {
            Timber.d("Binding same element for id %s, restoring state", boundedId);
            //binding the same element, restore the state
            if (isExpanded) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.GONE);
            }
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

    private void addClickListener() {
        final RelativeLayout.LayoutParams originalLayoutParams = (RelativeLayout.LayoutParams) textView
                .getLayoutParams();
        final int originalHeight = textView.getHeight();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageExpandedButton.animate().rotationBy(180).start();
                float startScale = 0f;
                float endScale = 0f;
                if (textView.getVisibility() == View.VISIBLE) {
                    isExpanded = false;
                    startScale = 1f;
                    endScale = 0f;
                } else {
                    isExpanded = true;
                    startScale = 0f;
                    endScale = 1f;
                }
                final boolean shouldShow = startScale == 0;
                ValueAnimator scaleAnimator = ValueAnimator.ofFloat(startScale, endScale);
                scaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                scaleAnimator.setDuration(150);
                scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float scale = (float) valueAnimator.getAnimatedValue();
                        originalLayoutParams.height = Math.round(originalHeight * scale);
                        textView.setLayoutParams(originalLayoutParams);
                    }
                });
                scaleAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        textView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        originalLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        textView.setLayoutParams(originalLayoutParams);
                        if (shouldShow) {
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        if (shouldShow) {
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                scaleAnimator.start();
            }
        };
        imageExpandedButton.setOnClickListener(clickListener);
        summaryPanel.setOnClickListener(clickListener);
    }
}
