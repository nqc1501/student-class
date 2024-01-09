package com.example.booking.api;

import com.example.core.domain.result.domain.BaseObject;
import com.example.core.domain.result.domain.Classroom;
import com.example.booking.dto.req.CancelRoomDto;
import com.example.booking.dto.req.ClassesGotDto;
import com.example.booking.dto.req.RequestRoomDto;
import com.example.booking.dto.res.ClassesRoomResDto;
import com.example.booking.dto.res.ListResultResDto;
import com.example.booking.service.ClassroomService;
import com.example.core.domain.result.JsonUtil;
import com.example.core.domain.result.Result;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
//@RequestMapping("/api/v1/class/")
@RequestMapping("/user")
public class ClassroomApi extends BaseObject {

    private final ClassroomService service;
    private final ModelMapper mapper;

    @Autowired
    public ClassroomApi(ClassroomService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/list-class-room")
    public String getClassesBooked(@RequestBody ClassesGotDto dto) {
        logger.info("getClassesBooked_____{}", dto);
        List<ClassesRoomResDto> classrooms = service.getClassesBooked(dto);
        ListResultResDto resDto = mapper.map(Result.success(classrooms), ListResultResDto.class);
        resDto.setTotal(classrooms.size());
        return JsonUtil.toJSONString(resDto);
    }

    @PostMapping("/cancel-room")
    public ResponseEntity<Result<Classroom>> cancelRoom(@RequestBody CancelRoomDto dto) {
        logger.info("cancelRoom___________{}", dto);
        return ResponseEntity.ok(service.cancelClassroom(dto.getId()));
    }

    @PostMapping("/request-room")
    public ResponseEntity<Result<Object>> requestRoom(@RequestBody RequestRoomDto dto) {
        logger.info("requestRoom___________{}", dto);
        return ResponseEntity.ok(service.requestRoom(dto));
    }

}
