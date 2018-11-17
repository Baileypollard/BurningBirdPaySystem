package com.example.app.databaseassignment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.app.databaseassignment.fragment.EnterEmployeeDetailsFragment;
import com.example.app.databaseassignment.fragment.ViewEmployeeDetailsFragment;

public class MainTabAdapter extends FragmentStatePagerAdapter
{
    private int numberOfTabs;

    public MainTabAdapter(FragmentManager fm, int numberOfTabs)
    {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                EnterEmployeeDetailsFragment enterDetailsFragment = new EnterEmployeeDetailsFragment();
                return enterDetailsFragment;
            case 1:
                ViewEmployeeDetailsFragment viewDetailsFragment = new ViewEmployeeDetailsFragment();
                return viewDetailsFragment;
            default:
                return  null;
        }
    }

    @Override
    public int getCount()
    {
        return this.numberOfTabs;
    }
}
