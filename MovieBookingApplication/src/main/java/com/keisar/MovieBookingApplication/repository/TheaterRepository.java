package com.keisar.MovieBookingApplication.repository;

import com.keisar.MovieBookingApplication.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    Optional<List<Theater>> findByTheaterLocation(String location);

}
