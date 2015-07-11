package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;
import android.widget.ListView;

import com.davemcpherson.spotifystreamer.commands.TopTracksCommand;

import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by dave on 6/27/2015.
 */
public class TopTracksTask extends AsyncTask<String,Void,Tracks>{

    private ListView tracksList;
    private TopTracksCommand command;

    public TopTracksTask(TopTracksCommand c, ListView lv){
        this.command =c;
        this.tracksList = lv;
    }



    @Override
    protected Tracks doInBackground(String... params) {
        return command.execute(params[0]);
    }

    @Override
    protected void onPostExecute(Tracks tracks) {

    }
}
