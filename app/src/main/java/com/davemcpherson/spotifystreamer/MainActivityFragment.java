package com.davemcpherson.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.davemcpherson.spotifystreamer.tasks.SearchArtistTask;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnItemClickListener {

	private FragmentManager fragMan;    

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
		listView.setOnItemClickListener(this);


        SearchArtistTask task = new SearchArtistTask();
        task.execute("coldplay");
        try {
            ArtistsPager ap = task.get();
            for(Artist a : ap.artists.items){
                Toast.makeText(getActivity(), a.toString(),Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return root;
    }

	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		getActivity().getSupportFragmentManager().beginTransaction()
			.replace(this.getId(), new TopTracksFragment(""))
			.commit();
	}

	
	

}
