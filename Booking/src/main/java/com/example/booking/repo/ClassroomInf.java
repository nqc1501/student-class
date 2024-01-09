package com.example.booking.repo;

import com.example.core.domain.result.domain.Classroom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ClassroomInf extends JpaRepository<Classroom, Integer> {

    List<Classroom> findByName(String key, Pageable pageable);

    List<Classroom> findByOwner(int owner, Pageable pageable);
}
