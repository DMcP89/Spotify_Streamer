package com.davemcpherson.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String temp[] ={"Test Artist"};

        ArrayAdapter<String> mArtistAdapter= new ArrayAdapter<String>(
                getActivity(),
                R.layout.artist_search_item,
                R.id.ArtistNameTxt,
                Arrays.asList(temp));

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) root.findViewById(R.id.ArtistList);
        listView.setAdapter(mArtistAdapter);

        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        spotify.getArtist("4gzpq5DPGxSnKTe4SA8HAU", new Callback<Artist>() {
            @Override
            public void success(Artist artist, Response response) {
                Log.i("Spotify","Artist: "+artist.name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Spotify",error.toString());
            }
        });


        return root;
    }
}
