package com.gameStore.ernestasUrbonas.repository;

import com.gameStore.ernestasUrbonas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
