package com.davemcpherson.spotifystreamer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

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
public class SearchArtistFragment extends Fragment implements OnItemClickListener, View.OnClickListener, View.OnKeyListener {

    private final static String PARCELABLE_TAG = "ArtistPager";

    private ListView artistList;
    private ArtisitAdapter artistAdapter;
    private EditText searchText;
    private ImageView searchBtn;
    private SpotifyService spotifyService;
    private OnArtistSelectedListener artistSelectedListener;

	public SearchArtistFragment() {
        this.setRetainInstance(true);
    }

    public void  setArguments(SpotifyService ss){
        this.spotifyService = ss;
    }


    @Override
    public void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        artistAdapter = new ArtisitAdapter(getActivity(), new ArrayList<Artist>());
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
        Log.i("Parcelable Test", "OnCreateView Call, adapter size: "+artistAdapter.getCount());
        artistList = (ListView) root.findViewById(R.id.ArtistList);
        artistList.setAdapter(artistAdapter);
        artistList.setOnItemClickListener(this);

        searchText = (EditText) root.findViewById(R.id.SearchArtistTxt);
        searchText.setOnKeyListener(this);

        searchBtn = (ImageView) root.findViewById(R.id.SearchArtistBtn);
        searchBtn.setOnClickListener(this);

        return root;
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
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
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
        Artist selectedArtist = (Artist)artistList.getItemAtPosition(p3);
       artistSelectedListener.OnArtistSelect(selectedArtist);
	}

    @Override
    public void onClick(View v) {
        searchForArtist();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if((event.getAction() ==  KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
            searchForArtist();
        }
        return false;
    }

    public void searchForArtist(){
        SearchArtistTask task = new SearchArtistTask(new SearchArtistCommand(spotifyService), artistList);
        task.execute(searchText.getText().toString());
    }



}
