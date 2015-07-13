package com.davemcpherson.spotifystreamer.commands;

import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by dave on 6/28/2015.
 */
public abstract class SpotifyCommand implements ICommand {

    protected SpotifyService spotifyService;

    public abstract Object execute(String param);
}
