package com.example.ishitasinha.sampleapp.viewholders;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.adapters.DrawerSectionsAdapter;
import com.example.ishitasinha.sampleapp.utility.Sections;

/**
 * Created by ishitasinha on 03/06/16.
 */
public class SectionParentViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    private TextView mSectionName;
    private ImageButton mExpandBtn;
    DrawerSectionsAdapter.OnDrawerItemSelectedListener listener;

    public SectionParentViewHolder(View itemView, DrawerSectionsAdapter.OnDrawerItemSelectedListener listener) {
        super(itemView);
        mExpandBtn = (ImageButton) itemView.findViewById(R.id.section_expand_button);
        mSectionName = (TextView) itemView.findViewById(R.id.section_name);
        this.listener = listener;

        mExpandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapseView();
                } else {
                    expandView();
                }
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {
                mExpandBtn.setRotation(ROTATED_POSITION);
            } else {
                mExpandBtn.setRotation(INITIAL_POSITION);
            }
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            mExpandBtn.startAnimation(rotateAnimation);
        }
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }

    public void bind(final Sections section) {
        mSectionName.setText(section.getName());
        if (section.getChildItemList().size() == 0) {
            mExpandBtn.setVisibility(View.GONE);
        }
        mSectionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDrawerItemSelected(section);
//                Context context = view.getContext();
//                Section sectionClicked = section.getMenuItem().section();
//                Intent intent = new Intent(context, SectionDetailsActivity.class);
//                intent.putExtra("SECTION", sectionClicked);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });
    }
}