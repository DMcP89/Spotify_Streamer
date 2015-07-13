package com.davemcpherson.spotifystreamer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
	
	private ListView lv;
	private Artist artist;
	private SpotifyService spotifyService;

	public TopTracksFragment(){

	}

	public void setArguments(Artist a, SpotifyService ss){
		this.artist = a;
		this.spotifyService = ss;

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_top_tracks,container,false);

		lv = (ListView)root.findViewById(R.id.tracksList);
		TopTrackAdapter adapter = new TopTrackAdapter(getActivity(), new ArrayList<Track>(), artist.name);
		lv.setAdapter(adapter);
		TopTracksTask task = new TopTracksTask(new TopTracksCommand(spotifyService),lv);
		task.execute(artist.id);
		return root;
	}
	
}
