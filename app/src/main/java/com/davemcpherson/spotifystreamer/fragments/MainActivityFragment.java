package com.davemcpherson.spotifystreamer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.davemcpherson.spotifystreamer.R;
import com.davemcpherson.spotifystreamer.commands.SearchArtistCommand;
import com.davemcpherson.spotifystreamer.tasks.SearchArtistTask;

import java.util.Arrays;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnItemClickListener {

	private FragmentManager fragMan;
    private ListView artistList;
    private EditText searchText;
    private SpotifyService spotifyService;

	public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String temp[] ={};

        ArrayAdapter<String> mArtistAdapter= new ArrayAdapter<String>(
                getActivity(),
                R.layout.artist_search_item,
                R.id.ArtistNameTxt,
                Arrays.asList(temp));

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        artistList = (ListView) root.findViewById(R.id.ArtistList);
        artistList.setAdapter(mArtistAdapter);
        artistList.setOnItemClickListener(this);

        searchText = (EditText) root.findViewById(R.id.SearchArtistTxt);
        searchText.addTextChangedListener(new SearchTextWatcher());
        spotifyService = new SpotifyApi().getService();

        //task.execute("coldplay");
        /*try {
            ArtistsPager ap = (ArtistsPager)task.get();
            ArtisitAdapter artisitAdapter = new ArtisitAdapter(getActivity(),ap.artists.items);
            listView.setAdapter(artisitAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
*/

        return root;
    }

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		getActivity().getSupportFragmentManager().beginTransaction()
			.replace(this.getId(), new TopTracksFragment(""))
			.commit();
	}


    private class SearchTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void afterTextChanged(Editable s) {
            //Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_LONG).show();
            SearchArtistTask task = new SearchArtistTask(new SearchArtistCommand(spotifyService), artistList);
            task.execute(s.toString());
        }
    }

	
	

}
