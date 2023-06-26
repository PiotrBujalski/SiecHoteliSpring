package com.example.SiecHoteli.Repo;

import com.example.SiecHoteli.Entity.Reservations;
import com.example.SiecHoteli.Entity.Reservations_History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservHistoryRepository extends JpaRepository<Reservations_History,Integer> {

}