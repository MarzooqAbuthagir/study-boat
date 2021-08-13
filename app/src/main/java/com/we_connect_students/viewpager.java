package com.we_connect_students;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class viewpager extends FragmentStatePagerAdapter {

    int tabCount;

    public viewpager(FragmentManager fragmentManager, int tabCount) {

        super(fragmentManager);
        //Initializing tab count
        this.tabCount= tabCount;

    }



    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                textpackage tab1 = new textpackage();
                return tab1;
            case 1:
                videopackage tab2 = new videopackage();
                return tab2;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return tabCount;
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
