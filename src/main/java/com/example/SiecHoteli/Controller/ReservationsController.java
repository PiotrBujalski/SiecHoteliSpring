package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Entity.Hotel;
import com.example.SiecHoteli.Entity.Reservations;
import com.example.SiecHoteli.Repo.ReservRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reserv")
public class ReservationsController {
    private final ReservRepository reservRepository;

    @Autowired
    public ReservationsController(ReservRepository reservRepository) {
        this.reservRepository = reservRepository;
    }

    @GetMapping("")
    public List<Reservations> getAllReservations() {
        return reservRepository.findAll();
    }


    @PostMapping("/add")
    public ResponseEntity<Object> createReservation(@RequestBody ReservationsRequest reservation) {
        Date today = new Date();
        Date startDate = reservation.getStartDate();
        Date endDate = reservation.getEndDate();

        if (startDate.after(endDate)) {
            return ResponseEntity.badRequest().body("Start date cannot be after end date");
        }

        if (startDate.before(today)) {
            return ResponseEntity.badRequest().body("Start date cannot be before today");
        }


        /// sprawdzic czy pokoj jest wolny

        var reserv = Reservations.builder()
                .hotel(reservation.getHotel())
                .roomID(reservation.getRoomID())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .build();

        reservRepository.save(reserv);
        return ResponseEntity.ok("Reservation added successfully");
    }

    @DeleteMapping("/delete/{reservID}")
    public ResponseEntity<String> deleteHotel(@PathVariable Integer reservID) {
        if (reservRepository.existsById(reservID)) {
            reservRepository.deleteById(reservID);
            return ResponseEntity.ok("Hotel deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{reservID}")
    public ResponseEntity<String> updateHotel(@PathVariable("reservID") Integer reservID,
                                              @RequestBody ReservationsRequest request) {

        Optional<Reservations> optional = reservRepository.findById(reservID);
        if (optional.isPresent()) {
            Reservations reserv = optional.get();
            if (request.getHotel() != null) {
                reserv.setHotel(request.getHotel());
            }
            if (request.getRoomID() != null) {
                reserv.setRoomID(request.getRoomID());
            }

            Date today = new Date();
            Date startDate = reserv.getStartDate();
            Date endDate = reserv.getEndDate();
            if (request.getStartDate() != null) {
                startDate = request.getStartDate();
            }
            if (request.getEndDate() != null) {
                endDate = request.getEndDate();
            }

            if (startDate.after(endDate)) {
                return ResponseEntity.badRequest().body("Start date cannot be after end date");
            }
            if (startDate.before(today)) {
                return ResponseEntity.badRequest().body("Start date cannot be before today");
            }

            reserv.setStartDate(startDate);
            reserv.setEndDate(endDate);

            reservRepository.save(reserv);
            return ResponseEntity.ok("Hotel updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
