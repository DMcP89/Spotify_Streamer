package com.davemcpherson.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.davemcpherson.spotifystreamer.fragments.SearchArtistFragment;
import com.davemcpherson.spotifystreamer.fragments.TopTracksFragment;
import com.davemcpherson.spotifystreamer.listeners.OnArtistSelectedListener;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;


public class MainActivity extends ActionBarActivity implements OnArtistSelectedListener {

    private static final String SEARCH_ARTIST_FRAGMENT= "SearchArtist";
    private static final String TOP_TRACKS_FRAGMENT= "TopTracks";

    private SpotifyService spotifyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spotifyService =new SpotifyApi().getService();
        SearchArtistFragment fragment = new SearchArtistFragment();
        fragment.setArguments(spotifyService);
		replaceFragment(R.id.fragment_container, fragment, SEARCH_ARTIST_FRAGMENT);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnArtistSelect(Artist artist) {
        TopTracksFragment newFrag = new TopTracksFragment();
        newFrag.setArguments(artist, spotifyService);
        Fragment oldFrag = getSupportFragmentManager().findFragmentByTag(SEARCH_ARTIST_FRAGMENT);
        replaceFragment(oldFrag.getId(),newFrag,TOP_TRACKS_FRAGMENT);
    }


    public void replaceFragment(int oldFragmentId, Fragment newFragment, String tag){
        getSupportFragmentManager().beginTransaction()
                .replace(oldFragmentId, newFragment,tag)
                .addToBackStack(tag)
                .commit();
    }
}
