package com.davemcpherson.spotifystreamer.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.davemcpherson.spotifystreamer.commands.ImageDownloadCommand;

import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by dave on 6/27/2015.
 */
public class ImageDownloadTask extends AsyncTask<Image, Void, Bitmap> {

    private ImageView imageView;

    public ImageDownloadTask(ImageView iv){
        imageView = iv;
    }

    public ImageDownloadTask(){}

    @Override
    protected Bitmap doInBackground(Image... params) {
        return new ImageDownloadCommand().execute(params[0].url);
    }

    @Override
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
