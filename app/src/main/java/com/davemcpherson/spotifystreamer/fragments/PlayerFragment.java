package com.davemcpherson.spotifystreamer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemcpherson.spotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by dave on 8/19/2015.
 */
public class PlayerFragment extends Fragment {

    private List<Track> tracks;
    private int selectedIndex;
    private Track currentTrack;

    public void setArguments(List<Track> trackList, int selectedPosition) {
        this.tracks = trackList;
        this.selectedIndex = selectedPosition;
        this.currentTrack = trackList.get(selectedPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_player, container, false);

        setupTextView((TextView) root.findViewById(R.id.ArtistNameTxt), currentTrack.artists.get(0).name);
        setupTextView((TextView)root.findViewById(R.id.AlbumNameTxt),currentTrack.album.name);
        setupTextView((TextView)root.findViewById(R.id.TrackNameTxt),currentTrack.name);
        populateAlbumImage((ImageView) root.findViewById(R.id.AlbumImage));


        return root;
    }

    private void populateAlbumImage(ImageView view){
        if(!currentTrack.album.images.isEmpty()){
            if(Patterns.WEB_URL.matcher(currentTrack.album.images.get(0).url).matches()){
                Picasso.with(getActivity()).load(currentTrack.album.images.get(0).url).into(view);
            }
        }
    }

    private void setupTextView(TextView v, String text){
        v.setText(text);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
