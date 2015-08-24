package com.davemcpherson.spotifystreamer.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.davemcpherson.spotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 8/19/2015.
 */
public class PlayerFragment extends DialogFragment{

    private List<Track> tracks;
    private int selectedIndex;
    private Track currentTrack;
    private View root;

    private MediaPlayer myPlayer = new MediaPlayer();

    public void setArguments(List<Track> trackList, int selectedPosition) {
        this.tracks = trackList;
        this.selectedIndex = selectedPosition;
        this.currentTrack = trackList.get(selectedPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_player, container, false);
        startMediaPlayer();
        setupSeekBar();
        setupPlayerforTrack(currentTrack);
        initImageButtons();


        return root;
    }

    private void setupPlayerforTrack(Track track){
        currentTrack = track;
        initTextViews();
        populateAlbumImage((ImageView) root.findViewById(R.id.AlbumImage));

    }

    private void initTextViews(){
        setupTextView((TextView) root.findViewById(R.id.ArtistNameTxt), currentTrack.artists.get(0).name);
        setupTextView((TextView) root.findViewById(R.id.AlbumNameTxt), currentTrack.album.name);
        setupTextView((TextView) root.findViewById(R.id.TrackNameTxt), currentTrack.name);
        setupTextView((TextView) root.findViewById(R.id.currentSeconds), "0:00");
        setupTextView((TextView) root.findViewById(R.id.totalSeconds), "0:30");
    }

    private void initImageButtons(){
        final ImageButton playPause = (ImageButton)root.findViewById(R.id.PlayBtn);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPlayer.isPlaying()) {
                    playPause.setImageResource(android.R.drawable.ic_media_play);
                    myPlayer.pause();
                } else {
                    playPause.setImageResource(android.R.drawable.ic_media_pause);
                    myPlayer.start();
                }
            }
        });
        ImageButton previous = (ImageButton)root.findViewById(R.id.PreviousBtn);
        previous.setOnClickListener(new SongNavigationClickListener(SongNavigationClickListener.PREVIOUS_CODE));
        ImageButton next = (ImageButton)root.findViewById(R.id.NextBtn);
        next.setOnClickListener(new SongNavigationClickListener(SongNavigationClickListener.NEXT_CODE));
    }

    private void populateAlbumImage(ImageView view){
        if(!currentTrack.album.images.isEmpty()){
            if(Patterns.WEB_URL.matcher(currentTrack.album.images.get(0).url).matches()){
                Picasso.with(getActivity()).load(currentTrack.album.images.get(0).url).into(view);
            }
        }
    }



    private void startMediaPlayer(){
        myPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            myPlayer.setDataSource(currentTrack.preview_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPlayer.start();
    }

    private void setupSeekBar(){
        final SeekBar seekBar = (SeekBar)root.findViewById(R.id.TrackSeekBar);
        seekBar.setMax(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(myPlayer != null && fromUser){
                    myPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        final Handler handler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(myPlayer != null){
                    int currentPosition = myPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                    String currentSeconds = (currentPosition < 10)? "0:0"+currentPosition : "0:"+currentPosition;
                    setupTextView((TextView)root.findViewById(R.id.currentSeconds),currentSeconds);
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void setupTextView(TextView v, String text){
        v.setText(text);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        myPlayer.stop();
        myPlayer.release();
        super.onDestroy();
    }

    private class SongNavigationClickListener implements View.OnClickListener{

        public static final int PREVIOUS_CODE = 0;
        public static final int NEXT_CODE = 1;

        private int state;

        public SongNavigationClickListener(int code){
            this.state = code;
        }

        @Override
        public void onClick(View v) {
            if(state == PREVIOUS_CODE){
                selectedIndex = (selectedIndex == 0)? tracks.size()-1 : selectedIndex-1;
            }else{
                selectedIndex = (selectedIndex == tracks.size()-1)? 0 : selectedIndex+1;
            }
            setupPlayerforTrack(tracks.get(selectedIndex));
            myPlayer.reset();
            startMediaPlayer();
        }
    }
}
