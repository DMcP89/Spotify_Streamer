package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by dave on 6/25/2015.
 */
public class SearchArtistTask extends AsyncTask<Void, Void,Void>{
    @Override
    protected Void doInBackground(Void... params) {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        ArtistsPager AP = spotify.searchArtists("coldplay");

        return null;
    }
}
