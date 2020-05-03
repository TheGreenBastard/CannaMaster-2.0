package com.cannamaster.cannamastergrowassistant;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;



/*********************
 * Settings Fragment
 *********************/

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    String KEY_PREF_EDITTEXT= "empty_string_edittext_preference";
    String KEY_PREF_LIST= "empty_string_list_preference";
    String KEY_PREF_DARKMODE= "empty_string_darkmode_preference";
    String KEY_PREF_FONT_SIZE= "empty_string_font_size";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.application_preferences);
    }

    @Override
    public void onStop() {
        super.onStop();
        // unregister
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // change text or list values in PreferenceActivity ("Screen/Page")
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPref, String key) {
        Log.d(">>APPLICATION SETTINGS", "key=" + key);
        // Edit Text
        if (key.equals(KEY_PREF_EDITTEXT)) {
            Preference editTextPref = findPreference(key);
            editTextPref.setSummary(sharedPref.getString(key, ""));
            // list value
        } else if (key.equals(KEY_PREF_LIST)) {
            Preference listPref = findPreference(key);
            listPref.setSummary(sharedPref.getString(key, ""));
        } else if (key.equals(KEY_PREF_DARKMODE)) {
            Preference darkPref = findPreference(key);
            darkPref.setSummary(sharedPref.getString(key, ""));
        } else if (key.equals(KEY_PREF_FONT_SIZE)) {
            Preference fontPref = findPreference(key);
            fontPref.setSummary(sharedPref.getString(key, ""));
        }


    }
}
