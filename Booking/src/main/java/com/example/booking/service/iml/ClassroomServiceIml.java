package com.example.booking.service.iml;

import com.example.core.domain.result.domain.BaseObject;
import com.example.core.domain.result.domain.Room;
import com.example.core.domain.result.domain.Student;
import com.example.booking.dto.req.ClassesGotDto;
import com.example.booking.dto.req.RequestRoomDto;
import com.example.booking.dto.res.ClassesRoomResDto;
import com.example.booking.dto.res.OwnerInfo;
import com.example.booking.repo.ClassroomInf;

import com.example.booking.repo.RoomRepo;
import com.example.booking.repo.StudentRepo;
import com.example.booking.service.ClassroomService;
import com.example.core.domain.result.domain.Classroom;
import com.example.core.domain.result.Result;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.example.booking.constant.Message.ACTIVE;
import static com.example.booking.constant.Message.CANCEL_CLASSROOM_SUCCESS;

@Service
public class ClassroomServiceIml extends BaseObject implements ClassroomService {

    private final ClassroomInf classroomRepo;
    private final ModelMapper mapper;
    private final RoomRepo roomRepo;
    private final StudentRepo studentRepo;

    @Autowired
    public ClassroomServiceIml(ClassroomInf classroomRepo,
                               ModelMapper mapper,
                               RoomRepo roomRepo,
                               StudentRepo studentRepo) {
        this.classroomRepo = classroomRepo;
        this.mapper = mapper;
        this.roomRepo = roomRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public List<ClassesRoomResDto> getClassesBooked(ClassesGotDto dto) {
        return findByKeyWordAndStartAtAndLimit(dto.getKey(), dto.getStart(), dto.getLimit(), dto.getOwner());
    }

    @Override
    public Result<Classroom> cancelClassroom(int id) {
        Optional<Classroom> classroom = findById(id);
        if (classroom.isPresent()) {
            classroom.get().setOwner(null);
            classroom.get().setStatus(0);
            classroom.get().setUpdated_at(new Timestamp(System.currentTimeMillis()));
            classroomRepo.save(classroom.get());
            return Result.success(CANCEL_CLASSROOM_SUCCESS, classroom.get());
        }
        return Result.success(CANCEL_CLASSROOM_SUCCESS);
    }

//    @Override
    public Result<Object> requestRoom(RequestRoomDto dto) {
        Optional<Classroom> classroom = findById(dto.getRoom());
        if (classroom.isPresent()) {
            if (classroom.get().getStatus() != ACTIVE) {
                classroom.get().setEnd_time(dto.getEndTime());
                classroom.get().setStatus(ACTIVE);
                classroom.get().setOwner(dto.getOwner());
                classroom.get().setStart_time(dto.getStartTime());
                classroom.get().setUpdated_at(new Timestamp(System.currentTimeMillis()));
                classroomRepo.save(classroom.get());
            }
            return Result.success((Object) classroom.get());
        }
        return Result.notFound();
    }

    public void save(Classroom classroom) {
        save(classroom);
    }

    public void saveAll(List<Classroom> classrooms) {
        saveAll(classrooms);
    }

    public void delete(int id) {
        classroomRepo.deleteById(id);
    }

    public List<ClassesRoomResDto> findByKeyWordAndStartAtAndLimit(String key, int start, int limit, Integer owner) {
        Pageable pageable = PageRequest.of(start, limit);
        List<Classroom> classrooms;
        if (StringUtils.hasLength(key)) {
            key = key.trim();
            classrooms = classroomRepo.findByName(key, pageable);
        } else if (owner != null) {
            classrooms = classroomRepo.findByOwner(owner, pageable);
        } else {
            classrooms = classroomRepo.findAll();
        }
        return fillMapper(classrooms);
    }

    private List<ClassesRoomResDto> fillMapper(List<Classroom> classrooms) {
        List<ClassesRoomResDto> resDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(classrooms)) {
            classrooms.forEach(classroom -> {
                ClassesRoomResDto dto = mapper.map(classroom, ClassesRoomResDto.class);
                if (dto.getOwner() != null) {
                    Student student = studentRepo.findById(dto.getOwner()).orElse(null);
                    if (student != null) {
                        OwnerInfo ownerInfo = mapper.map(student, OwnerInfo.class);
                        Room room = roomRepo.findById(student.getStudyClass()).orElse(new Room());
                        ownerInfo.setStudent_class(room);
                        dto.setOwner_info(ownerInfo);
                    }
                }
                resDtos.add(dto);
            });

        }
        resDtos.sort(Comparator.comparing(Classroom::getName));
        return resDtos;
    }

    public Optional<Classroom> findById(int id) {
        return classroomRepo.findById(id);
    }

}
