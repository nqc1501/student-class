package com.example.userservice.repo;

import com.example.core.domain.result.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CLassRoomRepo extends JpaRepository<Classroom, Integer> {
    List<Classroom> findByOwner(int owner);
}
