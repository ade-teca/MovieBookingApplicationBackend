package com.keisar.MovieBookingApplication.service;

import com.keisar.MovieBookingApplication.dto.request.BookingRequestDTO;
import com.keisar.MovieBookingApplication.dto.response.BookingResponseDTO;
import com.keisar.MovieBookingApplication.model.Booking;
import com.keisar.MovieBookingApplication.model.BookingStatus;
import com.keisar.MovieBookingApplication.model.Show;
import com.keisar.MovieBookingApplication.model.User;
import com.keisar.MovieBookingApplication.repository.BookingRepository;
import com.keisar.MovieBookingApplication.repository.MovieRepository;
import com.keisar.MovieBookingApplication.repository.ShowRepository;
import com.keisar.MovieBookingApplication.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository,
                          UserRepository userRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public BookingResponseDTO addBooking(BookingRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Show not found"));
        Show show = showRepository.findById(requestDTO.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // 1. Get all seat numbers already taken for this show (excluding cancelled bookings)
        List<String> takenSeats = show.getBookings().stream()
                .filter(b -> b.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(b -> b.getSeatNumbers().stream())
                .toList();

        // 2. Check if any of the requested seats are in the takenSeats list
        List<String> unavailableSeats = requestDTO.getSeatNumbers().stream()
                .filter(takenSeats::contains)
                .toList();

        if (!unavailableSeats.isEmpty()) {
            throw new RuntimeException("The following seats are already occupied: " + unavailableSeats);
        }

        // 3. (Optional) Double-check the count matches
        if (requestDTO.getNumberOfSeats() != requestDTO.getSeatNumbers().size()) {
            throw new RuntimeException("Number of seats does not match the count of seat numbers provided.");
        }

        // 4. Proceed with booking
        // ... set status, totalAmount, user, show, etc ...
        Booking booking = modelMapper.map(requestDTO, Booking.class);
        booking.setTotalAmount(show.getPrice() * requestDTO.getNumberOfSeats());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setBookingTime(LocalDateTime.now());
        booking.setShow(show);
        booking.setUser(user);

        return modelMapper.map(bookingRepository.save(booking), BookingResponseDTO.class);
    }

    public BookingResponseDTO confirmBooking(int id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("The following booking is already pending.");
        }

        //Ask for payment

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return  modelMapper.map(bookingRepository.save(booking), BookingResponseDTO.class);
    }



    public BookingResponseDTO cancellBooking(int id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        validateCancelletion(booking);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return  modelMapper.map(bookingRepository.save(booking), BookingResponseDTO.class);
    }

    public List<BookingResponseDTO> getAllUserBookings(int userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());

    }

    public List<BookingResponseDTO> getAllShowBookings(int showId) {
        return bookingRepository.findByShowId(showId).stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> getAllBookingStatus(BookingStatus bookingstatus) {
        return bookingRepository.findByBookingStatus(bookingstatus).stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }

    public void validateCancelletion(Booking booking) {

        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadLineTime = showTime.minusHours(2);

        if(LocalDateTime.now().isAfter(deadLineTime)) {
            throw new RuntimeException("Cannot cancel the booking.");
        }

        if(booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking already cancelled.");
        }
    }
}
