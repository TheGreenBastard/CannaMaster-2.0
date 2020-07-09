package com.cannamaster.cannamastergrowassistant.ui.main.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.MainActivity;
import java.util.ArrayList;

/**********************************************
 * Settings page relating to the entire app
 **********************************************/


public class Settings extends MainActivity {
    static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_frame_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set the drawer icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
       // getLayoutInflater().inflate(R.layout.settings_frame_layout);
        context = this;
        if(getFragmentManager().findFragmentById(android.R.id.content) == null) {
            getFragmentManager().beginTransaction().replace(R.id.settingsList, new SettingsFragment()).commit();
        }

    }

    public static class SettingsFragment extends PreferenceFragment implements AdapterView.OnItemLongClickListener {
        private ArrayList<Preference> mPreferences = new ArrayList<>();
        private String[] mPreferenceKeys = new String[] {"calendar_number_key","account_name_key", "account_type_key", "owner_account_key", "article_text_size_key", "day_night_key", "version_Info", "copyright" };
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}