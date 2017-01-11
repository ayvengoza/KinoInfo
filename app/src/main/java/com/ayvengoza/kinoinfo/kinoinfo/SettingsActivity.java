package com.ayvengoza.kinoinfo.kinoinfo;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by ang on 10.01.17.
 */

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_pref);
        Preference preference = findPreference("pref_page_number_key");
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
        preference = findPreference("pref_sort_by_key");
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
        .getString(preference.getKey(), ""));
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String newVal = newValue.toString();

        if(preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(newVal);
            if(index >= 0){
                preference.setSummary(listPreference.getEntries()[index]);
            } else {
                preference.setSummary(newVal);
            }
        } else if(preference instanceof EditTextPreference){
            EditTextPreference textPreference = (EditTextPreference) preference;
            preference.setSummary(newVal);
        }
        return true;
    }
}
