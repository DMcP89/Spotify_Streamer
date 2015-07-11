package com.davemcpherson.spotifystreamer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.davemcpherson.spotifystreamer.R;
import com.davemcpherson.spotifystreamer.commands.SearchArtistCommand;
import com.davemcpherson.spotifystreamer.listeners.OnArtistSelectedListener;
import com.davemcpherson.spotifystreamer.tasks.SearchArtistTask;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchArtistFragment extends Fragment implements OnItemClickListener {

    private ListView artistList;
    private EditText searchText;
    private SpotifyService spotifyService;
    private OnArtistSelectedListener artistSelectedListener;

	public SearchArtistFragment() {
    }

    public void  setArguments(SpotifyService ss){
        this.spotifyService = ss;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_artists, container, false);

        artistList = (ListView) root.findViewById(R.id.ArtistList);
        artistList.setOnItemClickListener(this);

        searchText = (EditText) root.findViewById(R.id.SearchArtistTxt);
        searchText.addTextChangedListener(new SearchTextWatcher());
        return root;
    }

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


    private class SearchTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            SearchArtistTask task = new SearchArtistTask(new SearchArtistCommand(spotifyService), artistList);
            task.execute(s.toString());
        }
    }

	
	

}
