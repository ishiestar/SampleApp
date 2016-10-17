package com.example.ishitasinha.sampleapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.example.ishitasinha.sampleapp.SectionDetailsActivity;
import com.quintype.core.data.SectionMeta;

import java.util.List;

/**
 * Created by ishitasinha on 19/09/16.
 */
public class SectionsListAdapter extends RecyclerView.Adapter<SectionsListAdapter.SectionViewHolder> {

    List<SectionMeta> mSectionMetaList;
    Context context;

    public SectionsListAdapter(List<SectionMeta> sectionMetaList) {
        mSectionMetaList = sectionMetaList;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.sections_list_item, parent, false);
        return new SectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        holder.textView.setText(mSectionMetaList.get(position).name());
    }

    @Override
    public int getItemCount() {
        return mSectionMetaList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public SectionViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.section_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SectionDetailsActivity.class);
            intent.putExtra("SECTION", textView.getText());
            context.startActivity(intent);
        }
    }
}
