package com.davemcpherson.spotifystreamer.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by dave on 6/27/2015.
 */
public class ArtisitAdapter extends ArrayAdapter<Artist> {
    public ArtisitAdapter(Context context, List<Artist> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return null;
    }
}
