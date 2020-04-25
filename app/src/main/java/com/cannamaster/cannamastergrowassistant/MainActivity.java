package com.cannamaster.cannamastergrowassistant;

import android.os.Bundle;
import android.widget.ImageView;


import com.cannamaster.cannamastergrowassistant.ui.main.AdvancedTechniquesFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.BasicsFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.SickPlantsFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.TipsAndTricksFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.cannamaster.cannamastergrowassistant.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewpager;
    private static String[] TAB_TITLE = {"Basics","Grow Assistant","Tips And Tricks","Advanced Techniques", "Sick Plants/Problems"};

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


        //FloatingActionButton fab = findViewById(R.id.fab);

        /**fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
}