package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;
import android.widget.ListView;

import com.davemcpherson.spotifystreamer.commands.SpotifyCommand;

import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by dave on 6/30/2015.
 */
public abstract class SpotifyServiceTask extends AsyncTask<String, Void, Object> {

    protected SpotifyCommand command;
    protected ListView listView;

    public SpotifyServiceTask(SpotifyCommand c){
        this.command = c;
    }

    public SpotifyServiceTask(SpotifyCommand c, ListView lv){
        this.command = c;
        this.listView = lv;
    }

    @Override
    protected Object doInBackground(String... params) {
        return command.execute(params[0]);
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }


    protected abstract void onPostExecute(ArtistsPager artistsPager);
}
