package com.example.ishitasinha.sampleapp.viewholders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.SectionDetailsActivity;
import com.example.ishitasinha.sampleapp.adapters.DrawerSectionsAdapter;
import com.example.ishitasinha.sampleapp.utility.Sections;
import com.quintype.core.data.NavMenu;

/**
 * Created by ishitasinha on 03/06/16.
 */
public class SectionChildViewHolder extends ChildViewHolder {

    private TextView mSubsectionTextView;
    DrawerSectionsAdapter.OnDrawerItemSelectedListener listener;
    Sections parent;

    public SectionChildViewHolder (View itemView, DrawerSectionsAdapter.OnDrawerItemSelectedListener listener, Sections parent) {
        super(itemView);
        mSubsectionTextView = (TextView) itemView.findViewById(R.id.child_section_name);
        this.listener = listener;
//        this.parent = parent;
    }

    public void bind(final NavMenu subSection) {
        mSubsectionTextView.setText(subSection.title());
        mSubsectionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDrawerItemSelected(parent);
                Context context = view.getContext();
                Intent intent = new Intent(context, SectionDetailsActivity.class);
                intent.putExtra("SECTION", subSection);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}
