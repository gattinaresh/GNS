package com.gns.user.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gns.user.services.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

}
