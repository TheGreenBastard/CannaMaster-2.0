package com.cannamaster.cannamastergrowassistant.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.cannamaster.cannamastergrowassistant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SettingsActivity<AppActivity> extends AppCompatActivity {
    static Context context;
    FloatingActionButton fab;
    CoordinatorLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = findViewById(R.id.settingsList);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getLayoutInflater().inflate(R.layout.content_settings, layout);
        context = this;
        fab = (FloatingActionButton) fab.findViewById(R.id.fab);
        layout.removeView(fab);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, Settings.class);
                context.startActivity(intent);
                return true;
            case R.id.menu_refresh:
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return false;
    }

class SettingsFragment extends PreferenceFragmentCompat implements AdapterView.OnItemLongClickListener {
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
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}

