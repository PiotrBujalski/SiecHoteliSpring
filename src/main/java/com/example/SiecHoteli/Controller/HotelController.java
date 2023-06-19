package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Entity.Hotel;
import com.example.SiecHoteli.Repo.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelRepository hoteleRepository;

    @Autowired
    public HotelController(HotelRepository hoteleRepository) {
        this.hoteleRepository = hoteleRepository;
    }

    @GetMapping("/getAll")
    public List<Hotel> getAllHotels() {
        return hoteleRepository.findAll();
    }
}

