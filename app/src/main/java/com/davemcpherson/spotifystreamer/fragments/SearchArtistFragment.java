package com.davemcpherson.spotifystreamer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.davemcpherson.spotifystreamer.MainActivity;
import com.davemcpherson.spotifystreamer.R;
import com.davemcpherson.spotifystreamer.adapters.ArtisitAdapter;
import com.davemcpherson.spotifystreamer.commands.SearchArtistCommand;
import com.davemcpherson.spotifystreamer.listeners.OnArtistSelectedListener;
import com.davemcpherson.spotifystreamer.tasks.SearchArtistTask;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchArtistFragment extends Fragment implements OnItemClickListener{

    private final static String PARCELABLE_TAG = "ArtistPager";

    private ListView artistList;
    private SearchView artistSearch;
    private ArtisitAdapter artistAdapter;
    private SpotifyService spotifyService;
    private OnArtistSelectedListener artistSelectedListener;

	public SearchArtistFragment() {
        //this.setRetainInstance(true);
    }

    public void  setArguments(SpotifyService ss){
        this.spotifyService = ss;
    }


    @Override
    public void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            artistAdapter = new ArtisitAdapter(getActivity(), new ArrayList<Artist>());
        }else{
            ArrayList<Artist> list = savedInstanceState.getParcelableArrayList("ArtistList");
            artistAdapter = new ArtisitAdapter(getActivity(),list);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupActionbar();
    }

    private void setupActionbar(){
        ActionBar ab = ((ActionBarActivity)getActivity()).getSupportActionBar();
        ab.setTitle("Spotify Streamer");
        ab.setSubtitle("");
        ab.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_artists, container, false);

        artistSearch = (SearchView)root.findViewById(R.id.artistSearchView);
        artistSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchForArtist(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        artistList = (ListView) root.findViewById(R.id.ArtistList);
        artistList.setAdapter(artistAdapter);
        artistList.setOnItemClickListener(this);

        return root;
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ArtistList",artistAdapter.getArrayList());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            artistSelectedListener = (OnArtistSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
        Artist selectedArtist = (Artist)artistList.getItemAtPosition(p3);
        artistSelectedListener.OnArtistSelect(selectedArtist);
	}

    public void searchForArtist(String s){
        if(((MainActivity)getActivity()).isNetworkConnected()) {
            SearchArtistTask task = new SearchArtistTask(new SearchArtistCommand(spotifyService), artistList);
            task.execute(s);
        }else{
            Toast.makeText(getActivity(),"Please enable Network to search",Toast.LENGTH_LONG).show();
            ((ArrayAdapter<Artist>)artistList.getAdapter()).clear();
            ((ArrayAdapter<Artist>)artistList.getAdapter()).notifyDataSetChanged();
        }
    }

}
