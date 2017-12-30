package com.example.reza.noteatreminder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{

    public static SettingsActivity settingsActivity;
    private AppCompatDelegate mDelegate;

    private static Timer myTimer ;
    private static SharedPreferences prefs;
    private static String unitKey;
    private static String chosenUnit;
    private static String intervalKey;
    private static String chosenInterval;
    private static int interval;
    private static int timerInterval;
    private static String reminderKey;
    private static boolean reminderOn = false;
    private boolean firstTime = true;

    public SettingsActivity(){
        this.settingsActivity = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);

        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_interval_key)));

        startTimer();
    }


    public void startTimer(){

        myTimer = new Timer();

        timerInterval = 10000;
        getSettingValues();

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, timerInterval);

    }

    public void getSettingValues(){

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        unitKey = this.getString(R.string.pref_units_key);



        intervalKey = this.getString(R.string.pref_interval_key);
        chosenInterval = prefs.getString(intervalKey,"2");

        if(firstTime){
            interval = Integer.parseInt(chosenInterval);
            chosenUnit = prefs.getString(unitKey,"not existing");
            firstTime = false;
        }


        timerInterval = 10000;
        if(chosenUnit.equalsIgnoreCase("minutes")){
            timerInterval = interval * 60 * 1000;
        }
        else if(chosenUnit.equalsIgnoreCase("hours")){
            timerInterval = interval * 60 * 60 * 1000;
        }



        //System.out.println("getting...timerinterval = " + timerInterval);

    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {


                //This method runs in the same thread as the UI.
                System.out.println("timerinterval = " + timerInterval);

                reminderKey = settingsActivity.getString(R.string.pref_reminder_status_key);
                reminderOn = prefs.getBoolean(reminderKey,false);
                if(reminderOn){

                    SoundPlayer.playTheSound();
                }
                else{
                    System.out.println("The reminder is OFF!");
                }


        }
    };

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).

            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);

                if(listPreference.getKey().equalsIgnoreCase("interval")){

                    CharSequence charSequence = listPreference.getEntries()[prefIndex];
                    final StringBuilder sb = new StringBuilder(charSequence.length());
                    sb.append(charSequence);
                    String str = sb.toString();
                    interval = Integer.parseInt(str);

                    if(!firstTime){
                        myTimer.cancel();
                        myTimer = null;
                        settingsActivity.startTimer();
                    }
                }
                else if(listPreference.getKey().equalsIgnoreCase("units")){

                    System.out.println("there 1...");
                    if(!firstTime){
                        myTimer.cancel();
                        myTimer = null;
                        CharSequence charSequence = listPreference.getEntries()[prefIndex];
                        final StringBuilder sb = new StringBuilder(charSequence.length());
                        sb.append(charSequence);
                        chosenUnit = sb.toString();;
                        settingsActivity.startTimer();
                        System.out.println("interval= " + interval);
                        System.out.println("unit= " + listPreference.getEntries()[prefIndex]);

                    }

                }

            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }

        return true;
    }

    /*@Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }*/


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

}
