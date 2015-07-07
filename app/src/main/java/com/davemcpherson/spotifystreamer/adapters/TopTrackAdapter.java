package com.davemcpherson.spotifystreamer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 6/26/2015.
 */
public class TopTrackAdapter extends ArrayAdapter<Track> {
    public TopTrackAdapter(Context context,List<Track> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return null;
    }
}
