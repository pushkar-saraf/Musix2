package com.pushkar.musix2.component;

import com.pushkar.musix2.exception.SongAlreadyExistsException;
import com.pushkar.musix2.model.Artist;
import com.pushkar.musix2.model.Song;
import com.pushkar.musix2.service.ArtistService;
import com.pushkar.musix2.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private SongService songService;
    private ArtistService artistService;

    public Runner(@Autowired SongService songService, @Autowired ArtistService artistService) {
        this.songService = songService;
        this.artistService = artistService;
    }

    @Override
    public void run(String... args) throws Exception {
//        try {
        artistService.add(new Artist("ARTIST-SAMPLE-1", "None", ""));
        Song s = new Song();
        s.setId("ID-SAMPLE-1");
        s.setName("Runner Song");
        s.setDuration(0);
        s.setUrl("");
        s.setArtist(new Artist("ARTIST-SAMPLE", "None", ""));
//            boolean add = songService.add(s);
//        } catch (SongAlreadyExistsException e) {
//            e.printStackTrace();
//        }
    }
}
