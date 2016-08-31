package com.pies.platform.managersActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.pies.platform.OBjfragmentFragment;
import com.pies.platform.R;
import com.pies.platform.admin.Feedback_FeedsFragment;
import com.pies.platform.model.managers.Manager_Assign;
import com.pies.platform.teachersActivity.AssignmentFragment;

import java.util.ArrayList;
import java.util.List;

public class Objandfeedback extends AppCompatActivity  implements TabLayout.OnTabSelectedListener{
   public static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objandfeedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
         name = extras.getString("teachername");
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager1);
        setupViewPager(viewPager,name);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs1);
        tabs.setupWithViewPager(viewPager);



    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager, String name) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new OBjfragmentFragment(), "Objectives");
        adapter.addFragment(new Feedback_FeedsFragment(),"Feedback");
        Feedback_FeedsFragment frag = new Feedback_FeedsFragment();
        Bundle    bundle1 = new Bundle();
        bundle1.putString("teachername", name);
        frag.setArguments(bundle1);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}