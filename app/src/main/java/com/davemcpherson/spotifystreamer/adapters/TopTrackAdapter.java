package com.davemcpherson.spotifystreamer.adapters;

import android.content.Context;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemcpherson.spotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 6/26/2015.
 */
public class TopTrackAdapter extends ArrayAdapter<Track> {

    private String artistName;

    public TopTrackAdapter(Context context,List<Track> objects, String an) {
        super(context, 0, objects);
        this.artistName = an;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Track track = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.top_track_item, parent, false);
        }
        ImageView trackImage = (ImageView)convertView.findViewById(R.id.TrackImg);
        populateImageView(trackImage,track);

        TextView trackNameText = (TextView)convertView.findViewById(R.id.TrackNameTxt);
        trackNameText.setText(track.name);

        TextView artistNameText = (TextView)convertView.findViewById(R.id.ArtistNameTxt);
        artistNameText.setText(artistName);
        return convertView;
    }

    private void populateImageView(ImageView iv, Track track){
        if(!track.album.images.isEmpty()){
            if(Patterns.WEB_URL.matcher(track.album.images.get(0).url).matches()) {
                Picasso.with(this.getContext()).load(track.album.images.get(0).url).into(iv);
            }
        }else{
            iv.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
