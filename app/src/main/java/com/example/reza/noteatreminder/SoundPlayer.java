package com.example.reza.noteatreminder;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
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


public class SoundPlayer {

    private Button b2,b3;
    private ImageView iv;
    private static MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx3, tx4;
    private int oneTimeOnly = 0;

    private static MainActivityFragment mainActivityFragment;

    public SoundPlayer(){

    }

    public View createPlayerView(final MainActivityFragment mainActivityFragment, LayoutInflater inflater,
                           ViewGroup container){

        this.mainActivityFragment = mainActivityFragment;

        View rootView = inflater.inflate(R.layout.fragment_main,container,false);

        b2 =  rootView.findViewById(R.id.button2);
        b3 =  rootView.findViewById(R.id.button3);
        iv =  rootView.findViewById(R.id.imageView);

        tx3 =  rootView.findViewById(R.id.textView3);
        tx4 =  rootView.findViewById(R.id.textView4);

        mediaPlayer = MediaPlayer.create(mainActivityFragment.getActivity(), R.raw.noteat_2);
        AudioManager audioManager = (AudioManager) mainActivityFragment.getContext().getSystemService(Context.AUDIO_SERVICE);
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
                Toast.makeText(mainActivityFragment.getContext(), "Playing sound"
                        ,Toast.LENGTH_SHORT).show();

                playTheSound();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    System.out.println("Finaltime is = " + finalTime);
                    oneTimeOnly = 1;
                }

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
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);

                b2.setEnabled(false);
                b3.setEnabled(true);
            }
        });


        return rootView;

    }

    public static void playTheSound(){

        mediaPlayer.start();

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

            if(roundedCurrentTime == roundedFinalTime){

                b2.setEnabled(false);
                b3.setEnabled(true);
                mediaPlayer.seekTo(0);
                //System.out.println("In the if!!!!");

            }
        }
    };

}
