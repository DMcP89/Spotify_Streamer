package com.davemcpherson.spotifystreamer.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.davemcpherson.spotifystreamer.R;
import com.davemcpherson.spotifystreamer.services.MediaPlayerService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 8/19/2015.
 */
public class PlayerFragment extends DialogFragment{

    private final static String TAG = PlayerFragment.class.getSimpleName();

    private List<Track> tracks;
    private int selectedIndex;
    private Track currentTrack;
    private View root;
    private boolean isPlaying;
    private boolean bound;
    private Intent mediaPlayerIntnet;
    private MediaPlayerService mService;


    public void setArguments(List<Track> trackList, int selectedPosition) {
        this.tracks = trackList;
        this.selectedIndex = selectedPosition;
        this.currentTrack = trackList.get(selectedPosition);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(this.getTag(),"onCreate");
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        mediaPlayerIntnet = new Intent(getActivity(), MediaPlayerService.class);
        mediaPlayerIntnet.putExtra(MediaPlayerService.URL, currentTrack.preview_url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_player, container, false);
        Log.d(this.getTag(),"onCreateView");
        //startMediaPlayer();
        setupSeekBar();
        setupPlayerforTrack(currentTrack);
        initImageButtons();
        isPlaying = true;
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.getTag(), "onStart");
        getActivity().bindService(mediaPlayerIntnet, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(this.getTag(), "onStop");
        if(bound){
            getActivity().unbindService(mConnection);
            bound=false;
        }
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
        ImageButton playPause = (ImageButton)root.findViewById(R.id.PlayBtn);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    ((ImageButton) v).setImageResource(android.R.drawable.ic_media_play);
                    mService.pauseMediaPlayer();
                    isPlaying = false;
                } else {
                    ((ImageButton) v).setImageResource(android.R.drawable.ic_media_pause);
                    mService.startMediaPlayer();
                    isPlaying = true;
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


    private void setupSeekBar(){
        final SeekBar seekBar = (SeekBar)root.findViewById(R.id.TrackSeekBar);
        seekBar.setMax(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              if(fromUser)
                mService.seekMediaPlayerTo(progress * 1000);
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
                if(mService != null) {
                   int currentPosition = (mService.getMediaPlayerPosition() / 1000)+1;
                   seekBar.setProgress(currentPosition);
                   String currentSeconds = (currentPosition < 10) ? "0:0" + currentPosition : "0:" + currentPosition;
                   setupTextView((TextView) root.findViewById(R.id.currentSeconds), currentSeconds);
                   Log.d(TAG, "CurrentPosition: "+currentPosition);
                   if(currentPosition == 30){
                        moveToNewTrack(1);
                   }
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void setupTextView(TextView v, String text){
        v.setText(text);
    }





    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(PlayerFragment.TAG, "serviceConnected");
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) iBinder;
            mService = binder.getService();
            mService.startService(mediaPlayerIntnet);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };



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
                moveToNewTrack(-1);
            }else{
                moveToNewTrack(1);
            }

        }
    }

    private void moveToNewTrack(int move){
        if(move > 0){
            selectedIndex = (selectedIndex == tracks.size()-1)? 0 : selectedIndex+1;
        }else{
            selectedIndex = (selectedIndex == 0)? tracks.size()-1 : selectedIndex-1;
        }
        setupPlayerforTrack(tracks.get(selectedIndex));
        mediaPlayerIntnet.putExtra(MediaPlayerService.URL, currentTrack.preview_url);
        try {
            mService.setupMediaPlayer(mediaPlayerIntnet);
            mService.startMediaPlayer();
        }catch (IOException e){

        }
    }
}
