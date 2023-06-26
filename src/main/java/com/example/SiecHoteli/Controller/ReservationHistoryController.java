package com.example.SiecHoteli.Controller;

import com.example.SiecHoteli.Entity.Reservations;
import com.example.SiecHoteli.Entity.Reservations_History;
import com.example.SiecHoteli.Repo.ReservHistoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
public class ReservationHistoryController {
    private final ReservHistoryRepository historyRepository;

    public ReservationHistoryController(ReservHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping("/getAll")
    public List<Reservations_History> getAllReservations() {
        return historyRepository.findAll();
    }

}
