package com.pushkar.musix2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
public class Artist {
    @Id
    private String id;
//    @Value("${artist.name}")
    private String name;
    private String url;

    public Artist(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
