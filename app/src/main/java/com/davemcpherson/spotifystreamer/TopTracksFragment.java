package com.davemcpherson.spotifystreamer;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class TopTracksFragment extends Fragment
{
	
	private ListView lv;
	private String artist;
	
	public TopTracksFragment(String a){
		this.artist = a;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_top_songs,container,false);
		String[] test = {"Test 1", "Test 2", "Test 3"};
		lv = (ListView)root.findViewById(R.id.tracksList);
		lv.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.artist_track_item, R.id.TrackNameTxt, Arrays.asList(test)));
		return root;
	}
	
}
