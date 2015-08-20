package com.davemcpherson.spotifystreamer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemcpherson.spotifystreamer.R;

/**
 * Created by dave on 8/19/2015.
 */
public class PlayerFragment extends Fragment {


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_player,container,false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
