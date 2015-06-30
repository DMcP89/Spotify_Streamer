package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;

import com.davemcpherson.spotifystreamer.commands.SpotifyCommand;

/**
 * Created by dave on 6/30/2015.
 */
public class SpotifyServiceTask extends AsyncTask<String, Void, Object> {

    protected SpotifyCommand command;

    public SpotifyServiceTask(SpotifyCommand c){
        this.command = c;
    }

    @Override
    protected Object doInBackground(String... params) {
        return command.execute(params[0]);
    }
}
