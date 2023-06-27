package com.example.SiecHoteli.Repo;

import com.example.SiecHoteli.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r.availability FROM Room r WHERE r.roomID = :roomID")
    boolean getAvailabilityByRoomID(@Param("roomID") int roomID);
}
