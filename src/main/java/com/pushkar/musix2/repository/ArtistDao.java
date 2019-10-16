package com.pushkar.musix2.repository;

import com.pushkar.musix2.model.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistDao extends CrudRepository<Artist, String> {
}
