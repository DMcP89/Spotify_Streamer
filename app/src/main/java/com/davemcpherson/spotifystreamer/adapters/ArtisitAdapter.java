package com.davemcpherson.spotifystreamer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemcpherson.spotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by dave on 6/27/2015.
 */
public class ArtisitAdapter extends ArrayAdapter<Artist> {
    private ViewHolder viewHolder;
    public ArtisitAdapter(Context context, List<Artist> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Artist artist = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_search_item, parent, false);
            popualateViewHolder(convertView);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


        populateImageView(viewHolder.artistImage,artist);

        viewHolder.artistNameTxt.setText(artist.name);

        return convertView;
    }

    private void popualateViewHolder(View convertView){
        viewHolder = new ViewHolder();
        viewHolder.artistNameTxt = (TextView)convertView.findViewById(R.id.ArtistNameTxt);
        viewHolder.artistImage = (ImageView)convertView.findViewById(R.id.ArtistImg);
        convertView.setTag(viewHolder);
    }

    public void populateImageView(ImageView artistImage,Artist artist){
        if(!artist.images.isEmpty()) {
            Picasso.with(this.getContext()).load(artist.images.get(0).url).into(artistImage);
        }else{
            artistImage.setImageResource(R.mipmap.ic_launcher);
        }
    }

    public ArrayList<Artist> getArrayList(){
        ArrayList<Artist> objects = new ArrayList<>(getCount());
        for (int i = 0; i < getCount(); i++) {
            objects.add(getItem(i));
        }
        return objects;
    }

    private static class ViewHolder{
        TextView artistNameTxt;
        ImageView artistImage;
    }
}
