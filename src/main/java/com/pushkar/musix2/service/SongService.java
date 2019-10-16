package com.pushkar.musix2.service;

import com.pushkar.musix2.exception.SongAlreadyExistsException;
import com.pushkar.musix2.exception.SongNotFoundException;
import com.pushkar.musix2.model.Song;

import java.util.ArrayList;

public interface SongService {
    public ArrayList<Song> findAll();
    public boolean add(Song track) throws SongAlreadyExistsException;
    public boolean deleteTrack(String id) throws SongNotFoundException;
    public ArrayList<Song> getTrackByName(String name) throws SongNotFoundException;
}
