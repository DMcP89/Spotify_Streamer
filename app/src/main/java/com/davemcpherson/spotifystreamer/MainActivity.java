package com.davemcpherson.spotifystreamer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.davemcpherson.spotifystreamer.fragments.PlayerFragment;
import com.davemcpherson.spotifystreamer.fragments.SearchArtistFragment;
import com.davemcpherson.spotifystreamer.fragments.TopTracksFragment;
import com.davemcpherson.spotifystreamer.listeners.OnArtistSelectedListener;
import com.davemcpherson.spotifystreamer.listeners.OnTrackSelectedListener;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;


public class MainActivity extends ActionBarActivity implements OnArtistSelectedListener, OnTrackSelectedListener{

    private static final String SEARCH_ARTIST_FRAGMENT= "SearchArtist";
    private static final String TOP_TRACKS_FRAGMENT= "TopTracks";
    private static final String PLAYER_FRAGMENT= "Player";

    private boolean twoPaneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.top_tracks_container) != null){
            twoPaneMode = true;
        }

        SearchArtistFragment fragment = new SearchArtistFragment();
        if(savedInstanceState == null) {
            addFragment(fragment, SEARCH_ARTIST_FRAGMENT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnArtistSelect(Artist artist) {
        TopTracksFragment newFrag = new TopTracksFragment();
        newFrag.setArguments(artist);
        if(!twoPaneMode) {
            Fragment oldFrag = getSupportFragmentManager().findFragmentByTag(SEARCH_ARTIST_FRAGMENT);
            replaceFragment(oldFrag.getId(), newFrag, TOP_TRACKS_FRAGMENT);
        }else{
            replaceFragment(R.id.top_tracks_container, newFrag, TOP_TRACKS_FRAGMENT);
        }
    }

    @Override
    public void OnTrackSelected(List<Track> tracks, int postion) {
        PlayerFragment newFrag = new PlayerFragment();
        newFrag.setArguments(tracks,postion);
        if(!twoPaneMode) {
            Fragment oldFrag = getSupportFragmentManager().findFragmentByTag(TOP_TRACKS_FRAGMENT);
            replaceFragment(oldFrag.getId(), newFrag, PLAYER_FRAGMENT);
        }else{
            newFrag.show(getSupportFragmentManager(),"dialog");
        }
    }

    private void replaceFragment(int oldFragmentId, Fragment newFragment, String tag){
        getSupportFragmentManager().beginTransaction()
                .replace(oldFragmentId, newFragment,tag)
                .addToBackStack(tag)
                .commit();
    }

    private void addFragment(Fragment fragment, String tag)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }

    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        boolean isConnected = ni != null &&
                              ni.isConnectedOrConnecting();
        return isConnected;
    }




}
