package com.davemcpherson.spotifystreamer.commands;

import java.util.HashMap;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dave on 6/29/2015.
 */
public class TopTracksCommand extends SpotifyCommand {

    private Tracks tracks;

    public TopTracksCommand(SpotifyService ss){
        this.spotifyService = ss;
    }
    @Override
    public Tracks execute(String artist_id) {
        HashMap<String,Object> options = new HashMap<>();
        options.put("country","US");
        spotifyService.getArtistTopTrack(artist_id, options, new Callback<Tracks>() {

            @Override
            public void success(Tracks results, Response response) {
                tracks = results;
            }

            @Override
            public void failure(RetrofitError error) {
                tracks = new Tracks();
            }
        });
        return tracks;
    }
}
