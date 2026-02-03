package com.keisar.MovieBookingApplication.repository;

import com.keisar.MovieBookingApplication.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findMovieByTitle(String title);
    List<Movie> findMovieByGenre(String genre);
    List<Movie> findMovieByLanguage(String movieLanguage);
}
