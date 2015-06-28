package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;

import kaaes.spotify.webapi.android.models.TracksPager;

/**
 * Created by dave on 6/27/2015.
 */
public class TopTracksTask extends AsyncTask<String, Void, TracksPager>{


    @Override
    protected TracksPager doInBackground(String... params) {
        return null;
    }
}
