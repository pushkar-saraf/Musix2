package com.pushkar.musix2.service;

import com.pushkar.musix2.exception.SongAlreadyExistsException;
import com.pushkar.musix2.exception.SongNotFoundException;
import com.pushkar.musix2.model.Song;
import com.pushkar.musix2.repository.SongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("prod")
@Profile("prod")
@Primary
public class SongServiceImpl implements SongService {
    private final SongDao songDao;

    @Autowired
    public SongServiceImpl(SongDao songDao) {
        this.songDao = songDao;
    }

    @Override
    public ArrayList<Song> findAll() {
        return (ArrayList<Song>) songDao.findAll();
    }

    @Override
    public boolean add(Song song) throws SongAlreadyExistsException {
        if(!songDao.findById(song.getId()).isPresent()) {
            songDao.save(song);
            return true;
        } else {
            throw new SongAlreadyExistsException();
        }
    }

    @Override
    public ArrayList<Song> getTrackByName(String name) throws SongNotFoundException {
        ArrayList<Song> song = songDao.getByName(name);
        if (song != null)
            return song;
        else
            throw new SongNotFoundException();
    }

    @Override
    public boolean deleteTrack(String id) throws SongNotFoundException {
        for (Song song : getTrackByName(id)) {
            songDao.delete(song);
        }
        return true;
    }

}
