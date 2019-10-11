package com.pushkar.musix2.controller;

import com.pushkar.musix2.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.pushkar.musix2.service.SongService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final SongService songDao;
    private Song song;
    private List<Song> songs;
    private Long x = 0L;


    @Autowired
    public MainController(SongService songDao) {
        this.songDao = songDao;
        this.song = new Song();
        song.setId(x);
        this.songs = new ArrayList<>();
    }

    @RequestMapping("/")
    public String base(Model model) {
        return "redirect:/songs";
    }

    @RequestMapping("/deleteSong/{id}")
    public String delete(@PathVariable Long id) {
        songDao.deleteSong(id);
        songs = songDao.findAll();
        return "redirect:/songs";
    }

    @PostMapping("/saveSong")
    public String saveRandomSong(@ModelAttribute Song s) {
        x = x + 1;
        song.setId(x);
        songDao.add(s);
        songs = songDao.findAll();
        return "redirect:/songs";
    }

    @GetMapping("/songs")
    public String index(Model model) {
        model.addAttribute("songs", songs);
        model.addAttribute("song", song);
        return "index";
    }
}
