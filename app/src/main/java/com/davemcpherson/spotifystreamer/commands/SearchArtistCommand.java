package com.davemcpherson.spotifystreamer.commands;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dave on 6/28/2015.
 */
public class SearchArtistCommand extends SpotifyCommand {

    private ArtistsPager artistsPager;

    public SearchArtistCommand(SpotifyService ss) {
        if(ss == null){
            this.spotifyService = new SpotifyApi().getService();
        }else{
            this.spotifyService = ss;
        }
    }


    @Override
    public ArtistsPager execute(String artistName) {
        spotifyService.searchArtists(artistName, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager ap, Response response) {
                artistsPager = ap;
            }

            @Override
            public void failure(RetrofitError error) {
                artistsPager = new ArtistsPager();
            }
        });
        return artistsPager;
    }
}
