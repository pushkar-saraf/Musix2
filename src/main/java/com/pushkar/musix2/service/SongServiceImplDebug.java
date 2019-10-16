package com.pushkar.musix2.service;

import com.pushkar.musix2.exception.SongAlreadyExistsException;
import com.pushkar.musix2.exception.SongNotFoundException;
import com.pushkar.musix2.model.Song;
import com.pushkar.musix2.repository.SongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service("debug")
@Profile("dev")
public class SongServiceImplDebug implements SongService {
    private final SongDao songDao;

    @Autowired
    public SongServiceImplDebug(SongDao songDao) {
        System.out.println("created");
        this.songDao = songDao;
    }

    @Override
    public ArrayList<Song> findAll() {
        System.out.println("here goes");
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
        ArrayList<Song> songs = songDao.getByName(name);
        if (songs != null)
            return songs;
        else
            throw new SongNotFoundException();
    }

    @Override
    public boolean deleteTrack(String id) {
        songDao.deleteById(id);
        return true;
    }
}
