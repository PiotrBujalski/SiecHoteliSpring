package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Entity.Reservations;
import com.example.SiecHoteli.Entity.Role;
import com.example.SiecHoteli.Entity.Room;
import com.example.SiecHoteli.Entity.User;
import com.example.SiecHoteli.Repo.ReservRepository;
import com.example.SiecHoteli.Repo.RoomRepository;
import com.example.SiecHoteli.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reserv")
public class ReservationsController {
    private final ReservRepository reservRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationsController(ReservRepository reservRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservRepository = reservRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/getFree")
    public ResponseEntity<?> getAvailableRooms(@RequestParam("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                               @RequestParam("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Room> availableRooms = roomRepository.findAvailableRooms(startDate, endDate);
        return ResponseEntity.ok(availableRooms);
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAllReservations(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(Role.ADMIN.name()))) {
            // Admin role
            List<Reservations> allReservations = reservRepository.findAll();
            return ResponseEntity.ok(allReservations);
        } else if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(Role.USER.name()))) {
            // User role
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByLogin(userDetails.getUsername());
            if (user != null) {
                Integer userId = user.getId();
                List<Reservations> userReservations = reservRepository.findAllByUserID(userId);
                return ResponseEntity.ok(userReservations);
            } else {
                return ResponseEntity.badRequest().body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }



    @PostMapping("/add")
    public ResponseEntity<Object> createReservation(@RequestBody ReservationsRequest reservation, Authentication authentication) {
        boolean isUser = authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals(Role.USER.name()));

        if (isUser) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByLogin(userDetails.getUsername());
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            if (!user.getId().equals(reservation.getUserID())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only add reservations for yourself");
            }
        }

        boolean isUserExists = userRepository.existsById(reservation.getUserID());
        if (!isUserExists) {
            return ResponseEntity.badRequest().body("User is not in the database");
        }

        Date today = new Date();
        Date startDate = reservation.getStartDate();
        Date endDate = reservation.getEndDate();

        if (startDate.after(endDate)) {
            return ResponseEntity.badRequest().body("Start date cannot be after end date");
        }

        if (startDate.before(today)) {
            return ResponseEntity.badRequest().body("Start date cannot be before today");
        }

        boolean isReservationExists = reservRepository.existsByUserIDAndEndDateGreaterThanEqualAndStartDateLessThanEqual(reservation.getUserID(), startDate, endDate);
        if (isReservationExists) {
            return ResponseEntity.badRequest().body("This reservation overlaps with an existing reservation for the same user");
        }

        boolean isRoomInHotel = roomRepository.existsByRoomIDAndHotel_HotelID(reservation.getRoom().getRoomID(), reservation.getHotel().getHotelID());
        if (!isRoomInHotel) {
            return ResponseEntity.badRequest().body("The specified room does not belong to the hotel");
        }

        boolean isFree = roomRepository.getAvailabilityByRoomID(reservation.getRoom().getRoomID());
        if (!isFree) {
            return ResponseEntity.badRequest().body("This room is occupied");
        }

        var reserv = Reservations.builder()
                .hotel(reservation.getHotel())
                .room(reservation.getRoom())
                .userID(reservation.getUserID())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .build();

        reservRepository.save(reserv);
        return ResponseEntity.ok("Reservation added successfully");
    }


    @DeleteMapping("/delete/{reservID}")
    public ResponseEntity<String> deleteReserv(@PathVariable Integer reservID) {
        if (reservRepository.existsById(reservID)) {
            reservRepository.deleteById(reservID);
            return ResponseEntity.ok("Hotel deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{reservID}")
    public ResponseEntity<String> updateReserv(@PathVariable("reservID") Integer reservID,
                                               @RequestBody ReservationsRequest request) {

        Optional<Reservations> optional = reservRepository.findById(reservID);
        if (optional.isPresent()) {
            Reservations reserv = optional.get();
            if (request.getHotel() != null) {
                reserv.setHotel(request.getHotel());
            }
            if (request.getRoom() != null) {
                boolean is_free = roomRepository.getAvailabilityByRoomID(request.getRoom().getRoomID());
                if (!is_free) {
                    return ResponseEntity.badRequest().body("This room is occupied");
                }
                reserv.setRoom(request.getRoom());
            }
            if (request.getUserID() != null) {
                boolean is_user = userRepository.existsById(request.getUserID());
                if (!is_user) {
                    return ResponseEntity.badRequest().body("User is not in database");
                }
                reserv.setUserID(request.getUserID());
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

    @DeleteMapping("/deleteExpired")
    public ResponseEntity<String> deleteExpiredReservations() {
        reservRepository.deleteExpiredReservations();
        return ResponseEntity.ok("Expired reservations deleted successfully");
    }
}
