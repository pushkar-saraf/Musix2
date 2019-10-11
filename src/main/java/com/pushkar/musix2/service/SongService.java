package com.pushkar.musix2.service;

import com.pushkar.musix2.model.Song;
import java.util.List;

public interface SongService {
    public List<Song> findAll();
    public boolean add(Song song);
    public boolean deleteSong(Long id);
}
