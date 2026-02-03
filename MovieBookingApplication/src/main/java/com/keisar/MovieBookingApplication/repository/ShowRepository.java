package com.keisar.MovieBookingApplication.repository;

import com.keisar.MovieBookingApplication.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    Optional<List<Show>> findByMovie_Id(int movieId);
    Optional<List<Show>> findByTheater_Id(int movieId);
}
