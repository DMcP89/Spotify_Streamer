package com.davemcpherson.spotifystreamer.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 6/26/2015.
 */
public class TopTrackAdapter extends ArrayAdapter<Track> {
    public TopTrackAdapter(Context context, int resource, List<Track> objects) {
        super(context, resource, objects);
    }
}
