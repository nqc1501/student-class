package com.example.booking.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClassroomRepo {

    private final ClassroomInf interfaceRepo;

    @Autowired
    public ClassroomRepo(ClassroomInf interfaceRepo) {
        this.interfaceRepo = interfaceRepo;
    }


}
