package com.example.app.databaseassignment.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.example.app.databaseassignment.adapter.MainTabAdapter;
import com.example.app.databaseassignment.util.DatabaseManager;
import com.example.app.databaseassignment.util.SharedPreference;

public class MainActivity extends AppCompatActivity
{
    private ViewPager mainViewPager;
    private TabLayout mainTabLayout;
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        mainViewPager = (ViewPager) findViewById(R.id.main_pager);
        mainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);

        MainTabAdapter pagerAdapter = new MainTabAdapter(getSupportFragmentManager(), mainTabLayout.getTabCount());
        mainViewPager.setAdapter(pagerAdapter);
        mainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTabLayout));

        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                mainViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        sharedPreference = new SharedPreference(getApplicationContext());
    }

    public void onClickLogout(View v)
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        try
        {
            DatabaseManager.closeDatabase();
            sharedPreference.setEmployeeId("");
        }
        catch (CouchbaseLiteException e)
        {

        }
    }
}
