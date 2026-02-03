package com.keisar.MovieBookingApplication.repository;

import com.keisar.MovieBookingApplication.model.Booking;
import com.keisar.MovieBookingApplication.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(int userId);
    List<Booking> findByShowId(int showId);
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}
