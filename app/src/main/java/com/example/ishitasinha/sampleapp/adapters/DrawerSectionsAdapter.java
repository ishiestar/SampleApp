package com.example.ishitasinha.sampleapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.utility.Sections;
import com.example.ishitasinha.sampleapp.viewholders.SectionChildViewHolder;
import com.example.ishitasinha.sampleapp.viewholders.SectionParentViewHolder;
import com.quintype.core.data.NavMenu;

import java.util.List;

/**
 * Created by ishitasinha on 03/06/16.
 */
public class DrawerSectionsAdapter extends ExpandableRecyclerAdapter<SectionParentViewHolder, SectionChildViewHolder> {

    private LayoutInflater mInflater;
    OnDrawerItemSelectedListener listener;

    public DrawerSectionsAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList, OnDrawerItemSelectedListener listener) {
        super(parentItemList);
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    // onCreate ...
    @Override
    public SectionParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentSection = mInflater.inflate(R.layout.section_parent_list_item, parentViewGroup, false);
        return new SectionParentViewHolder(parentSection, listener);
    }

    @Override
    public SectionChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childSection = mInflater.inflate(R.layout.section_child_list_item, childViewGroup, false);
        return new SectionChildViewHolder(childSection, listener, null);
    }

    // onBind ...
    @Override
    public void onBindParentViewHolder(SectionParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        Sections section = (Sections) parentListItem;
        parentViewHolder.bind(section);
    }

    @Override
    public void onBindChildViewHolder(SectionChildViewHolder ingredientViewHolder, int position, Object childListItem) {
        NavMenu subsection = (NavMenu) childListItem;
        ingredientViewHolder.bind(subsection);
    }

    public interface OnDrawerItemSelectedListener {
        void onDrawerItemSelected(Sections menuGroup);
    }
}
