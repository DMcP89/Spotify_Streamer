package com.davemcpherson.spotifystreamer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.davemcpherson.spotifystreamer.MainActivity;
import com.davemcpherson.spotifystreamer.R;
import com.davemcpherson.spotifystreamer.adapters.TopTrackAdapter;
import com.davemcpherson.spotifystreamer.commands.TopTracksCommand;
import com.davemcpherson.spotifystreamer.tasks.TopTracksTask;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public class TopTracksFragment extends Fragment
{
	
	private ListView tracksList;
	private Artist artist;
	private SpotifyService spotifyService;

	public TopTracksFragment(){
		this.setRetainInstance(true);
	}

	public void setArguments(Artist a, SpotifyService ss){
		this.artist = a;
		this.spotifyService = ss;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionbar();
	}


	private void setupActionbar(){
		ActionBar ab = ((ActionBarActivity) getActivity()).getSupportActionBar();
		ab.setTitle("Top Tracks");
		ab.setSubtitle(artist.name);
		ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_top_tracks,container,false);

		tracksList = (ListView)root.findViewById(R.id.tracksList);
		TopTrackAdapter adapter = new TopTrackAdapter(getActivity(), new ArrayList<Track>(), artist.name);
		tracksList.setAdapter(adapter);
		if(((MainActivity)getActivity()).isNetworkConnected()) {
			TopTracksTask task = new TopTracksTask(new TopTracksCommand(spotifyService), tracksList);
			task.execute(artist.id);
		}else{
			Toast.makeText(getActivity(),"Please enable Network to Play",Toast.LENGTH_LONG).show();
		}
		return root;
	}
	
}
