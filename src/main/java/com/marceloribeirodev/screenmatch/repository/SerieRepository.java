package com.marceloribeirodev.screenmatch.repository;

import com.marceloribeirodev.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
