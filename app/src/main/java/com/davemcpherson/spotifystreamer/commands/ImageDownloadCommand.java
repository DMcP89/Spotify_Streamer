package com.davemcpherson.spotifystreamer.commands;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by dave on 6/29/2015.
 */
public class ImageDownloadCommand implements ICommand{

    public Bitmap execute(String url) {
        Bitmap bmImage = null;
        try{
            InputStream in = new URL(url).openStream();
            bmImage = BitmapFactory.decodeStream(in);
        }catch(Exception e){
            Log.e("Error", "Is e.message null? "+ (e.getMessage() == null));
            Log.e("Error", "There was an issue");
            //e.printStackTrace();
        }
        return bmImage;
    }
}
