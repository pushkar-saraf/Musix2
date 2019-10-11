package com.pushkar.musix2.model;

import javax.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name")
    private String name = "Song";

    @Column(name = "artist")
    private String artist = "Artist";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
