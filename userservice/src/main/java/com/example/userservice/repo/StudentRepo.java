package com.example.userservice.repo;

import com.example.core.domain.result.domain.Student;
import com.example.core.domain.result.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    List<Student> findByNameOrEmailLike(String input, Pageable pageable);

    List<Student> findByName(String input, Pageable pageable);

    @Query("SELECT s from students s where s.student_code = :input")
    List<Student> findByStudentCode(@Param(value = "input") String input, Pageable pageable);

    List<Student> findByStudyClass(Integer input, Pageable pageable);

    Optional<Student> findFirst1ByEmail(String email);

    @Query("SELECT s from students s")
    Page<Student> findAllAndPage(Pageable pageable);
}
