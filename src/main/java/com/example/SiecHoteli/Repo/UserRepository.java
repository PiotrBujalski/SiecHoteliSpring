package com.example.SiecHoteli.Repo;

import com.example.SiecHoteli.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
    boolean existsById(Integer id);
}
