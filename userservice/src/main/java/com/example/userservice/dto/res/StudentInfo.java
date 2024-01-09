package com.example.userservice.dto.res;

import com.example.core.domain.result.domain.Room;
import com.example.core.domain.result.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo extends Student {
    private Room student_class;
}
