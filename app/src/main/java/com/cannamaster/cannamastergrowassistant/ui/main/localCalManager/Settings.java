package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cannamaster.cannamastergrowassistant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**********************************************
 * Settings page relating to Calendar Manager
 **********************************************/

public class Settings extends CalendarManagerAppActivity {
    static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.cal_mgr_activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getLayoutInflater().inflate(R.layout.settings_page_layout, layout);
        context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        layout.removeView(fab);
        if(getFragmentManager().findFragmentById(android.R.id.content) == null) {
            getFragmentManager().beginTransaction().replace(R.id.settingsList, new SettingsFragment()).commit();
        }

    }

    public static class SettingsFragment extends PreferenceFragment implements AdapterView.OnItemLongClickListener {
        private ArrayList<Preference> mPreferences = new ArrayList<>();
        private String[] mPreferenceKeys = new String[] {"account_name","account_type","owner_account", "version_Info"};
        private SharedPreferences.OnSharedPreferenceChangeListener mListener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
            mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    for (Preference pref : mPreferences) {
                        if (pref.getKey().equals(key)) {
                            if (pref instanceof EditTextPreference) {
                                pref.setSummary(sharedPreferences.getString(key, "The URL for the calendar used"));
                            }

                            //break;
                        }

                    }
                }
            };
            SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
            prefs.registerOnSharedPreferenceChangeListener(mListener);
            for (String prefKey : mPreferenceKeys) {
                Preference pref = (Preference) getPreferenceManager().findPreference(prefKey);
                mPreferences.add(pref);
                mListener.onSharedPreferenceChanged(prefs, prefKey);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            if(view != null)
            {
                View lv = view.findViewById (android.R.id.list);
                if (lv instanceof ListView)
                {
                    ((ListView)lv).setOnItemLongClickListener(this);
                }
                else
                {
                    //The view created is not a list view!
                }
            }
            //view.setBackgroundResource(R.drawable.background);
            return view;
        }

        @Override
        public void onResume()
        {
            super.onResume();
            SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
            prefs.registerOnSharedPreferenceChangeListener(mListener);
            for (String prefKey : mPreferenceKeys) {
                Preference pref = (Preference) getPreferenceManager().findPreference(prefKey);
                mPreferences.add(pref);
                mListener.onSharedPreferenceChanged(prefs, prefKey);
            }
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            return false;
        }
    }



}