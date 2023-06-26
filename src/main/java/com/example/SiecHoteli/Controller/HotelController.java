package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Controller.auth.AuthenticationResponse;
import com.example.SiecHoteli.Controller.auth.RegisterRequest;
import com.example.SiecHoteli.Entity.Hotel;
import com.example.SiecHoteli.Entity.Role;
import com.example.SiecHoteli.Entity.User;
import com.example.SiecHoteli.Repo.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelRepository hoteleRepository;

    @Autowired
    public HotelController(HotelRepository hoteleRepository) {
        this.hoteleRepository = hoteleRepository;
    }

    @GetMapping("")
    public List<Hotel> getAllHotels() {
        return hoteleRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addHotel(@RequestBody HotelRequest hotelRequest) {
        var hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .city(hotelRequest.getCity())
                .build();

        hoteleRepository.save(hotel);
        return ResponseEntity.ok("Hotel added successfully");
    }

    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable Integer hotelId) {
        if (hoteleRepository.existsById(hotelId)) {
            hoteleRepository.deleteById(hotelId);
            return ResponseEntity.ok("Hotel deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHotel(@PathVariable("id") Integer hotelId, @RequestBody HotelRequest hotelRequest) {
        Optional<Hotel> optionalHotel = hoteleRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            if (hotelRequest.getName() != null) {
                hotel.setName(hotelRequest.getName());
            }
            if (hotelRequest.getCity() != null) {
                hotel.setCity(hotelRequest.getCity());
            }
            hoteleRepository.save(hotel);
            return ResponseEntity.ok("Hotel updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

