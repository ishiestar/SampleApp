package com.example.ishitasinha.sampleapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.adapters.ListAdapter;
import com.example.ishitasinha.sampleapp.utility.Paginator;
import com.quintype.core.Quintype;
import com.quintype.core.data.Callback;
import com.quintype.core.section.Section;
import com.quintype.core.story.Story;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * <p/>
 * Use the {@link SectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionFragment extends Fragment {

    public static final String TAG = SectionFragment.class.getSimpleName();
    private static final String SECTION = "param1";
    String sectionId;
    String sectionName;
    RecyclerView recyclerView;
    TextView emptyText;
    private Section mSection;

    public SectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment SectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SectionFragment newInstance(Section section) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putParcelable(SECTION, section);
        fragment.setArguments(args);
        fragment.sectionId = section.id();
        fragment.sectionName = section.name();
        return fragment;
    }

    public String getSectionName() {
        return sectionName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSection = getArguments().getParcelable(SECTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_section, container, false);
        emptyText = (TextView) rootView.findViewById(R.id.tv_no_stories);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.section_stories_list);
        Quintype.story()
                .getStories()
                .section(mSection.name())
                .limit(10)
                .execute(new Callback<List<Story>>() {
                    @Override
                    public void onSuccess(List<Story> storyList) {
                        if (!storyList.isEmpty()) {
                            emptyText.setVisibility(View.INVISIBLE);
                            ListAdapter adapter = new ListAdapter(storyList, null);
                            recyclerView.setAdapter(adapter);
                            recyclerView.addOnScrollListener(new Paginator(getActivity(), adapter, mSection.name()));
                        } else
                            emptyText.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
                    }
                });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String getQualifiedName() {
        return TAG + "_" + sectionId;
    }
}
