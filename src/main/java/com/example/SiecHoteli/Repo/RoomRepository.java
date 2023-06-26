package com.example.SiecHoteli.Repo;

import com.example.SiecHoteli.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
