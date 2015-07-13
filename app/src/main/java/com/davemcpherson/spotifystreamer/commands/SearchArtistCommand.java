package com.davemcpherson.spotifystreamer.commands;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by dave on 6/28/2015.
 */
public class SearchArtistCommand extends SpotifyCommand {

    public SearchArtistCommand(SpotifyService ss) {
        if(ss == null){
            this.spotifyService = new SpotifyApi().getService();
        }else{
            this.spotifyService = ss;
        }
    }


    @Override
    public ArtistsPager execute(String artist) {
        return this.spotifyService.searchArtists(artist);
    }
}
