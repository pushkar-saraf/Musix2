package com.pushkar.musix2.repository;

import com.pushkar.musix2.model.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongDao extends CrudRepository<Song, Long> { }
