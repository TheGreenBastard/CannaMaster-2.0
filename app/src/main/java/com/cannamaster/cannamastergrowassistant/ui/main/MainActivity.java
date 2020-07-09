package com.cannamaster.cannamastergrowassistant.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.Prefs.Settings;
import com.cannamaster.cannamastergrowassistant.ui.main.favorites.FavoritesListActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
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
import com.bumptech.glide.Glide;
import static androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode;

public class MainActivity extends AppCompatActivity {

    /***********************************************************************
     *  this activity mainly takes care of the drawer and recycler views
     *
     *  content can be found on the "endpages"
     **********************************************************************/


    private static String[] TAB_TITLE = {"Basics", "Grow Assistant", "Tips And Tricks", "Advanced Techniques", "Sick Plants/Problems"};
    /****************************
     * double back press to exit
     ****************************/
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (InitApplication.getInstance().isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        /** Set up initial Views */
        setContentView(R.layout.activity_main);
        /** Associate The ViewPager with new instance of adapter (ie link the viewpager with the adapter (SectionsPagerAdapter) */
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mViewPager = findViewById(R.id.vpPager);
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
        mNavigationView = findViewById(R.id.nvView);
        // Add navigation icons
        setupNavigationIcons(mNavigationView);
        // Tie the DrawerLayout and Toolbar together
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(mDrawerToggle);
        // load the random header image
        loadBackdrop();
        // Set up drawer item
        setupNavigationIcons(mNavigationView);

        // Set up view pager listener
        // setupViewPagerListener();
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
            @Override
            public Fragment getItem(int position) {
                // Create a new fragment and specify the planet to show based on
                // position of the viewpager tabs
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
                        fragment = new GrowAssistantFragment();
                        break;
                }
                return fragment;
            }


            // Returns the page title for the top tab indicator
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
        switch (getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY:
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
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Dark Mode Menu
    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        recreate();
    }

    /** This loads the main view header image that scrolls into oblivion "backdrop.jpg" **/
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(HeaderImage.getRandomHeaderImage()).centerCrop().into(imageView);
    }

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

    /******************************
     * Icons for Drawer Menu
     *****************************/
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

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_basics:
                mViewPager.setCurrentItem(0);
                break;

            case R.id.nav_tipsandtricks:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_advancedtechniques:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.nav_sickplants:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.nav_growassistant:
                mViewPager.setCurrentItem(4);
                break;

            case R.id.nav_favorites:
                Intent favoritesIntent = new Intent(this, FavoritesListActivity.class);
                startActivity(favoritesIntent);
                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivity(settingsIntent);
                new Settings();
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


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);  // 2 second pause
    }


}
