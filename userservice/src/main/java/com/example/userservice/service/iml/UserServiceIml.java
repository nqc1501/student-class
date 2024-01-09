package com.example.userservice.service.iml;

import com.example.core.domain.result.JsonUtil;
import com.example.core.domain.result.Result;
import com.example.core.domain.result.domain.*;
import com.example.userservice.dto.req.*;
import com.example.userservice.dto.res.GetStudentResDto;
import com.example.userservice.dto.res.StudentInfo;
import com.example.userservice.repo.CLassRoomRepo;
import com.example.userservice.repo.RoomRepo;
import com.example.userservice.repo.StudentRepo;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.lang.model.element.PackageElement;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.userservice.config.Message.*;

@AllArgsConstructor
@Service
public class UserServiceIml extends BaseObject implements UserService {

    private final UserRepo userRepo;
    private final StudentRepo studentRepo;
    private final RoomRepo roomRepo;
    private final CLassRoomRepo cLassRoomRepo;
    private ModelMapper modelMapper;



    public List<GetStudentResDto> getListUsers(GetStudentReqDto dto) {
        List<User> users;
        String email = dto.getEmail();
        String name = dto.getName();
        Pageable pageable = genPageable(dto.getStart(), dto.getLimit());
        if (StringUtils.hasLength(email)) {
            email = email.trim();
            logger.info("query by email____" + email);
            users = userRepo.findByEmail(email);
        } else if (StringUtils.hasLength(name)) {
            name = name.trim();
            logger.info("query by name____" + name);
            users = userRepo.findByName(name);
        } else {
            logger.info("query all_________");
            users = userRepo.findAllAndPage(pageable).getContent();
        }

        List<GetStudentResDto> resDto = new ArrayList<>();

        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(user -> resDto.add(initGetStudentResDto(user)));
        }
        return resDto;
    }

    private GetStudentResDto initGetStudentResDto(User user) {
        GetStudentResDto dto = modelMapper.map(user, GetStudentResDto.class);
        dto.setStudent_info(initStudentInfo(user.getEmail()));
        return dto;
    }

    private StudentInfo setUpFieldStudentInfo(Student student) {
        StudentInfo studentInfo = modelMapper.map(student, StudentInfo.class);
        if (student.getStudyClass() != null) {
            Optional<Room> room = roomRepo.findById(student.getStudyClass());
            room.ifPresent(studentInfo::setStudent_class);
        }
        return studentInfo;

    }

    private StudentInfo initStudentInfo(String email) {
        Optional<Student> student = studentRepo.findFirst1ByEmail(email);
        return student.map(this::setUpFieldStudentInfo).orElse(null);
    }

    private StudentInfo initStudentInfo(Student student) {
        return setUpFieldStudentInfo(student);
    }

    public Result<Object> deleteUserInfo(Integer id) {
        userRepo.deleteById(id);
        return Result.success(DELETE_USER_SUCCESS);
    }

    public List<Room> getListRoom(GetListClassesDto dto) {
        String key = dto.getKey();
        List<Room> rooms;
        if (StringUtils.hasLength(key)) {
            rooms = roomRepo.findByNameLike(key);
        } else {
            rooms = roomRepo.findAll();
        }
        return rooms;
    }

    private Pageable genPageable(int start, int limit) {
        return PageRequest.of(start, limit);
    }

    public List<StudentInfo> getListStudents(GetListStudentReqDto dto) {

        List<Student> students;
        String name = dto.getName();
        String code = dto.getCode();
        Integer clazz = dto.getClazz();

        if (dto.getAll() != null && dto.getAll()) {
            students = studentRepo.findAll();
        } else {
            Pageable pageable = genPageable(dto.getStart(), dto.getLimit());
            if (StringUtils.hasLength(name)) {
                name = name.trim();
                students = studentRepo.findByName(name, pageable);
            } else if (StringUtils.hasLength(code)) {
                code = code.trim();
                logger.info("code _____ " + code);
                students = studentRepo.findByStudentCode(code, pageable);
            } else if (clazz != null) {
                students = studentRepo.findByStudyClass(clazz, pageable);
            } else {
                students = studentRepo.findAllAndPage(pageable).getContent();
            }
        }
        List<StudentInfo> studentInfos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(students)) {
            students.forEach(student -> studentInfos.add(initStudentInfo(student)));
        }
        return studentInfos;
    }

    public Result<Object> deleteStudent(DeleteStudentReq dto) {
        Optional<Student> student = studentRepo.findById(dto.getId());
        student.ifPresent(value -> callbackExecutor.execute(() -> {
            studentRepo.deleteById(dto.getId());
            userRepo.deleteByEmail(value.getEmail());
            List<Classroom> classrooms = cLassRoomRepo.findByOwner(value.getId());
            if (!CollectionUtils.isEmpty(classrooms)) {
                classrooms.forEach(classroom -> classroom.setOwner(null));
            }
            cLassRoomRepo.saveAll(classrooms);
        }));
        return Result.success(DELETE_STUDENT_SUCCESS);
    }

    public Result<Object> deleteClassroom(DeleteClassRoomReqDto dto) {
        cLassRoomRepo.deleteById(dto.getId());
        return Result.success(DELETE_CLASSROOM_SUCCESS);
    }

    public Result<Object> updateOrAddStudent(AddStudentReqDto dto) {
        Student student = new Student();
        modelMapper.map(dto, student);
        student.setAbilityToBorrow(1);
        logger.info("student___" + JsonUtil.toJSONString(student));
        //todo: update img
        student.setImage("/students/641bd64a6a6b4.jpg");
        studentRepo.save(student);
        return Result.success();
    }

    public Result<Object> updateOrAddClassRoom(UpdateOrAddNewClass dto) {
        Classroom classroom = new Classroom();
        modelMapper.map(dto, classroom);
        classroom.setStatus(1);
        classroom.setOwner(null);
        //todo: update img
        classroom.setImage("/classRooms/641c133147afd.png");
        cLassRoomRepo.save(classroom);
        return Result.success();
    }
}
