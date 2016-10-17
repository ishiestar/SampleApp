package com.example.ishitasinha.sampleapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.ishitasinha.sampleapp.DetailFragment;
import com.quintype.core.story.Story;

import java.util.List;

/**
 * Created by ishitasinha on 02/05/16.
 */
public class StoryViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<>();
    List<Story> storyList;

    public StoryViewPagerAdapter(FragmentManager fm, List<Story> storyList) {
        super(fm);
        this.storyList = storyList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(storyList.get(position).id(), position);
    }

    @Override
    public int getCount() {
        return storyList.size();
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
