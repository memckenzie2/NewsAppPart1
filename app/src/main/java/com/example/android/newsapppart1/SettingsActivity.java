package com.example.android.newsapppart1;

/**
 * Implementation of Settings Activity based on the Earthquake app from the udacity nano-degree.
 * The source code for those projects can be found here:
 * https://github.com/udacity/ud843-QuakeReport
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class NewsfeedPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            //Retrieves current preferences and updates display.
            Preference storiesQuantity = findPreference(getString(R.string.settings_quantity_key));
            bindPreferenceSummaryToValue(storiesQuantity);

            Preference storiesOrderBy = findPreference(getString(R.string.settings_order_key));
            bindPreferenceSummaryToValue(storiesOrderBy);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            //Takes the new user inputted values and updates the displayed preferences.
            String stringValue = value.toString();

            //Return the labels for the list item
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {

                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }


        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

    }
}
