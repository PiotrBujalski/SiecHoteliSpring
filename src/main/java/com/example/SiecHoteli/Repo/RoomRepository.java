package com.example.SiecHoteli.Repo;

import com.example.SiecHoteli.Entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r.availability FROM Room r WHERE r.roomID = :roomID")
    boolean getAvailabilityByRoomID(@Param("roomID") int roomID);

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.availability = :availability WHERE r.roomID = :roomID")
    void setAvailabilityByRoomID(@Param("roomID") int roomID, @Param("availability") boolean availability);

    boolean existsByRoomIDAndHotel_HotelID(int roomID, int hotelID);

    @Query("SELECT r FROM Room r WHERE NOT EXISTS (SELECT 1 FROM Reservations res WHERE res.room = r AND (res.startDate <= :endDate AND res.endDate >= :startDate))")
    List<Room> findAvailableRooms(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
