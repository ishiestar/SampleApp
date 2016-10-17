package com.example.ishitasinha.sampleapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ishitasinha.sampleapp.SectionFragment;

import java.util.List;

/**
 * Created by ishitasinha on 09/06/16.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mSubsectionList;

    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mSubsectionList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mSubsectionList.get(position);
    }

    @Override
    public int getCount() {
        return mSubsectionList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "LATEST";
        }
        return ((SectionFragment) mSubsectionList.get(position)).getSectionName();
    }
}
