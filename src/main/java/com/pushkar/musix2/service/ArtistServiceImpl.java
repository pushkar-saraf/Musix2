package com.pushkar.musix2.service;

import com.pushkar.musix2.model.Artist;
import com.pushkar.musix2.repository.ArtistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class ArtistServiceImpl implements ArtistService{
    private final ArtistDao artistDao;

    @Autowired
    ArtistServiceImpl(ArtistDao artistDao){
        this.artistDao = artistDao;
    }
    public  boolean add(Artist artist){
        if(artist.getId()==null){
            artist.setId(randomString());
        }
        artistDao.save(artist);
        return true;
    }


    private String randomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
