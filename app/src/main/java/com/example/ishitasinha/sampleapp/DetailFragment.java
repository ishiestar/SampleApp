package com.example.ishitasinha.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishitasinha.sampleapp.adapters.StoryAdapter;
import com.example.ishitasinha.sampleapp.utility.FetchedImages;
import com.quintype.core.Quintype;
import com.quintype.core.data.Callback;
import com.quintype.core.story.IdStory;
import com.quintype.core.story.Story;
import com.quintype.coreui.story.StoryPresenter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * <p/>
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "ID";
    private static final String ARG_POSITION = "POSITION";

    public static final String LOG_TAG = DetailFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String id;
    private int position;

    private OnFragmentInteractionListener mListener;

    TextView titleTextView,
            authorTextView,
            timeTextView;
    RecyclerView rvStoryContent;
    StoryPresenter presenter;
    StoryAdapter adapter;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param storyId  Story ID with which the story details are to be fetched.
     * @param position The recycler view position that was clicked.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String storyId, int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, storyId);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        Log.v(LOG_TAG, "newInstance: id=" + storyId + ", position=" + position);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
            position = getArguments().getInt(ARG_POSITION);
            Log.v(LOG_TAG, "onCreate: id=" + id + ", position=" + position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        titleTextView = (TextView) rootView.findViewById(R.id.story_title);
        authorTextView = (TextView) rootView.findViewById(R.id.story_author);
        timeTextView = (TextView) rootView.findViewById(R.id.story_updated_at);
        rvStoryContent = (RecyclerView) rootView.findViewById(R.id.rv_story_content);
        rvStoryContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvStoryContent.setHasFixedSize(true);
        populateView();
        return rootView;
    }

    private void populateView() {

        Quintype.story().getStoryById().id(id).execute(new Callback<IdStory>() {
            @Override
            public void onSuccess(IdStory idStory) {
                final Story story = idStory.story();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        presenter = StoryPresenter.create(story);
                        adapter = new StoryAdapter(presenter);
//                        if (mListener != null)
//                            mListener.onFragmentInteraction(adapter.getImages());
                        rvStoryContent.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), "Failed to get data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openGallery() {
//        Timber.i("Is Fragment Added " + isAdded());
        Intent intent = new Intent(getActivity(), ImageSliderActivity.class);
        if (adapter != null) {
            intent.putExtra("ELEMENTS", new FetchedImages(adapter.getImages()));
            startActivity(intent);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(FetchedImages images);
    }
}
