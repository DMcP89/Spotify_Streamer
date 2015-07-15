package com.davemcpherson.spotifystreamer.commands;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.RetrofitError;

/**
 * Created by dave on 6/28/2015.
 */
public class SearchArtistCommand extends SpotifyCommand {
    private Artist artist;
    public SearchArtistCommand(SpotifyService ss) {
        if(ss == null){
            this.spotifyService = new SpotifyApi().getService();
        }else{
            this.spotifyService = ss;
        }
    }


    @Override
    public ArtistsPager execute(String artist) {
        try {
            return this.spotifyService.searchArtists(artist);
        }catch (RetrofitError error){
            SpotifyError spotifyError = SpotifyError.fromRetrofitError(error);
            return new ArtistsPager();
        }
    }
}
