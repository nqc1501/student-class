package com.example.userservice.repo;

import com.example.core.domain.result.domain.Student;
import com.example.core.domain.result.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u from users u WHERE u.name = :input")
    List<User> findByName(@Param("input") String input);

    List<User> findByEmail(String input);

    void deleteByEmail(String email);

    @Query("SELECT u from users u")
    Page<User> findAllAndPage(Pageable pageable);
}
