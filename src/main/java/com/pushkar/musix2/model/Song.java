package com.pushkar.musix2.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;

@Entity
@Table(name = "songs")
@PropertySource("classpath:application.properties")
public @Data class Song {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name="name")
    @Value("${song.name}")
    private String name = "Song";

    @ManyToOne
    @JoinColumn(name = "artist")
    private Artist artist = new Artist();

    @Column(name = "duration")
    private int duration;

    @Column(name = "url")
    private String url;

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
