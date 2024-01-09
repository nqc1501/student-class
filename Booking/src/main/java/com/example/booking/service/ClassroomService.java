package com.example.booking.service;

import com.example.booking.dto.req.ClassesGotDto;
import com.example.booking.dto.req.RequestRoomDto;
import com.example.core.domain.result.domain.Classroom;
import com.example.booking.dto.res.ClassesRoomResDto;
import com.example.core.domain.result.Result;

import java.util.List;

public interface ClassroomService {
    List<ClassesRoomResDto> getClassesBooked(ClassesGotDto dto);

    Result<Classroom> cancelClassroom(int id);

    Result<Object> requestRoom(RequestRoomDto dto);
}
