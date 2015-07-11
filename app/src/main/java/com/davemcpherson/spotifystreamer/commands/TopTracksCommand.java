package com.davemcpherson.spotifystreamer.commands;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by dave on 6/29/2015.
 */
public class TopTracksCommand extends SpotifyCommand {

    public TopTracksCommand(SpotifyService ss){
        this.spotifyService = ss;
    }
    @Override
    public Tracks execute(String artist_id) {
        return this.spotifyService.getArtistTopTrack(artist_id);
    }
}
