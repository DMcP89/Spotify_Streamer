package com.davemcpherson.spotifystreamer.listeners;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by dave on 8/23/2015.
 */
public class PlayPauseClickListener implements View.OnClickListener {
    //Play = True Pause = false;
    private boolean STATE = true;
    private MediaPlayer player;

    public PlayPauseClickListener(MediaPlayer mp){
        this.player = mp;
    }

    @Override
    public void onClick(View v) {
        if(STATE){
            handlePlayClick(v);
        }else{
            handlePauseClick(v);
        }
        STATE = !STATE;
    }


    private void handlePlayClick(View v){
        Toast.makeText(v.getContext(), "Play", Toast.LENGTH_SHORT).show();
        ((ImageButton)v).setImageResource(android.R.drawable.ic_media_pause);
        if(!player.isPlaying()) {
            player.start();
        }
    }

    private void handlePauseClick(View v){
        Toast.makeText(v.getContext(), "Pause", Toast.LENGTH_SHORT).show();
        ((ImageButton)v).setImageResource(android.R.drawable.ic_media_play);
    }
}
