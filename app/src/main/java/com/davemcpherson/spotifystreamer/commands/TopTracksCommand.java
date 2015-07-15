package com.davemcpherson.spotifystreamer.commands;

import android.util.Log;

import java.util.HashMap;

import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RetrofitError;

/**
 * Created by dave on 6/29/2015.
 */
public class TopTracksCommand extends SpotifyCommand {

    public TopTracksCommand(SpotifyService ss){
        this.spotifyService = ss;
    }
    @Override
    public Tracks execute(String artist_id) {
        HashMap<String,Object> options = new HashMap<>();
        options.put("country","US");
        try {
            return this.spotifyService.getArtistTopTrack(artist_id, options);
        }catch(RetrofitError error){
            Log.i("TopTracksCommand","Error caught");
            SpotifyError spotifyError = SpotifyError.fromRetrofitError(error);
            return new Tracks();
        }
    }
}
