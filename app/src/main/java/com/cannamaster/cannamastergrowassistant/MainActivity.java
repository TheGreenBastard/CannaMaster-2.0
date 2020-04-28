package com.cannamaster.cannamastergrowassistant;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.cannamaster.cannamastergrowassistant.ui.main.AdvancedTechniquesFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.BasicsFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.SickPlantsFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.TipsAndTricksFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

import com.bumptech.glide.Glide;

import com.cannamaster.cannamastergrowassistant.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {


    private static String[] TAB_TITLE = {"Basics","Grow Assistant","Tips And Tricks","Advanced Techniques", "Sick Plants/Problems"};
    /****************************
     * double back press to exit
     ****************************/
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private TabLayout mTabStrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Set up initial Views */
        setContentView(R.layout.activity_main);
        /** Associate The ViewPager with new instance of adapter (ie link the viewpager with the adapter (SectionsPagerAdapter) */
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.vpPager);
        mViewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);
        // End Viewpager setup
        //Find toolbar to set drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.sections_toolbar);
        setSupportActionBar(toolbar);
        //Set the drawer icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        /** Navigation Drawer */

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our navigation view
        mNavigationView = (NavigationView) findViewById(R.id.nvView);

        // Add navigation icons
        setupNavigationIcons(mNavigationView);

        // Tie the DrawerLayout and Toolbar together
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(mDrawerToggle);

        // Set up drawer item
        setupNavigationIcons(mNavigationView);

        // Set up view pager listener
        //setupViewPagerListener();
        /** Viewpager Tabs Adapter */
        // How many tabs do we want?
        class SectionsPagerAdapter extends FragmentPagerAdapter {
            private int NUM_ITEMS = 5;

            public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
                super(fm, behavior);
            }




            // Returns total number of pages
            @Override
            public int getCount() {
                return NUM_ITEMS;
            }

            /** Returns the fragment to display for that page */
            @NonNull
            @Override
            public Fragment getItem(int position) {
                // Create a new fragment and specify the planet to show based on
                // position
                Fragment fragment = null;

                switch (position) {
                    case 0:
                        fragment = new BasicsFragment();
                        break;
                    case 1:
                        fragment = new TipsAndTricksFragment();
                        break;
                    case 2:
                        fragment = new AdvancedTechniquesFragment();
                        break;
                    case 3:
                        fragment = new SickPlantsFragment();
                        break;
                    case 4:
                        fragment = new TipsAndTricksFragment();
                        break;

                }
                return fragment;
            }


            // Returns the page title for the top indicator
            @Override
            public CharSequence getPageTitle(int position) {
                return TAB_TITLE[position];
            }

        }
    }

    /*********************
     * Dark Mode Menu
     *********************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Dark Mode Menu
    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    // This loads the main view header image that scrolls into oblivion "backdrop.jpg"
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(HeaderImage.getRandomHeaderImage()).centerCrop().into(imageView);
    }

    /** ViewPager Tabs Listener
    public void setupViewPagerListener(){
        // (Optional) If you use an OnPageChangeListener with your view pager you should set it in the widget rather than on the pager directly.
        mTabStrip.(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setTitle(TAB_TITLE[position]);
                mNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }*/

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /** Icons for Drawer Menu */
    private void setupNavigationIcons(NavigationView mNavigationView) {

        mNavigationView.getMenu().findItem(R.id.nav_basics).setIcon(R.drawable.ic_launcher);
        mNavigationView.getMenu().findItem(R.id.nav_growassistant).setIcon(R.drawable.ic_launcher);
        mNavigationView.getMenu().findItem(R.id.nav_tipsandtricks).setIcon(R.drawable.ic_launcher);
        mNavigationView.getMenu().findItem(R.id.nav_advancedtechniques).setIcon(R.drawable.ic_launcher);
        mNavigationView.getMenu().findItem(R.id.nav_sickplants).setIcon(R.drawable.ic_launcher);

        mNavigationView.getMenu().findItem(R.id.nav_favorites).setIcon(R.drawable.ic_star);
        mNavigationView.getMenu().findItem(R.id.nav_settings).setIcon(R.drawable.settings);
        mNavigationView.getMenu().findItem(R.id.nav_help).setIcon(R.drawable.help_circle);
        mNavigationView.getMenu().findItem(R.id.nav_about).setIcon(R.drawable.information);
    }



    // TODO: 4/25/2020 - make an image randomizer for header

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);  // 2 second pause
    }



        //FloatingActionButton fab = findViewById(R.id.fab);

        /**fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });*/
    }
