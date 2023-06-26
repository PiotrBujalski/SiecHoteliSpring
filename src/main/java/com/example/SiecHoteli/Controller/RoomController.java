package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Entity.Room;
import com.example.SiecHoteli.Repo.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private final RoomRepository roomRepository;


    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createRoom(@RequestBody RoomRequest roomRequest) {
        if (roomRequest.getPrice() <= 0) {
            return ResponseEntity.badRequest().body("Price must be greater than zero");
        }

        var newRoom = Room.builder()
                .hotel(roomRequest.getHotel())
                .type(roomRequest.getType())
                .price(roomRequest.getPrice())
                .availability(roomRequest.isAvailability())
                .build();

        roomRepository.save(newRoom);
        return ResponseEntity.ok("Room added successfully");
    }

    @DeleteMapping("/delete/{roomID}")
    public ResponseEntity<String> deleteRoom(@PathVariable("roomID") int roomID) {
        if (roomRepository.existsById(roomID)) {
            roomRepository.deleteById(roomID);
            return ResponseEntity.ok("Room deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{roomID}")
    public ResponseEntity<String> updateRoom(@PathVariable("roomID") int roomID,
                                             @RequestBody RoomRequest roomRequest) {

        Optional<Room> optional = roomRepository.findById(roomID);
        if (optional.isPresent()) {
            if (!roomRepository.existsById(roomID)) {
                return ResponseEntity.notFound().build();
            }
            Room existingRoom = optional.get();

            if (roomRequest.getHotel() != null) {
                existingRoom.setHotel(roomRequest.getHotel());
            }
            if (roomRequest.getType() != null) {
                existingRoom.setType(roomRequest.getType());
            }

            Double price = roomRequest.getPrice();
            if (price != null) {
                if (price <= 0) {
                    return ResponseEntity.badRequest().body("Price must be greater than zero");
                }
                existingRoom.setPrice(price);
            }
            Boolean availability = roomRequest.isAvailability();
            if (availability != null) {
                existingRoom.setAvailability(roomRequest.isAvailability());
            }

            roomRepository.save(existingRoom);
            return ResponseEntity.ok("Room updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
