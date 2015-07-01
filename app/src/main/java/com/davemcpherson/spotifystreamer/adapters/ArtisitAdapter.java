package com.davemcpherson.spotifystreamer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemcpherson.spotifystreamer.R;
import com.davemcpherson.spotifystreamer.tasks.ImageDownloadTask;

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
        Artist artist = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_search_item, parent, false);
        }

        ImageView artistImage = (ImageView)convertView.findViewById(R.id.ArtistImg);
        ImageDownloadTask task = new ImageDownloadTask(artistImage);
        if(!artist.images.isEmpty()) {
            task.execute(artist.images.get(0));
        }else{
            artistImage.setImageResource(R.mipmap.ic_launcher);
        }


        TextView artitstName = (TextView)convertView.findViewById(R.id.ArtistNameTxt);
        artitstName.setText(artist.name);

        return convertView;
    }
}
