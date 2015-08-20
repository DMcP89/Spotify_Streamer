package com.davemcpherson.spotifystreamer.listeners;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 8/19/2015.
 */
public interface OnTrackSelectedListener {
    public void OnTrackSelected(Track track);
}
