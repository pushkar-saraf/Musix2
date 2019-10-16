package com.pushkar.musix2.repository;

import com.pushkar.musix2.model.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SongDao extends CrudRepository<Song, String> {

    @Query("select s from Song s where lower(s.name)=lower(:name)")
    public ArrayList<Song> getByName(@Param("name") String name);
}
