package com.davemcpherson.spotifystreamer.listeners;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by dave on 7/10/2015.
 */
public interface OnArtistSelectedListener {
    public void OnArtistSelect(Artist artist);
}
