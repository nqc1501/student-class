package com.example.userservice.dto.res;

import com.example.core.domain.result.domain.BaseEntity;
import com.example.core.domain.result.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentResDto extends BaseEntity {
    private String email;
    private String email_verified_at;
    private int id;
    private String name;
    private int role;
    private StudentInfo student_info;
}
