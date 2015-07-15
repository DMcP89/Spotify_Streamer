package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.davemcpherson.spotifystreamer.adapters.ArtisitAdapter;
import com.davemcpherson.spotifystreamer.commands.SearchArtistCommand;

import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by dave on 6/25/2015.
 */
public class SearchArtistTask extends AsyncTask<String, Void, ArtistsPager>{

    private SearchArtistCommand command;
    private ListView artistList;

    public SearchArtistTask(SearchArtistCommand c, ListView lv) {
        this.command = c;
        this.artistList = lv;
    }

    @Override
    protected ArtistsPager doInBackground(String... params) {
        return command.execute(params[0]);
    }

    @Override
    protected void onPostExecute(ArtistsPager artistsPager) {
        if((artistsPager.artists != null)&&(!artistsPager.artists.items.isEmpty())) {
            ((ArtisitAdapter) artistList.getAdapter()).clear();
            ((ArtisitAdapter) artistList.getAdapter()).addAll(artistsPager.artists.items);
            ((ArtisitAdapter) artistList.getAdapter()).notifyDataSetChanged();
        }else{
            ((ArtisitAdapter) artistList.getAdapter()).clear();
            ((ArtisitAdapter) artistList.getAdapter()).notifyDataSetChanged();
            Toast.makeText(artistList.getContext(),"No Artist Found! Please refine search",Toast.LENGTH_SHORT).show();
        }
    }
}
