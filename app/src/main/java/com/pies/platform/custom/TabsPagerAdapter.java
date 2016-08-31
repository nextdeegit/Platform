package com.pies.platform.custom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pies.platform.OBjfragmentFragment;
import com.pies.platform.admin.Feedback_FeedsFragment;

/**
 * Created by Nsikak  Thompson on 8/30/2016.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm, Object feed) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {



        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
