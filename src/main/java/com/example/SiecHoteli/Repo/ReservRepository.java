package com.example.SiecHoteli.Repo;

import com.example.SiecHoteli.Entity.Reservations;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservRepository extends JpaRepository<Reservations,Integer> {
    @Transactional
    @Modifying
    @Query(value = "CALL delete_expired_reservations()", nativeQuery = true)
    void deleteExpiredReservations();

    List<Reservations> findAllByUserID(Integer userId);

    boolean existsByUserIDAndEndDateGreaterThanEqualAndStartDateLessThanEqual(Integer userID, Date startDate, Date endDate);
}
