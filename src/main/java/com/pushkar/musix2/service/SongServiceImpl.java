package com.pushkar.musix2.service;

import com.pushkar.musix2.model.Song;
import com.pushkar.musix2.repository.SongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongDao songDao;

    @Autowired
    public SongServiceImpl(SongDao songDao) {
        this.songDao = songDao;
    }

    @Override
    public List<Song> findAll() {
        return (List<Song>) songDao.findAll();
    }

    @Override
    public boolean add(Song song) {
        songDao.save(song);
        return true;
    }

    @Override
    public boolean deleteSong(Long id) {
        songDao.deleteById(id);
        return true;
    }
}
