package com.example.userservice.service;

import com.example.core.domain.result.Result;
import com.example.core.domain.result.domain.Room;
import com.example.userservice.dto.req.*;
import com.example.userservice.dto.res.GetStudentResDto;
import com.example.userservice.dto.res.StudentInfo;

import java.util.List;

public interface UserService {

    List<GetStudentResDto> getListUsers(GetStudentReqDto dto);

    Result<Object> deleteUserInfo(Integer id);

    List<Room> getListRoom(GetListClassesDto dto);

    List<StudentInfo> getListStudents(GetListStudentReqDto dto);

    Result<Object> deleteStudent(DeleteStudentReq dto);

    Result<Object> deleteClassroom(DeleteClassRoomReqDto dto);

    Result<Object> updateOrAddStudent(AddStudentReqDto dto);

    Result<Object> updateOrAddClassRoom(UpdateOrAddNewClass dto);

}
