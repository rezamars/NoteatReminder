package com.example.reza.noteatreminder;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //private Button b1,b2,b3,b4;
    private Button b2,b3;
    private ImageView iv;
    private MediaPlayer mediaPlayer; //Mediaplayer

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx3, tx4;
    private int oneTimeOnly = 0;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main,container,false);

        //b1 =  rootView.findViewById(R.id.button);
        b2 =  rootView.findViewById(R.id.button2);
        b3 =  rootView.findViewById(R.id.button3);
        //b4 =  rootView.findViewById(R.id.button4);
        iv =  rootView.findViewById(R.id.imageView);

        //tx1 =  rootView.findViewById(R.id.textView2);
        tx3 =  rootView.findViewById(R.id.textView3);
        tx4 =  rootView.findViewById(R.id.textView4);
        //tx3.setText(R.string.bell);

        mediaPlayer = MediaPlayer.create(MainActivityFragment.super.getActivity(), R.raw.door_bell);
        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        try{
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);
        }
        catch (Exception e){
            System.out.print("NullPointerException caught" + e.getMessage());
        }

        seekbar =  rootView.findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        b2.setEnabled(false);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivityFragment.super.getContext(), "Playing sound"
                        ,Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    System.out.println("Finaltime is = " + finalTime);
                    oneTimeOnly = 1;
                }

                /*
                tx2.setText(String.format(Locale.getDefault(),"%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );
                */

                tx3.setText(String.format(Locale.getDefault(),"%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                seekbar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);
                b2.setEnabled(true);
                b3.setEnabled(false);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivityFragment.super.getContext(), "Pausing sound"
                  //      ,Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                //seekbar.setProgress(0);

                b2.setEnabled(false);
                b3.setEnabled(true);
            }
        });

        /*
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(MainActivityFragment.super.getContext(),"You have Jumped forward 5 seconds"
                            ,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivityFragment.super.getContext(),"Cannot jump forward 5 seconds"
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(MainActivityFragment.super.getContext(),"You have Jumped backward 5 seconds"
                            ,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivityFragment.super.getContext(),"Cannot jump backward 5 seconds"
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        return rootView;
    }

    int roundedCurrentTime;
    int roundedFinalTime;

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx3.setText(String.format(Locale.getDefault(),"%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
            roundedCurrentTime = (mediaPlayer.getCurrentPosition()/1000);
            roundedFinalTime = (int) (finalTime/1000);
            //System.out.println("Rounded: " + roundedCurrentTime + "," + roundedFinalTime);
            if(roundedCurrentTime == roundedFinalTime){

                b2.setEnabled(false);
                b3.setEnabled(true);
                mediaPlayer.seekTo(0);
                //roundedCurrentTime = 0;
                //seekbar.setProgress(0);
                System.out.println("In the if!!!!");
                //startTime = 0;

            }
        }
    };

}
