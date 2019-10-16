package com.pushkar.musix2.controller;

import com.pushkar.musix2.exception.SongAlreadyExistsException;
import com.pushkar.musix2.exception.SongNotFoundException;
import com.pushkar.musix2.model.Artist;
import com.pushkar.musix2.model.Song;
import com.pushkar.musix2.service.ArtistService;
import com.pushkar.musix2.service.LastFmService;
import com.pushkar.musix2.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    private final SongService songService;
    private final ArtistService artistService;
    private final List<String> colors = Arrays.asList("#f44336", "#E91E63", "#9C27B0", "#673AB7", "#3F51B5",
            "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50", "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107",
            "#FF9800", "#FF5722", "#795548", "#9E9E9E", "#607D8B");
    private Map<String, String> images;
    private final LastFmService lastFmService;
    private ArrayList<Song> tracks;
    private ArrayList<Song> songs;
    private Long x = 0L;
    private ArrayList<Artist> artists;

//    @Qualifier("debug")
    public MainController(@Autowired  SongService songService, @Autowired ArtistService artistService,
                          @Autowired LastFmService lastFmService) {
        this.artistService = artistService;
        this.lastFmService = lastFmService;
        this.songService = songService;
    }

    private void generateImages(int type) {
        images = new HashMap<>();
        Random random = new Random();
        if(type==2){
            for (Artist track : artists) {
                System.out.println(track);
                int r = random.nextInt(colors.size());
                String color = colors.get(r);
                images.put(track.getName(), color);
            }
        } else {
            ArrayList<Song> list;

            if (type == 0) {
                list = tracks;
            } else {
                list = songs;
            }
            for (Song track : list) {
                System.out.println(track);
                int r = random.nextInt(colors.size());
                String color = colors.get(r);
                images.put(track.getName(), color);
            }
        }
    }

    private void saveSongById(String id) throws SongAlreadyExistsException {
        for (Song track : tracks) {
            if (track.getId().equals(id)) {
                Song song = new Song();
                song.setId(track.getId());
                song.setName(track.getName());
                artistService.add(track.getArtist());
                song.setArtist(track.getArtist());
                song.setDuration(track.getDuration());
                song.setUrl(track.getUrl());
                songService.add(song);
            }
        }
    }


    @RequestMapping("/")
    public String base() {
        return "redirect:/top";
    }


    @RequestMapping("/songs")
    public String songs(Model model) {
        songs = songService.findAll();
        model.addAttribute("songs", songs);
        model.addAttribute("images", images);
        generateImages(1);
        return "songs";
    }

    @RequestMapping("/findByName")
    public String findByName(@RequestParam(value = "search") String search, Model model) {
        try {
            songs = songService.getTrackByName(search);
        } catch (SongNotFoundException e) {
            return "error";
        }
        generateImages(1);
        model.addAttribute("songs", songs);
        model.addAttribute("images", images);
        return "songs";
    }

    @RequestMapping("/playlists")
    public String playlists(){
        return "playlists";
    }

    @RequestMapping("/deleteSong/{name}")
    public String delete(@PathVariable String name) {
        try {
            songService.deleteTrack(name);
        } catch (SongNotFoundException e) {
            return "error";
        }
        return "redirect:/songs";
    }

    @RequestMapping("/search")
    public String search(Model model) {
        tracks = new ArrayList<>();
        return "search";
    }

    @RequestMapping("/searchSong")
    public String search(@RequestParam(value = "search") String search, Model model) throws IOException {
        this.tracks = lastFmService.search(search);
        System.out.println(tracks);
        generateImages(0);
        model.addAttribute("tracks", tracks);
        model.addAttribute("images", images);
        return "search";
    }

    @RequestMapping("/topArtists")
    public String getTopArtists(Model model){
        try {
            artists = lastFmService.getTopArtists();
        } catch (IOException e) {
            artists = new ArrayList<>();
            return "error";
        }
        model.addAttribute("images", images);
        model.addAttribute("artists", artists);
        generateImages(2);
        return "artists";
    }

    @RequestMapping("/saveTrack/{id}")
    public String saveRandomSong(@PathVariable String id) {
        try {
            saveSongById(id);
        } catch (SongAlreadyExistsException e) {
            return "error";
        }
        return "redirect:/top";
    }

    @RequestMapping("/saveFromSearch/{id}")
    public String saveFromSearch(@PathVariable String id) {
        try {
            saveSongById(id);
        } catch (SongAlreadyExistsException e) {
            return "error";
        }
        return "redirect:/search";
    }

    @GetMapping("/top")
    public String index(Model model) throws IOException {
        this.tracks = lastFmService.getTopTracks();
        generateImages(0);
        model.addAttribute("tracks", tracks);
        model.addAttribute("images", images);
        return "index";
    }

}
