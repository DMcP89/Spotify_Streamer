package com.davemcpherson.spotifystreamer.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by dave on 7/11/2015.
 */
public class ArtistParcelable implements Parcelable {

    public List<Artist> list;


    public ArtistParcelable(List<Artist> a){
        list = a;
    }

    private ArtistParcelable(Parcel in){
        list = (List<Artist>) in.readArrayList(Artist.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(list);
    }

    public final Parcelable.Creator<ArtistParcelable> CREATOR = new  Parcelable.Creator<ArtistParcelable>(){

        @Override
        public ArtistParcelable createFromParcel(Parcel source) {
            return new ArtistParcelable(source);
        }

        @Override
        public ArtistParcelable[] newArray(int size) {
            return new ArtistParcelable[size];
        }
    };
}
