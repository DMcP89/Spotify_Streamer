package com.davemcpherson.spotifystreamer.tasks;

import com.davemcpherson.spotifystreamer.commands.TopTracksCommand;

import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by dave on 6/27/2015.
 */
public class TopTracksTask extends SpotifyServiceTask{


    public TopTracksTask(TopTracksCommand c) {
        super(c);
    }

    @Override
    protected void onPostExecute(ArtistsPager artistsPager) {

    }
}
