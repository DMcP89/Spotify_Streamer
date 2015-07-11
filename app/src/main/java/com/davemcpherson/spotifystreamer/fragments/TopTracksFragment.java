package com.davemcpherson.spotifystreamer.fragments;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.davemcpherson.spotifystreamer.R;
import com.davemcpherson.spotifystreamer.commands.TopTracksCommand;
import com.davemcpherson.spotifystreamer.tasks.TopTracksTask;

import java.util.*;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;

public class TopTracksFragment extends Fragment
{
	
	private ListView lv;
	private Artist artist;
	private SpotifyService spotifyService;

	public TopTracksFragment(){}

	public void setArguments(Artist a, SpotifyService ss){
		this.artist = a;
		this.spotifyService = ss;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_top_tracks,container,false);
		String[] test = {artist.name, this.getId()+"", R.layout.fragment_search_artists +""};
		lv = (ListView)root.findViewById(R.id.tracksList);
		lv.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.top_track_item, R.id.TrackNameTxt, Arrays.asList(test)));
		TopTracksTask task = new TopTracksTask(new TopTracksCommand(spotifyService),lv);
		task.execute(artist.id);


		return root;
	}
	
}
