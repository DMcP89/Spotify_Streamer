package com.davemcpherson.spotifystreamer.tasks;

import com.davemcpherson.spotifystreamer.commands.SearchArtistCommand;

/**
 * Created by dave on 6/25/2015.
 */
public class SearchArtistTask extends SpotifyServiceTask{

    public SearchArtistTask(SearchArtistCommand c) {
        super(c);
    }
}
