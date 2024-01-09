package com.example.userservice.api;

import com.example.core.domain.result.JsonUtil;
import com.example.core.domain.result.Result;
import com.example.core.domain.result.domain.BaseObject;
import com.example.core.domain.result.domain.Room;
import com.example.userservice.dto.req.*;
import com.example.userservice.dto.res.GetStudentResDto;
import com.example.userservice.dto.res.StudentInfo;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController extends BaseObject {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // người dùng
    @PostMapping("/list-user")
    public ResponseEntity<Result<List<GetStudentResDto>>> getListUsers(@RequestBody GetStudentReqDto dto) {
        logger.info("getListUsers Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(Result.success(userService.getListUsers(dto)));
    }

    @PostMapping("/delete-user")
    public ResponseEntity<Result<Object>> deleteUser(@RequestBody DeleteUserReq dto) {
        logger.info("deleteUser Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(userService.deleteUserInfo(dto.getId()));
    }

    //học sinh
    @PostMapping("/list-class")
    public ResponseEntity<Result<List<Room>>> getListClasses(@RequestBody GetListClassesDto dto) {
        logger.info("getListClasses Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(Result.success(userService.getListRoom(dto)));
    }

    @PostMapping("/list-student")
    public ResponseEntity<Result<List<StudentInfo>>> getListStudents(@RequestBody GetListStudentReqDto dto) {
        logger.info("getListStudents Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(Result.success(userService.getListStudents(dto)));
    }

    @PostMapping("/add-student")
    public ResponseEntity<Result<Object>> addStudent(@RequestBody AddStudentReqDto dto) {
        logger.info("addStudent_____" + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(userService.updateOrAddStudent(dto));
    }

    @PostMapping("/update-student")
    public ResponseEntity<Result<Object>> updateStudent(@RequestBody AddStudentReqDto dto) {
        logger.info("updateStudent_____" + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(userService.updateOrAddStudent(dto));
    }

    @PostMapping("/delete-student")
    public ResponseEntity<Result<Object>> deleteStudent(@RequestBody DeleteStudentReq dto) {
        logger.info("deleteStudent Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(userService.deleteStudent(dto));
    }

    //phòng học

    @PostMapping("/update-class-room")
    public ResponseEntity<Result<Object>> updateClassRoom(@RequestBody UpdateOrAddNewClass dto) {
        logger.info("deleteStudent Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(userService.updateOrAddClassRoom(dto));
    }

    @PostMapping("/add-class-room")
    public ResponseEntity<Result<Object>> addClassRoom(@RequestBody UpdateOrAddNewClass dto) {
        logger.info("deleteStudent Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(userService.updateOrAddClassRoom(dto));
    }

    @PostMapping("/delete-class-room")
    public ResponseEntity<Result<Object>> deleteClassroom(@RequestBody DeleteClassRoomReqDto dto) {
        logger.info("deleteClassroom Request dto _____ " + JsonUtil.toJSONString(dto));
        return ResponseEntity.ok(userService.deleteClassroom(dto));
    }

}
