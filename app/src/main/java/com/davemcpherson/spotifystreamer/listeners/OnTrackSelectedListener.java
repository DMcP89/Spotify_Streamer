package com.davemcpherson.spotifystreamer.listeners;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 8/19/2015.
 */
public interface OnTrackSelectedListener {
    public void OnTrackSelected(List<Track> tracks, int selectedPosition);
}
