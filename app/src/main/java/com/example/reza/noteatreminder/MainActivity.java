package com.example.reza.noteatreminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback{

    /*
    private Timer myTimer;
    private SharedPreferences prefs;
    private String unitKey;
    private String chosenUnit;
    private String intervalKey;
    private String chosenInterval;
    private int interval;
    private int timerInterval;
    */
    private Intent settingsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setElevation(0f);
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        //settingsIntent = new Intent(this, SettingsActivity.class);
        //startActivity(settingsIntent);

        //startTimer();
    }

    /*
    public void startTimer(){

        myTimer = new Timer();

        getSettingValues();

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, (timerInterval/6));

    }

    public void getSettingValues(){

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        unitKey = this.getString(R.string.pref_units_key);
        chosenUnit = prefs.getString(unitKey,"not existing");

        intervalKey = this.getString(R.string.pref_interval_key);
        chosenInterval = prefs.getString(intervalKey,"2");

        interval = Integer.parseInt(chosenInterval);


        timerInterval = 10000;
        if(chosenUnit.equalsIgnoreCase("minutes")){
            timerInterval = interval * 60 * 1000;
        }
        else if(chosenUnit.equalsIgnoreCase("hours")){
            timerInterval = interval * 60 * 60 * 1000;
        }

        //System.out.println("timerinterval = " + timerInterval);

    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //startActivity(new Intent(this,SettingsActivity.class));
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //MainActivityFragment ff = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);


    }

    @Override
    public void onItemSelected(Uri dateUri) {
        //startActivity(new Intent(this,SettingsActivity.class));
    }

    /*
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

            //Do something to the UI thread here
            System.out.println("timerInterval: " + timerInterval);
            SoundPlayer.playTheSound();
        }
    };
    */
}
