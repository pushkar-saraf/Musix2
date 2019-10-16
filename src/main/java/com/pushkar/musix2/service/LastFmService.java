package com.pushkar.musix2.service;

import com.pushkar.musix2.model.Artist;
import com.pushkar.musix2.model.Song;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public interface LastFmService {
    ArrayList<Song> getTopTracks() throws IOException;

    ArrayList<Artist> getTopArtists() throws IOException;

    ArrayList<Song> search(String search) throws IOException;
}
