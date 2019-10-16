package com.pushkar.musix2.component;

import com.pushkar.musix2.exception.SongAlreadyExistsException;
import com.pushkar.musix2.model.Artist;
import com.pushkar.musix2.model.Song;
import com.pushkar.musix2.service.ArtistService;
import com.pushkar.musix2.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Listener implements ApplicationListener<ContextRefreshedEvent> {

    private SongService songService;
    private ArtistService artistService;

    public Listener(@Autowired SongService songService, @Autowired ArtistService artistService) {
        this.songService = songService;
        this.artistService = artistService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        try {
        artistService.add(new Artist("ARTIST-SAMPLE", "None", ""));
        Song s = new Song();
        s.setId("ID-SAMPLE");
        s.setName("Listener Song");
        s.setDuration(0);
//            s.setUrl("");
        s.setArtist(new Artist("ARTIST-SAMPLE", "None", ""));
//            boolean add = songService.add(s);
//        } catch (SongAlreadyExistsException e) {
//
//        }
    }
}
