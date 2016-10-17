package com.example.ishitasinha.sampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ishitasinha.sampleapp.adapters.SectionsListAdapter;
import com.quintype.core.Quintype;
import com.quintype.core.data.SectionMeta;

import java.util.List;

public class SectionsActivity extends AppCompatActivity /*implements DrawerSectionsAdapter.OnDrawerItemSelectedListener*/ {

    public static final String LOG_TAG = SectionsActivity.class.getSimpleName();
    RecyclerView sectionsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sectionsRecyclerView = (RecyclerView) findViewById(R.id.rv_sections);
        sectionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SectionMeta> sectionMetas = Quintype.publisherConfig().sections();
        SectionsListAdapter adapter = new SectionsListAdapter(sectionMetas);
//        Map<String, Sections> menuMap = new HashMap<>();
//        List<NavMenu> menuList = Quintype.publisherConfig().layout().navigationMenu();
//        for (NavMenu menuItem : menuList) {
//            String parentId = menuItem.parentId();
//            if (parentId == null) {
//                Sections parentSection = new Sections();
//                parentSection.setMenuItem(menuItem);
//                menuMap.put(menuItem.id(), parentSection);
//            } else if (menuMap.containsKey(parentId)) {
//                menuMap.get(parentId).addSubsection(menuItem);
//            } else {
//                Sections parentSection = new Sections();
//                parentSection.addSubsection(menuItem);
//                menuMap.put(parentId, parentSection);
//            }
//        }
//        for (Iterator<Map.Entry<String, Sections>> menuEntry = menuMap.entrySet().iterator(); menuEntry.hasNext(); ) {
//            Map.Entry<String, Sections> entry = menuEntry.next();
//            Sections currentSection = entry.getValue();
//            if (currentSection.getMenuItem() == null && !currentSection.getChildItemList().isEmpty()) {
//                for (Object childItem : currentSection.getChildItemList()) {
//                    Sections parentSection = new Sections();
//                    parentSection.setMenuItem((NavMenu) childItem);
//                    menuMap.put(((NavMenu) childItem).id(), parentSection);
//                }
//                menuEntry.remove();
//            }
//        }
//        for (Sections section : menuMap.values()) {
//            Log.v(LOG_TAG, section.getName() + "[subsections: " + section.getChildItemList().size() + "]");
//        }
//        List<? extends ParentListItem> list = new ArrayList<>(menuMap.values());
//        Collections.sort(list, new Comparator<ParentListItem>() {
//            @Override
//            public int compare(ParentListItem t1, ParentListItem t2) {
//                return ((Long) ((Sections) t1).getMenuItem().rank()).compareTo(((Sections) t2).getMenuItem().rank());
//            }
//        });
//        DrawerSectionsAdapter adapter = new DrawerSectionsAdapter(getApplicationContext(), list, this);
        sectionsRecyclerView.setAdapter(adapter);
    }

   /* @Override
    public void onDrawerItemSelected(Sections menuGroup) {
        Intent intent = new Intent(this, SectionPagerActivity.class);
        intent.putExtra("param1", menuGroup);
        startActivity(intent);
    }*/
}
