package com.example.SiecHoteli.Repo;

import com.example.SiecHoteli.Entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservRepository extends JpaRepository<Reservations,Integer> {
}
