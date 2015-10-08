package com.davemcpherson.spotifystreamer.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by dave on 9/8/2015.
 */
public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener {

    private final IBinder mBinder = new LocalBinder();

    public static final String URL = "trackUrl";
    public boolean complete = false;

    private MediaPlayer myMediaPlayer = new MediaPlayer();


    public MediaPlayerService(){
    }

    @Override
    public void onCreate() {
        Log.d(this.getClass().getSimpleName(),"Service Created");
        myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(this.getClass().getSimpleName(), "Service Starting");

        if(!myMediaPlayer.isPlaying()){
            try {
                setupMediaPlayer(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            myMediaPlayer.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getSimpleName(),"Service Stopping");
        if(myMediaPlayer.isPlaying()){
            myMediaPlayer.stop();
        }
        myMediaPlayer.release();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(MediaPlayerService.class.getSimpleName(),"MediaPlayer complete");
        complete = true;
    }


    public int getMediaPlayerPosition(){
        if(myMediaPlayer !=null) {
            return myMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void pauseMediaPlayer(){
        if(myMediaPlayer.isPlaying()){
            myMediaPlayer.pause();
        }
    }

    public void startMediaPlayer(){
        if(!myMediaPlayer.isPlaying()){
            myMediaPlayer.start();
        }
    }

    public void seekMediaPlayerTo(int position){
        if(myMediaPlayer != null) {
            myMediaPlayer.seekTo(position);
        }
    }

    public void setupMediaPlayer(Intent intent) throws IOException{
        if(myMediaPlayer.isPlaying()) {
            myMediaPlayer.stop();
            myMediaPlayer.reset();
        }
        myMediaPlayer.setDataSource(intent.getStringExtra(URL));
        myMediaPlayer.prepare();
    }


    public class LocalBinder extends Binder{
        public MediaPlayerService getService(){
            return MediaPlayerService.this;
        }
    }
}
