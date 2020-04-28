package com.cannamaster.cannamastergrowassistant;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
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
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

//import com.astuetz.PagerSlidingTabStrip;

import com.cannamaster.cannamastergrowassistant.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    // ViewPager Variables
    private ViewPager mViewpager;
    private static String[] TAB_TITLE = {"Basics","Grow Assistant","Tips And Tricks","Advanced Techniques", "Sick Plants/Problems"};
    // Drawer Variables
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
   // private PagerSlidingTabStrip mTabStrip;


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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        /** Navigation Drawer */

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our navigation view
        mNavigationView = (NavigationView)findViewById(R.id.nvView);

        // Add navigation icons
        setupNavigationIcons(mNavigationView);

        // Tie the DrawerLayout and Toolbar together
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.
                drawer_close);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(mDrawerToggle);

        // Set up drawer item
        setupDrawerContent(mNavigationView);

        // Set up view pager listener
       // setupViewPagerListener();


        // TODO: 4/25/2020 - make an image randomizer for header 
        /** This loads the main view header image that scrolls into oblivion "backdrop.jpg"
        private void loadBackdrop() {
            final ImageView imageView = findViewById(R.id.main_activity_header_image);

            Glide.with(this).load(HeaderImage.getRandomHeaderImage()).centerCrop().into(imageView);
        }
         */

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
                        fragment = new GrowAssistantActivity();
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


        //FloatingActionButton fab = findViewById(R.id.fab);

        /**fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }



    /** ViewPager Tabs Listener
    public void setupViewPagerListener(){
        // (Optional) If you use an OnPageChangeListener with your view pager you should set it in the widget rather than on the pager directly.
        mDrawerToggle.setToolbarNavigationClickListener(new ViewPager.OnPageChangeListener() {
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
    }
*/


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

    /** Nav Drawer */
    private void setupDrawerContent(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {

            case R.id.nav_basics:

                mViewpager.setCurrentItem(0);
                break;
            case R.id.nav_growassistant:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.nav_tipsandtricks:
                mViewpager.setCurrentItem(2);
                break;
            case R.id.nav_advancedtechniques:
                mViewpager.setCurrentItem(3);
                break;
            case R.id.nav_sickplants:
                mViewpager.setCurrentItem(4);
                break;

            case R.id.nav_favorites:
                Intent favoritesIntent = new Intent(this, FavoritesListActivity.class);
                startActivity(favoritesIntent);
                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.nav_help:
                Intent helpIntent = new Intent(this, HelpPage.class);
                startActivity(helpIntent);
                break;
            case R.id.nav_about:
                Intent aboutIntent = new Intent(this, AboutPage.class);
                startActivity(aboutIntent);
            default:
                return;
        }

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    /** Viewpager Tabs Adapter */
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 5;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            // Create a new fragment and specify the planet to show based on
            // position
            Fragment fragment = null;

            Class fragmentClass;
            switch(position) {
                case 0:
                    fragmentClass = BasicsFragment.class;
                    break;
                case 1:
                    fragmentClass = SickPlantsFragment.class;
                    break;
                case 2:
                    fragmentClass = TipsAndTricksFragment.class;
                    break;
                case 3:
                    fragmentClass = AdvancedTechniquesFragment.class;
                    break;
                case 4:
                    fragmentClass = SickPlantsFragment.class;
                    break;
                default:
                    return null;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fragment;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLE[position];
        }

    }

    /****************************
     * double back press to exit
     ****************************/
    boolean doubleBackToExitPressedOnce = false;

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

}