package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;
import android.widget.ListView;

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
        ArtisitAdapter artisitAdapter = new ArtisitAdapter(this.artistList.getContext(), artistsPager.artists.items);
        this.artistList.setAdapter(artisitAdapter);
    }
}
