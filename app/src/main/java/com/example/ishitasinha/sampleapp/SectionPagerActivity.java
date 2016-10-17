package com.example.ishitasinha.sampleapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.adapters.SectionsPagerAdapter;
import com.example.ishitasinha.sampleapp.utility.Sections;
import com.quintype.core.data.NavMenu;

import java.util.ArrayList;
import java.util.List;

public class SectionPagerActivity extends AppCompatActivity {
    private static final String NAV_MENU_GROUP = "param1";
    public static final String TAG = SectionPagerActivity.class.getSimpleName();
    String sectionId;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView parentSectionName;
    private Sections navMenuGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_pager);
        if (getIntent().getExtras() != null) {
            navMenuGroup = getIntent().getParcelableExtra(NAV_MENU_GROUP);
        }
        viewPager = (ViewPager) findViewById(R.id.section_container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

//        parentSectionName.setText(navMenuGroup.getName());

        List<Fragment> childSectionFragments = new ArrayList<>();
        childSectionFragments.add(SectionFragment.newInstance(navMenuGroup.getMenuItem().section()));
        for (NavMenu menuItem:navMenuGroup.getChildItemList()) {
            childSectionFragments.add(SectionFragment.newInstance(menuItem.section()));
        }

        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), childSectionFragments));
        viewPager.setCurrentItem(navMenuGroup.getPosition());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#000000")));
    }


    public String getQualifiedName() {
        return TAG + sectionId;
    }

}
