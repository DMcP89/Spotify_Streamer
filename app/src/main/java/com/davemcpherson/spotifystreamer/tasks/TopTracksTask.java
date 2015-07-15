package com.davemcpherson.spotifystreamer.tasks;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.davemcpherson.spotifystreamer.adapters.TopTrackAdapter;
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
        if((tracks.tracks != null)&&(!tracks.tracks.isEmpty())){
            ((TopTrackAdapter) tracksList.getAdapter()).clear();
            ((TopTrackAdapter) tracksList.getAdapter()).addAll(tracks.tracks);
            ((TopTrackAdapter) tracksList.getAdapter()).notifyDataSetChanged();
        }else{
            ((TopTrackAdapter) tracksList.getAdapter()).clear();
            ((TopTrackAdapter) tracksList.getAdapter()).notifyDataSetChanged();
            Toast.makeText(tracksList.getContext(),"No Top Tracks found!",Toast.LENGTH_SHORT).show();
        }
    }
}
